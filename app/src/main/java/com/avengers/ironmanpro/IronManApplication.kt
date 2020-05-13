package com.avengers.ironmanpro

import android.app.Application
import com.avengers.ironman.IronMan

class IronManApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        IronMan.init(this)
        IronMan.get().largeImageConfig().apply {
            isLargeImgOpen = true
            fileSizeThreshold = 500.0
            memorySizeThreshold = 800.0
        }
    }
}