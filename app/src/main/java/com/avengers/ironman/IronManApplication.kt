package com.avengers.ironman

import android.app.Application

class IronManApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        IronMan.init(this)
    }
}