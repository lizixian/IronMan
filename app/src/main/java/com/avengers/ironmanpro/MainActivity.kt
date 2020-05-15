package com.avengers.ironmanpro

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.avengers.ironman_analysis.ResultActivity
import com.yhao.floatwindow.FloatWindow


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


        startActivity(Intent(this, LargeImageTestActivity::class.java))

    }


}
