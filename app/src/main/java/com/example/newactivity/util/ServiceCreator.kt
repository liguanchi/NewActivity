package com.example.newactivity.util

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {
    private const val BASE_URL = "http://v.juhe.cn/toutiao/"

    //构建Retrofit对象
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        //设定json转换器
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun <T> create(serviceClass: Class<T>):T= retrofit.create(serviceClass)

    inline fun <reified T> create():T= create(T::class.java)
}