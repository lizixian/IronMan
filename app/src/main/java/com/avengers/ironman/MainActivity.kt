package com.avengers.ironman

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.avengers.ironman.utils.DeviceUtils
import com.avengers.ironman.utils.LogUtils

class MainActivity : AppCompatActivity() {

    /**
     * app信息获取
     * 手机信息获取
     * 大图检测
     * ANR检测
     * OOM检测
     * 函数耗时统计
     * 启动耗时统计
     * 流量监控
     * 耗电监控
     * FPS
     * CPU使用率
     * 内存占用
     * 页面加载时间
     * catch统计
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LogUtils.info(
            "getRomSpace = " + DeviceUtils.getRomSpace(this) + " getSDCardSpace = " + DeviceUtils.getSDCardSpace(
                this
            )
        )


    }
}
