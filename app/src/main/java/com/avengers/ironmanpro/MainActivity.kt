package com.avengers.ironmanpro

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.avengers.ironman.utils.DeviceUtils
import com.avengers.ironman.utils.LogUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LogUtils.info(
            "getSDCardSpace = " + DeviceUtils.getSDCardSpace(this) + " getRomSpace = " +
                    DeviceUtils.getRomSpace(this)
        )
        LogUtils.info("是否Root = " + DeviceUtils.isDeviceRooted())
    }
}
