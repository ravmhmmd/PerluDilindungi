package com.example.perludilindungi.data

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

data class CheckInRequest(

    @SerializedName("qrCode"     ) var qrCode      : String,
    @SerializedName("latitude"   ) var latitude    : Double,
    @SerializedName("longitude"  ) var longitude   : Double

)

class CheckInRequestSerializer : JsonSerializer<CheckInRequest>{
    override fun serialize(
        src: CheckInRequest?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val obj = JsonObject()
        obj.add("qrCode",context?.serialize(src!!.qrCode))
        obj.add("latitude",context?.serialize(src!!.latitude))
        obj.add("longitude",context?.serialize(src!!.longitude))
        return obj
    }

}

data class CheckInResponse(

    @SerializedName("success"    ) var success: Boolean,
    @SerializedName("code"       ) var code: Int,
    @SerializedName("message"    ) var message: String,
    @SerializedName("data"       ) var data: CheckInResponseData

)

data class CheckInResponseData(

    @SerializedName("userStatus" ) var userStatus  : String,
    @SerializedName("reason"     ) var reason      : String

)
