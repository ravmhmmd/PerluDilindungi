package com.example.perludilindungi.detail

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perludilindungi.R
import com.example.perludilindungi.databinding.FragmentDetailBinding

class FacilityDetailViewModel : ViewModel() {

    private val _nameText = MutableLiveData<String>()
    val nameText: LiveData<String>
        get() = _nameText

    private val _codeText = MutableLiveData<String>()
    val codeText: LiveData<String>
        get() = _codeText

    private val _facilityTypeText = MutableLiveData<String>()
    val facilityTypeText: LiveData<String>
        get() = _facilityTypeText

    private val _addressText = MutableLiveData<String>()
    val addressText: LiveData<String>
        get() = _addressText

    private val _phoneText = MutableLiveData<String>()
    val phoneText: LiveData<String>
        get() = _phoneText

    private val _statusText = MutableLiveData<String>()
    val statusText: LiveData<String>
        get() = _statusText

    private val _statusImage = MutableLiveData<String>()
    val statusImage: LiveData<String>
        get() = _statusImage


    fun initDetailData(args: FacilityDetailFragmentArgs, binding: FragmentDetailBinding, context: Context) {
        _nameText.value = args.name
        _codeText.value = "Kode: " + args.code
        _facilityTypeText.value = args.facilityType
        _addressText.value = args.address
        _phoneText.value = "No telp: " + args.phone
        _statusText.value = args.status
        if (args.facilityType == "RUMAH SAKIT") {
            binding.detailFacilityType.setBackgroundColor(ContextCompat.getColor(context, R.color.purple))
        } else {
            binding.detailFacilityType.setBackgroundColor(ContextCompat.getColor(context, R.color.pink_color))
        }
        if (args.status == "Siap Vaksinasi") {
            _statusImage.value = "@drawable/ready_icon"
        } else {
            _statusImage.value = "@drawable/unready_icon"
        }
    }
}