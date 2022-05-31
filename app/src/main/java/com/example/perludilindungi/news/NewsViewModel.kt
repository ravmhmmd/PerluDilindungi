package com.example.perludilindungi.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perludilindungi.ApiService
import com.example.perludilindungi.data.News
import com.example.perludilindungi.data.ResultsNews
import com.example.perludilindungi.list.FacilityListFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsViewModel(application: Application) : AndroidViewModel(application)  {
    val url = "https://perludilindungi.herokuapp.com/api/"
    val newsData: LiveData<MutableList<ResultsNews>>
        get() = getCurrentNewsData()

    fun getCurrentNewsData(): LiveData<MutableList<ResultsNews>> {
        val newsData = MutableLiveData<MutableList<ResultsNews>>(ArrayList())

        val retrofit = Retrofit.Builder()
            .baseUrl(FacilityListFragment.url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ApiService::class.java)
        val  newsCall = service.getNewsList()

        newsCall.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.code() == 200) {
                    newsData.value = response.body()?.results!!
                }
            }
            override fun onFailure(call: Call<News>, t: Throwable) {
            }
        })
        return newsData
    }
}