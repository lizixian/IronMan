package com.avengers.ironman;

import android.app.Application;
import android.content.Context;

/**
 * 入口类
 * <p>
 * 功能：
 * app信息获取 (ing)
 * 手机信息获取 (ing)
 * 大图检测 (ing)
 * ANR检测
 * OOM检测
 * 函数耗时统计
 * 启动耗时统计
 * 流量监控
 * 耗电监控
 * FPS
 * CPU使用率
 * 内存占用
 * 页面加载时间
 * catch统计
 */
public class IronMan {

    private static Context context;
    /**
     * 文件大小阈值 kb为单位
     */
    private double largeImageFileSizeThreshold = 500.0;
    /**
     * 内存大小阈值 kb为单位
     */
    private double largeImageMemorySizeThreshold = 800.0;

    private IronMan() {
    }

    public static IronMan getInstance() {
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        private static final IronMan sInstance = new IronMan();
    }

    public static void init(Application application) {
        context = application;
    }

    public static Context getContext() {
        return context;
    }

    public double getLargeImageFileSizeThreshold() {
        return largeImageFileSizeThreshold;
    }

    public double getLargeImageMemorySizeThreshold() {
        return largeImageMemorySizeThreshold;
    }
}
