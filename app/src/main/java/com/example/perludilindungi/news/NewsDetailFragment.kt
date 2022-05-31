package com.example.perludilindungi.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.perludilindungi.R
import com.example.perludilindungi.databinding.FragmentDetailBinding
import com.example.perludilindungi.databinding.FragmentNewsDetailBinding
import com.example.perludilindungi.detail.FacilityDetailFragmentArgs
import com.example.perludilindungi.detail.FacilityDetailViewModel

class NewsDetailFragment : Fragment() {

    val args: NewsDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentNewsDetailBinding>(inflater,
            R.layout.fragment_news_detail,container,false)

        val newsDetailViewModel = NewsDetailViewModel()
        newsDetailViewModel.initDetailData(args, binding, requireContext())

        binding.setLifecycleOwner(this)
        return binding.root
    }
}