package com.example.perludilindungi.data

import com.google.gson.annotations.SerializedName

data class Province(

    @SerializedName("curr_val") var currVal: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("results") var results: ArrayList<ResultsProvince> = arrayListOf()

)

data class ResultsProvince(

    @SerializedName("key") var key: String? = null,
    @SerializedName("value") var value: String? = null

)
