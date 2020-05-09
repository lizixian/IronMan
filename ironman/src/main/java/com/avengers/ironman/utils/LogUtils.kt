package com.avengers.ironman.utils

import android.util.Log

object LogUtils {

    private const val TAG = "IronMan"

    fun info(msg: String) {
        Log.i(TAG, msg)
    }

    fun debug(msg: String) {
        Log.d(TAG, msg)
    }

    fun error(msg: String) {
        Log.e(TAG, msg)
    }
}