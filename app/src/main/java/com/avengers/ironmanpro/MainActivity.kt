package com.avengers.ironmanpro

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.format.Formatter
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.avengers.ironman.utils.DeviceUtils
import com.avengers.ironman_analysis.ResultActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.yhao.floatwindow.FloatWindow
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageView = ImageView(this)
        imageView.setImageResource(R.drawable.ic_launcher)
        imageView.setOnClickListener {
            startActivity(Intent(this, ResultActivity::class.java))
        }
        IronManApplication.application?.let {
            FloatWindow
                .with(it)
                .setView(imageView)
                .setHeight(200)
                .setWidth(200)
                .build()
        }



        Glide.with(this)
            .load("https://crazypetter.com.tw/wp-content/uploads/2019/07/BLOW-%E6%88%90%E9%95%B7%E5%8F%B2_190413_0911.jpg")
            .into(image1)

        Glide.with(this)
            .load("https://n.sinaimg.cn/sinacn10112/566/w1018h1148/20191111/fd6e-iieqapt6530904.jpg")
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(image2)

    }


}
