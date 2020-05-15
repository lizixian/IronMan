package com.avengers.ironman_analysis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.avengers.ironman.largeimage.LargeImageManager
import kotlinx.android.synthetic.main.layout_large_image_detail.*

class LargeImageDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_large_image_detail)
        val id = intent.getStringExtra("id")
        val largeInfo = LargeImageManager.lagerImageCache[id]
        imageView.setImageBitmap(largeInfo?.bitmap)
    }

}