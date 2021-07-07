package com.example.newactivity

import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.WebViewClient
import com.example.newactivity.databinding.ActivityNewsContentBinding

class NewsContentActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityNewsContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityNewsContentBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportActionBar?.hide()//去掉标题

        fullScreen(this)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

//        mBinding.webView.settings.javaScriptEnabled = true
//        mBinding.webView.webViewClient = WebViewClient()
//        mBinding.webView.loadUrl("https://mini.eastday.com/mobile/210430083930636889030.html")



    }

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

}