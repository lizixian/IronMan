package com.avengers.ironman.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.text.format.Formatter

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
}


