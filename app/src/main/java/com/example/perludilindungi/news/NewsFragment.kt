package com.example.perludilindungi.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perludilindungi.ApiService
import com.example.perludilindungi.R
import com.example.perludilindungi.data.*
import com.example.perludilindungi.database.BookmarkDatabase
import com.example.perludilindungi.databinding.FragmentNewsBinding
import com.example.perludilindungi.detail.FacilityDetailViewModel
import com.example.perludilindungi.list.CustomAdapter
import com.example.perludilindungi.list.FacilityListFragment
import com.example.perludilindungi.viewmodel.BookmarkViewModel
import com.example.perludilindungi.viewmodel.BookmarkViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsFragment : Fragment() {

    private lateinit var recyclerview: RecyclerView
    lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = DataBindingUtil.inflate<FragmentNewsBinding>(inflater, R.layout.fragment_news,container,false)
        recyclerview = rootView.root.findViewById(R.id.recyclerviewNews)
        val application = requireNotNull(this.activity).application
        newsViewModel = NewsViewModel(application)
        return rootView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        newsViewModel.newsData.observe(viewLifecycleOwner,
            { newData ->
                val adapterNews = CustomAdapter(requireContext(), newData)

                recyclerview.adapter = adapterNews
                adapterNews.notifyDataSetChanged()
            })
    }
}