package com.avengers.ironman.largeimage

import android.graphics.Bitmap

class LargeImageInfo {
    var id: String? = ""
    var framework = "other"  //框架
    var url: String? = null
    var fileSize = 0.0 //文件大小
    var memorySize = 0.0 //内存大小
    var width = 0  //图片宽高
    var height = 0
    var viewWidth = 0 //控件宽高
    var viewHeight = 0
    var activity: String? = null //控件所在的activity名字
    var layoutLevel: String? = null //布局层级
    var viewString = "" //控件信息
    var bitmap: Bitmap? = null
    var phoneModel: String? = null //手机型号
    var phoneSysVersion: String? = null  //手机型号
    var sdcardSpace: String? = null  //sd卡剩余空间
    var romSpace: String? = null  //系统剩余空间
    var memorySpace: String? = null //内存占用

    override fun toString(): String {
        return "LargeImageInfo{" +
                "framework='" + framework + '\'' +
                ", url='" + url + '\'' +
                ", fileSize=" + fileSize +
                ", memorySize=" + memorySize +
                ", width=" + width +
                ", height=" + height +
                ", viewWidth=" + viewWidth +
                ", viewHeight=" + viewHeight +
                ", activity='" + activity + '\'' +
                ", layoutLevel='" + layoutLevel + '\'' +
                ", viewString=" + viewString +
                '}'
    }
}

fun largeImageInfoDsl(init: LargeImageInfo.() -> Unit): LargeImageInfo {
    val info = LargeImageInfo()
    info.init()
    return info
}