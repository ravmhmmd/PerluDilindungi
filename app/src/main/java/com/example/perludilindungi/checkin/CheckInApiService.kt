package com.example.perludilindungi.checkin

import android.util.Log
import com.example.perludilindungi.ApiService
import com.example.perludilindungi.data.CheckInRequest
import com.example.perludilindungi.data.CheckInResponse
import retrofit2.Response
import retrofit2.Callback

class CheckInApiService {
    fun doCheckIn(ciRequest : CheckInRequest, ciResult : (CheckInResponse?) -> Unit){
        val retrofit = CheckInServiceBuilder.buildService(ApiService::class.java)
        retrofit.checkIn(ciRequest).enqueue(
            object : Callback<CheckInResponse> {
                override fun onResponse(
                    call: retrofit2.Call<CheckInResponse>,
                    response: Response<CheckInResponse>
                ) {
                    val res = response.body()
                    ciResult(res)
                }

                override fun onFailure(call: retrofit2.Call<CheckInResponse>, t: Throwable) {
                    ciResult(null)
                }

            }
        )
    }
}