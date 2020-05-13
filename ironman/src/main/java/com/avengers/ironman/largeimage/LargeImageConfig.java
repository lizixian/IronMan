package com.avengers.ironman.largeimage;

public class LargeImageConfig {
    /**
     * 文件大小阈值 kb为单位
     */
    private double fileSizeThreshold = 500.0;
    /**
     * 内存大小阈值 kb为单位
     */
    private double memorySizeThreshold = 800.0;

    /**
     * 开关
     */
    private boolean isLargeImgOpen = true;

    public double getFileSizeThreshold() {
        return fileSizeThreshold;
    }

    public LargeImageConfig setFileSizeThreshold(double fileSizeThreshold) {
        this.fileSizeThreshold = fileSizeThreshold;
        return this;
    }

    public double getMemorySizeThreshold() {
        return memorySizeThreshold;
    }

    public LargeImageConfig setMemorySizeThreshold(double memorySizeThreshold) {
        this.memorySizeThreshold = memorySizeThreshold;
        return this;
    }

    public boolean isLargeImgOpen() {
        return isLargeImgOpen;
    }

    public LargeImageConfig setLargeImgOpen(boolean largeImgOpen) {
        isLargeImgOpen = largeImgOpen;
        return this;
    }
}
