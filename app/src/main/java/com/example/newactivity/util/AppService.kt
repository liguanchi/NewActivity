package com.example.newactivity.util

import com.example.retrofittset.model.Succeed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AppService {
    @GET("index?")
    fun getAppData(@Query("key") id:String):Call<Succeed>
}