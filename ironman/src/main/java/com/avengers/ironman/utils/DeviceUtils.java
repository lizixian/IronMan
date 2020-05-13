package com.avengers.ironman.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.blankj.utilcode.util.LogUtils;

import java.io.File;
import java.lang.reflect.Method;

public class DeviceUtils {
    @SuppressLint("ObsoleteSdkInt")
    public static boolean isHigherJellyBeanMr2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    /**
     * 获取 PackageInfo
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            PackageManager pm = context.getPackageManager();
            info = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return info;
    }

    /**
     * 获得SD卡总大小
     */
    public static String getSdcardTotalSize(Context context) {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long blockSize = isHigherJellyBeanMr2() ? statFs.getBlockSizeLong() : statFs.getBlockSize();
        long totalBlocks = isHigherJellyBeanMr2() ? statFs.getBlockCountLong() : statFs.getBlockCount();
        return Formatter.formatFileSize(context, blockSize * totalBlocks);
    }

    /**
     * 获得sd卡剩余容量，即可用大小
     */
    public static String getSdcardAvailableSize(Context context) {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long blockSize = isHigherJellyBeanMr2() ? statFs.getBlockSizeLong() : statFs.getBlockSize();
        long availableBlocks = isHigherJellyBeanMr2() ? statFs.getAvailableBlocksLong() : statFs.getAvailableBlocks();
        return Formatter.formatFileSize(context, blockSize * availableBlocks);
    }

    /**
     * 获得机身内存总大小
     */
    public static String getRomTotalSize(Context context) {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        long blockSize = isHigherJellyBeanMr2() ? statFs.getBlockSizeLong() : statFs.getBlockSize();
        long totalBlocks = isHigherJellyBeanMr2() ? statFs.getBlockCountLong() : statFs.getBlockCount();
        return Formatter.formatFileSize(context, blockSize * totalBlocks);
    }

    /**
     * 获得机身可用内存
     */
    public static String getRomAvailableSize(Context context) {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        long blockSize = isHigherJellyBeanMr2() ? statFs.getBlockSizeLong() : statFs.getBlockSize();
        long availableBlocks = isHigherJellyBeanMr2() ? statFs.getAvailableBlocksLong() : statFs.getAvailableBlocks();
        return Formatter.formatFileSize(context, blockSize * availableBlocks);
    }

    public static String getSDCardSpace(Context context) {
        try {
            String free = getSdcardAvailableSize(context);
            String total = getSdcardTotalSize(context);
            return free + "/" + total;
        } catch (Exception ex) {
            return "-/-";
        }
    }

    public static String getRomSpace(Context context) {
        try {
            String free = getRomAvailableSize(context);
            String total = getRomTotalSize(context);
            return free + "/" + total;
        } catch (Exception ex) {
            return "-/-";
        }
    }

    /**
     * 内存占用
     *
     * @return
     */
    public static String memoryUsage(Context context) {
        Runtime runtime = Runtime.getRuntime();
        long memoryUsage = (runtime.totalMemory() - runtime.freeMemory()) >> 10;
        return Formatter.formatFileSize(context, memoryUsage);
    }

    public static String memoryUsageRate(Context context) {
        Runtime runtime = Runtime.getRuntime();
        long totalSize = runtime.maxMemory() >> 10;
        long memoryUsage = (runtime.totalMemory() - runtime.freeMemory()) >> 10;
        long memoryUsageRate = memoryUsage * 100 / totalSize;
        return Formatter.formatFileSize(context, memoryUsageRate);
    }

    public static String getMemorySpace(Context context) {
        try {
            return memoryUsage(context) + "/" + memoryUsageRate(context);
        } catch (Exception ex) {
            return "-/-";
        }
    }

    /**
     * 获取手机型号
     */
    public static String getPhoneModel() {
        return Build.MANUFACTURER + " " + Build.MODEL;
    }

    /**
     * 获取系统版本
     */
    public static String getPhoneSysVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取SDK版本
     */
    public static int getSdkInt() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 分辨率
     */
    public static int getWidthPixels(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager =
                (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(metrics);
            return metrics.widthPixels;
        } else {
            return 0;
        }
    }

    /**
     * 分辨率
     */
    public static int getHeightPixels(Context context) {
        return getRealHeightPixels(context) - getStatusBarHeight(context);
    }

    /**
     * 分辨率
     */
    public static int getRealHeightPixels(Context context) {
        WindowManager windowManager =
                (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        int height = 0;
        Display display = windowManager != null ? windowManager.getDefaultDisplay() : null;
        DisplayMetrics dm = new DisplayMetrics();
        Class<?> c;
        try {
            c = Class.forName("android.view.Display");
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            height = dm.heightPixels;
        } catch (Exception ex) {
            ex.printStackTrace();
            LogUtils.i("getRealHeightPixels = " + ex.getMessage());
        }
        return height;
    }

    /**
     * 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getApplicationContext().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 是否Root
     */
    public static boolean isDeviceRooted() {
        String su = "su";
        String[] locations = new String[]{
                "/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
                "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/",
                "/system/sbin/", "/usr/bin/", "/vendor/bin/"
        };
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     */
    public static float getScreenDensity() {
        return Resources.getSystem().getDisplayMetrics().density;
    }
}
