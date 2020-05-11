package com.avengers.ironmanpro

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        LogUtils.info(
//            "getSDCardSpace = " + DeviceUtils.getSDCardSpace(this) + " getRomSpace = " +
//                    DeviceUtils.getRomSpace(this)
//        )
//        LogUtils.info("是否Root = " + DeviceUtils.isDeviceRooted())
        Glide.with(this)
            .load("https://n.sinaimg.cn/sinacn10112/566/w1018h1148/20191111/fd6e-iieqapt6530904.jpg")
            .into(image)
        image.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background))
        aaa()
    }

    fun aaa(){

    }
}
