package com.avengers.ironmanpro

import android.app.Application
import com.avengers.ironman.IronMan


class IronManApplication : Application() {
    companion object {
        var application: Application? = null
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        IronMan.init(this)
        IronMan.get().largeImageConfig().apply {
            isLargeImgOpen = true
            fileSizeThreshold = 0.0
            memorySizeThreshold = 0.0
        }
    }
}