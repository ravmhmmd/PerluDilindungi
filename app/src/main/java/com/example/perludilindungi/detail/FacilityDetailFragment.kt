package com.example.perludilindungi.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.perludilindungi.R
import com.example.perludilindungi.database.BookmarkDatabase
import com.example.perludilindungi.databinding.FragmentDetailBinding
import com.example.perludilindungi.viewmodel.BookmarkViewModel
import com.example.perludilindungi.viewmodel.BookmarkViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.properties.Delegates

class FacilityDetailFragment : Fragment() {

    private var facilityId: Long = 0
    val args: FacilityDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentDetailBinding>(inflater,
            R.layout.fragment_detail,container,false)

        facilityId = args.id

        val facilityDetailViewModel = FacilityDetailViewModel()
        facilityDetailViewModel.initDetailData(args, binding, requireContext())

        val application = requireNotNull(this.activity).application
        val dataSource = BookmarkDatabase.getInstance(application).bookmarkDatabaseDao
        val viewModelFactory = BookmarkViewModelFactory(dataSource, application)
        val bookmarkViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(BookmarkViewModel::class.java)

        bookmarkViewModel.setFacilityId(args.id)
        bookmarkViewModel.getBookmarkStatus()

        binding.setLifecycleOwner(this)

        binding.facilityDetailViewModel = facilityDetailViewModel
        binding.bookmarkViewModel = bookmarkViewModel

        binding.bookmarkButton.setOnClickListener { bookmarkViewModel.handleOnClickBookmarkButton(args) }

        binding.googleMapsButton.setOnClickListener { openFacilityMap() }

        return binding.root
    }

    private fun openFacilityMap() {
        val address = args.name + " " + args.address
        val gmmIntentUri = Uri.parse("geo:0,0?q=" + address.replace(" ", "+"))
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}