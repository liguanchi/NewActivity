package com.example.newactivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.newactivity.databinding.ActivityMainBinding
import com.example.newactivity.model.Data
import com.example.newactivity.util.DataService
import com.example.newactivity.util.ServiceCreator
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val data = ServiceCreator.create(DataService::class.java)
        data.getNew("cd49c0ca44152641199e4c48476b5473").enqueue(object :Callback<Data>{
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                Log.i("aaaa",response.body()?.data.toString())
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })



    }

    //http://v.juhe.cn/toutiao/index?key=cd49c0ca44152641199e4c48476b5473

}