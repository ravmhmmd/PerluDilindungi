package com.example.perludilindungi.data

import com.google.gson.annotations.SerializedName

data class City(

    @SerializedName("curr_val") var currVal: String? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("results") var results: ArrayList<ResultsCity> = arrayListOf()

)

data class ResultsCity(

    @SerializedName("key") var key: String? = null,
    @SerializedName("value") var value: String? = null

)
