package com.avengers.ironmanpro

import android.app.Application
import com.avengers.ironman.IronMan

class IronManApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        IronMan.init(this)
    }
}