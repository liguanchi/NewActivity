package com.example.newactivity.util

import com.example.newactivity.model.Data
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DataService {
    @GET("index?key=<keys>")
    fun getNew(@Query("key") keys:String) :Call<Data>

}