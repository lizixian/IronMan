package com.avengers.ironman.largeimage

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import com.avengers.ironman.IronMan
import com.avengers.ironman.MainLooper
import com.avengers.ironman.utils.ConvertUtils
import com.avengers.ironman.utils.DeviceUtils
import com.avengers.ironman.utils.MD5
import java.util.*

class LargeImageManager private constructor() {
    private var tempView: View? = null
    private var tempLayoutLevel: String? = null
    private var tempActivity: Activity? = null


    fun saveViewTargetInfo(view: View?, layoutLevel: String?, activity: Activity?) {
        this.tempView = view
        this.tempLayoutLevel = layoutLevel
        this.tempActivity = activity
    }

    fun transform(
        imageUrl: String?,
        bitmapDrawable: BitmapDrawable?,
        framework: String?
    ): Bitmap? {
        val sourceBitmap = ConvertUtils.drawable2Bitmap(bitmapDrawable)
        return transform(imageUrl, sourceBitmap, framework)
    }

    fun transform(
        imageUrl: String?,
        sourceBitmap: Bitmap?,
        framework: String?
    ): Bitmap? {
        if (null == sourceBitmap) {
            return null
        }
        saveImageInfo(
            imageUrl,
            sourceBitmap.byteCount,
            sourceBitmap.width,
            sourceBitmap.height,
            framework,
            sourceBitmap
        )
        return sourceBitmap
    }

    private fun saveImageInfo(
        imageUrl: String?,
        byteCount: Int,
        width: Int,
        height: Int,
        framework: String?,
        sourceBitmap: Bitmap
    ) {
        if (byteCount <= 0) {
            return
        }
        //图片大小
        val memorySize = ConvertUtils.byte2MemorySize(byteCount.toLong(), ConvertUtils.MB)
        val fileSizeThreshold = IronMan.get().largeImageConfig().fileSizeThreshold
        val memorySizeThreshold = IronMan.get().largeImageConfig().memorySizeThreshold
        val largeImageInfo: LargeImageInfo?
        if (lagerImageCache.containsKey(imageUrl)) {
            largeImageInfo = lagerImageCache[imageUrl]
            if (largeImageInfo != null && (memorySize > memorySizeThreshold || largeImageInfo.fileSize > fileSizeThreshold)) {
                createLargeImageInfo(
                    largeImageInfo,
                    imageUrl,
                    framework,
                    memorySize,
                    width,
                    height,
                    sourceBitmap
                )
            } else {
                lagerImageCache.remove(imageUrl)
            }
        } else {
            if (memorySize > memorySizeThreshold) {
                largeImageInfo = LargeImageInfo()
                createLargeImageInfo(
                    largeImageInfo,
                    imageUrl,
                    framework,
                    memorySize,
                    width,
                    height,
                    sourceBitmap
                )
            }
        }
    }

    private fun createLargeImageInfo(
        largeImageInfo: LargeImageInfo?,
        imageUrl: String?,
        framework: String?,
        memorySize: Double,
        width: Int,
        height: Int,
        sourceBitmap: Bitmap
    ) {
        MainLooper.runOnUiThread {
            largeImageInfo?.id = MD5.hexdigest(imageUrl)
            largeImageInfo?.framework = framework ?: "other"
            largeImageInfo?.url = imageUrl
            largeImageInfo?.memorySize = memorySize
            largeImageInfo?.width = width
            largeImageInfo?.height = height
            largeImageInfo?.viewWidth = tempView?.measuredWidth ?: 0
            largeImageInfo?.viewHeight = tempView?.measuredHeight ?: 0
            largeImageInfo?.viewString = tempView.toString()
            largeImageInfo?.bitmap = sourceBitmap
            largeImageInfo?.layoutLevel = tempLayoutLevel ?: "can not get layoutLevel"
            largeImageInfo?.activity =
                tempActivity?.javaClass?.simpleName ?: "can not get activity name"
            largeImageInfo?.phoneModel = DeviceUtils.getPhoneModel()
            largeImageInfo?.phoneSysVersion = DeviceUtils.getPhoneSysVersion()
            largeImageInfo?.sdcardSpace = DeviceUtils.getSDCardSpace(IronMan.getContext())
            largeImageInfo?.romSpace = DeviceUtils.getRomSpace(IronMan.getContext())
            largeImageInfo?.memorySpace = DeviceUtils.getMemorySpace(IronMan.getContext())
            lagerImageCache[imageUrl] = largeImageInfo!!
        }
    }

    companion object {
        var lagerImageCache: MutableMap<String?, LargeImageInfo> = HashMap()

        @JvmStatic
        val instance: LargeImageManager by lazy { LargeImageManager() }
    }
}