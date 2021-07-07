package com.example.newactivity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newactivity.databinding.ActivityMainBinding
import com.example.newactivity.util.AppService
import com.example.newactivity.util.ServiceCreator
import com.example.retrofittset.model.New
import com.example.retrofittset.model.Succeed
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    private fun fullScreen(activity: Activity) {
        run {
            //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
            val window = activity.window
            val decorView = window.decorView
            //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
            val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            decorView.systemUiVisibility = option
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private val new = mutableListOf<New>()

    private val newsList = ArrayList<New>()

    private val adapter = NewsAdapter(newsList)

    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportActionBar?.hide()//去掉标题
        fullScreen(this)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        initNew()

        mBinding.switchMaterial.setColorSchemeResources(R.color.red)
        mBinding.switchMaterial.setOnRefreshListener {
            refresh(adapter)
        }

    }

    private fun refresh(adapter: NewsAdapter) {
        thread {
            Thread.sleep(2000)
            runOnUiThread {
                initNew()
                adapter.notifyDataSetChanged()
                mBinding.switchMaterial.isRefreshing = false
            }
        }
    }

    private fun initNew() {
        val appService = ServiceCreator.create<AppService>()
        appService.getAppData("cd49c0ca44152641199e4c48476b5473")
            .enqueue(object : Callback<Succeed> {
                override fun onResponse(call: Call<Succeed>, response: Response<Succeed>) {
                    val data = response.body()
                    if (data != null) {
                        val res = data.result.data
                        for (i in res) {
                            new.add(
                                New(
                                    i.title,
                                    i.date,
                                    i.author_name,
                                    i.url,
                                    i.thumbnail_pic_s
                                )
                            )
                            Log.d("dddd", i.url)
                        }
                        initFruit()
                        mBinding.NewsRecyclerView.layoutManager =
                            LinearLayoutManager(this@MainActivity)
                        mBinding.NewsRecyclerView.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<Succeed>, t: Throwable) {
                    t.printStackTrace()
                    Log.d("Test", "失败了")
                }
            })
    }

    private fun initFruit() {
        newsList.clear()
        repeat(30) {
            val index = (0 until new.size).random()
            newsList.add(new[index])
        }
    }


    inner class NewsAdapter(private val newsList: ArrayList<New>) :
        RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val title: TextView = view.findViewById(R.id.newTitle)
            val author_name: TextView = view.findViewById(R.id.newId)
            val date: TextView = view.findViewById(R.id.newTime)
            val thumbnail_pic_s: ImageView = view.findViewById(R.id.newImage)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.new_item, parent, false)
            val viewHolder = ViewHolder(view)
            viewHolder.itemView.setOnClickListener {
                val index = viewHolder.adapterPosition
                val intent = Intent(parent.context, WebView::class.java)
                intent.putExtra("newData", newsList[index].url)
                parent.context.startActivity(intent)
            }
            return viewHolder
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val new = newsList[position]
            holder.title.text = new.title
            holder.author_name.text = new.author_name
            holder.date.text = new.date
            Glide.with(this@MainActivity).load(new.thumbnail_pic_s).into(holder.thumbnail_pic_s)
        }

        override fun getItemCount() = newsList.size
    }


}