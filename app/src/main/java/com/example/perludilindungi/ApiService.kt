package com.example.perludilindungi

import com.example.perludilindungi.data.*
import com.google.zxing.qrcode.encoder.QRCode
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {
    @GET("get-news")
    fun getNewsList(): Call<News>

    @GET("get-province")
    fun getProvinceList(): Call<Province>

    @GET("get-city?")
    fun getCityList(@Query("start_id") start_id: String): Call<City>

    @GET("get-faskes-vaksinasi?")
    fun getFaskesList(@Query("province") province: String, @Query("city") city: String): Call<Faskes>

    @POST("check-in")
    fun checkIn(
        @Body checkIn : CheckInRequest
    ) : Call<CheckInResponse>
}