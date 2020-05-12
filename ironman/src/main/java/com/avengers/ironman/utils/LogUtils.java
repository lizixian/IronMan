package com.avengers.ironman.utils;

import android.util.Log;

public class LogUtils {
    private static final String TAG = "IronMan";

    public static void info(String msg) {
        Log.i(TAG, msg);
    }

    public static void debug(String msg) {
        Log.d(TAG, msg);
    }

    public static void error(String msg) {
        Log.e(TAG, msg);
    }
}
