package com.avengers.ironman.largeimage

class LargeImageConfig {
    /**
     * 文件大小阈值 kb为单位
     */
    var fileSizeThreshold = 500.0

    /**
     * 内存大小阈值 kb为单位
     */
    var memorySizeThreshold = 800.0

    /**
     * 开关
     */
    var isLargeImgOpen = true

    fun setFileSizeThreshold(fileSizeThreshold: Double): LargeImageConfig = apply {
        this.fileSizeThreshold = fileSizeThreshold
    }

    fun setMemorySizeThreshold(memorySizeThreshold: Double): LargeImageConfig = apply {
        this.memorySizeThreshold = memorySizeThreshold
    }

    fun setLargeImgOpen(largeImgOpen: Boolean): LargeImageConfig = apply {
        isLargeImgOpen = largeImgOpen
    }
}