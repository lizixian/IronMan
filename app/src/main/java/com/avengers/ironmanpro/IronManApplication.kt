package com.avengers.ironmanpro

import android.app.Application
import com.avengers.ironman.IronMan
import com.avengers.ironman.utils.SpUtil

class IronManApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SpUtil.getInstance().init(this)
        IronMan.init(this)
    }
}