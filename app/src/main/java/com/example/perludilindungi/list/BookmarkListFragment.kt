package com.example.perludilindungi.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perludilindungi.R
import com.example.perludilindungi.data.ResultsFaskes
import com.example.perludilindungi.database.Bookmark
import com.example.perludilindungi.database.BookmarkDatabase
import com.example.perludilindungi.databinding.FragmentBookmarkListBinding
import com.example.perludilindungi.viewmodel.BookmarkViewModel
import com.example.perludilindungi.viewmodel.BookmarkViewModelFactory


class BookmarkListFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkListBinding

    var bookmarkData: List<Bookmark> = ArrayList()
    private lateinit var bookmarkViewModel: BookmarkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,R.layout.fragment_bookmark_list,container,false)

        val application = requireNotNull(this.activity).application
        val dataSource = BookmarkDatabase.getInstance(application).bookmarkDatabaseDao
        val viewModelFactory = BookmarkViewModelFactory(dataSource, application)
        bookmarkViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(BookmarkViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerview: RecyclerView = binding.recyclerviewBookmarkFaskes
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        bookmarkViewModel.currentBookmarkData.observe(viewLifecycleOwner,
            { newData ->
                bookmarkData = newData
                val adapterFaskes = CustomAdapter(requireContext(),
                    ArrayList(),
                    bookmarkData,
                    true
                )
                recyclerview.adapter = adapterFaskes
                adapterFaskes.notifyDataSetChanged()
            })
    }
}