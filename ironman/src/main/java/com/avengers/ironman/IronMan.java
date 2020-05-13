package com.avengers.ironman;

import android.app.Application;
import android.content.Context;

import com.avengers.ironman.largeimage.LargeImageConfig;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ProcessUtils;
import com.blankj.utilcode.util.Utils;

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

    private static volatile IronMan sIronMan;
    private static volatile boolean isInitializing = false;
    private static volatile boolean alreadyInit = false;
    private LargeImageConfig largeImageConfig;

    private IronMan() {
        largeImageConfig = new LargeImageConfig();
    }

    public static void init(Application application) {
        if (alreadyInit) {
            return;
        }
        alreadyInit = true;
        context = application;
        initAndroidUtil(application);
        if (!ProcessUtils.isMainProcess()) {
            return;
        }
        get();
    }

    public static IronMan get() {
        if (sIronMan == null) {
            synchronized (IronMan.class) {
                if (sIronMan == null) {
                    checkAndInitializeIronMan();
                }
            }
        }
        return sIronMan;
    }

    private static void checkAndInitializeIronMan() {
        if (isInitializing) {
            throw new IllegalArgumentException("checkAndInitializeIronMan");
        }
        isInitializing = true;
        try {
            initializeIronMan();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            isInitializing = false;
        }
    }

    private static void initializeIronMan() {
        sIronMan = new IronMan();
    }

    public LargeImageConfig largeImageConfig() {
        return largeImageConfig;
    }

    private static void initAndroidUtil(Application application) {
        Utils.init(application);
        LogUtils.getConfig().setLogSwitch(true)
                .setConsoleSwitch(true)
                .setGlobalTag("IronMan")
                .setLogHeadSwitch(true)
                .setLog2FileSwitch(false)
                .setBorderSwitch(true)
                .setSingleTagSwitch(true)
                .setStackDeep(2);
    }

    public static Context getContext() {
        return context;
    }

}
