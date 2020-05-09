package com.avengers.ironman.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.text.format.Formatter
import android.util.DisplayMetrics
import android.view.WindowManager
import java.io.File

object DeviceUtils {

    private fun isHigherJellyBeanMr2(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2

    /**
     * 获取 PackageInfo
     */
    fun getPackageInfo(context: Context): PackageInfo? {
        var info: PackageInfo? = null
        try {
            val pm = context.packageManager
            info = pm.getPackageInfo(context.packageName, PackageManager.GET_CONFIGURATIONS)
        } catch (ex: Exception) {
            ex.printStackTrace()
            LogUtils.info(ex.message ?: "getPackageInfo#Error info is null")
        }
        return info
    }

    /**
     * 获得SD卡总大小
     */
    fun getSdcardTotalSize(context: Context?): String {
        val statFs = StatFs(Environment.getExternalStorageDirectory().path)
        val blockSize =
            if (isHigherJellyBeanMr2()) statFs.blockSizeLong else statFs.blockSize.toLong()
        val totalBlocks =
            if (isHigherJellyBeanMr2()) statFs.blockCountLong else statFs.blockCount.toLong()
        return Formatter.formatFileSize(context, blockSize.times(totalBlocks))
    }

    /**
     * 获得sd卡剩余容量，即可用大小
     */
    fun getSdcardAvailableSize(context: Context?): String {
        val statFs = StatFs(Environment.getExternalStorageDirectory().path)
        val blockSize =
            if (isHigherJellyBeanMr2()) statFs.blockSizeLong else statFs.blockSize.toLong()
        val availableBlocks =
            if (isHigherJellyBeanMr2()) statFs.availableBlocksLong else statFs.availableBlocks.toLong()
        return Formatter.formatFileSize(context, blockSize.times(availableBlocks))
    }

    /**
     * 获得机身内存总大小
     */
    private fun getRomTotalSize(context: Context?): String? {
        val statFs = StatFs(Environment.getDataDirectory().path)
        val blockSize =
            if (isHigherJellyBeanMr2()) statFs.blockSizeLong else statFs.blockSize.toLong()
        val totalBlocks =
            if (isHigherJellyBeanMr2()) statFs.blockCountLong else statFs.blockCount.toLong()
        return Formatter.formatFileSize(context, blockSize.times(totalBlocks))
    }

    /**
     * 获得机身可用内存
     */
    private fun getRomAvailableSize(context: Context?): String? {
        val statFs = StatFs(Environment.getDataDirectory().path)
        val blockSize =
            if (isHigherJellyBeanMr2()) statFs.blockSizeLong else statFs.blockSize.toLong()
        val availableBlocks =
            if (isHigherJellyBeanMr2()) statFs.availableBlocksLong else statFs.availableBlocks.toLong()
        return Formatter.formatFileSize(context, blockSize.times(availableBlocks))
    }

    fun getSDCardSpace(context: Context?): String? {
        return try {
            val free: String = getSdcardAvailableSize(context)
            val total: String = getSdcardTotalSize(context)
            "$free/$total"
        } catch (e: java.lang.Exception) {
            "-/-"
        }
    }

    fun getRomSpace(context: Context?): String? {
        return try {
            val free = getRomAvailableSize(context!!)
            val total = getRomTotalSize(context)
            "$free/$total"
        } catch (e: java.lang.Exception) {
            "-/-"
        }
    }

    /**
     * 获取手机型号
     */
    fun getPhoneModel(): String {
        return Build.MANUFACTURER + " " + Build.MODEL
    }

    /**
     * 获取系统版本
     */
    fun getPhoneSysVersion(): String {
        return Build.VERSION.RELEASE
    }

    /**
     * 获取SDK版本
     */
    fun getSdkInt(): Int {
        return Build.VERSION.SDK_INT
    }

    /**
     * 分辨率
     */
    fun getWidthPixels(context: Context?): Int {
        val metrics = DisplayMetrics()
        val windowManager =
            context?.applicationContext?.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        windowManager?.defaultDisplay?.getMetrics(metrics)
        return windowManager?.let { metrics.widthPixels } ?: 0
    }

    /**
     * 分辨率
     */
    fun getHeightPixels(context: Context?): Int {
        return getRealHeightPixels(context) - getStatusBarHeight(context)
    }

    /**
     * 分辨率
     */
    fun getRealHeightPixels(context: Context?): Int {
        val windowManager =
            context?.applicationContext?.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        var height = 0
        val display = windowManager?.defaultDisplay
        val dm = DisplayMetrics()
        val c: Class<*>
        try {
            c = Class.forName("android.view.Display")
            val method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
            method.invoke(display, dm)
            height = dm.heightPixels
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtils.info("getRealHeightPixels = " + e.message)
        }
        return height
    }

    /**
     * 状态栏高度
     */
    fun getStatusBarHeight(context: Context?): Int {
        val resources = context?.applicationContext?.getResources()
        val resourceId = resources?.getIdentifier("status_bar_height", "dimen", "android")
        return resourceId?.let { resources?.getDimensionPixelSize(it) } ?: 0
    }

    /**
     * 是否Root
     */
    fun isDeviceRooted(): Boolean {
        val su = "su"
        val locations = arrayOf(
            "/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
            "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/",
            "/system/sbin/", "/usr/bin/", "/vendor/bin/"
        )
        val location = locations.filter { File(it + su).exists() }
        return location.isNotEmpty()
    }

    /**
     *
     */
    fun getScreenDensity(): Float {
        return Resources.getSystem().displayMetrics.density
    }

}


