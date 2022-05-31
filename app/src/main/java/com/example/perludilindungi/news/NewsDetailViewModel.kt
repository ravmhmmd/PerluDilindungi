package com.example.perludilindungi.news

import android.content.Context
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perludilindungi.R
import com.example.perludilindungi.databinding.FragmentDetailBinding
import com.example.perludilindungi.databinding.FragmentNewsDetailBinding
import com.example.perludilindungi.detail.FacilityDetailFragmentArgs

class NewsDetailViewModel : ViewModel() {

    private val _titleText = MutableLiveData<String>()
    val titleText: LiveData<String>
        get() = _titleText

    private val _linkText = MutableLiveData<String>()
    val linkText: LiveData<String>
        get() = _linkText

    private val _guidText = MutableLiveData<String>()
    val guidText: LiveData<String>
        get() = _guidText

    private val _pubDateText = MutableLiveData<String>()
    val pubDate: LiveData<String>
        get() = _pubDateText

    private val _descText = MutableLiveData<String>()
    val descDate: LiveData<String>
        get() = _descText

    private val _enclosure = MutableLiveData<String>()
    val enclosure: LiveData<String>
        get() = _enclosure


    fun initDetailData(args: NewsDetailFragmentArgs, binding: FragmentNewsDetailBinding, context: Context) {
        _titleText.value = args.title
        _linkText.value = args.link
        _guidText.value = args.guid
        _pubDateText.value = args.pubDate
        _descText.value = args.description
        _enclosure.value = args.enclosure

        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(args.guid)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.setSupportZoom(true)
        binding.webLink.text = binding.webView.getUrl()
    }
}