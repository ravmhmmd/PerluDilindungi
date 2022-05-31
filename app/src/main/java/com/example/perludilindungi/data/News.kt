package com.example.perludilindungi.data

import com.google.gson.annotations.SerializedName

data class News(

    @SerializedName("success"     ) var success    : Boolean?           = null,
    @SerializedName("message"     ) var message    : String?            = null,
    @SerializedName("count_total" ) var countTotal : Int?               = null,
    @SerializedName("results"     ) var results    : ArrayList<ResultsNews> = arrayListOf()

)

class ResultsNews {
    @SerializedName("title"       ) var title       : String?           = null
    @SerializedName("link"        ) var link        : ArrayList<String> = arrayListOf()
    @SerializedName("guid"        ) var guid        : String?           = null
    @SerializedName("pubDate"     ) var pubDate     : String?           = null
    @SerializedName("description" ) var description : Description?      = Description()
    @SerializedName("enclosure"   ) var enclosure   : Enclosure?        = Enclosure()
}

data class Description (

    @SerializedName("__cdata" ) var _cdata : String? = null

)

data class Enclosure (

    @SerializedName("_url"    ) var Url    : String? = null,
    @SerializedName("_length" ) var Length : String? = null,
    @SerializedName("_type"   ) var Type   : String? = null

)