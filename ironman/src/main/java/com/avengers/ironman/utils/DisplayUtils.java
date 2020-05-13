package com.avengers.ironman.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.blankj.utilcode.util.LogUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;

public class DisplayUtils {

    public static String getResolution(Context context) {
        return getWidthPixels(context) + "x" + getRealHeightPixels(context);
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
     * 获取屏幕尺寸
     *
     * @param context
     * @return
     */
    public static double getScreenInch(Activity context) {
        double inch = 0;

        try {
            int realWidth = 0, realHeight = 0;
            Display display = context.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                Point size = new Point();
                display.getRealSize(size);
                realWidth = size.x;
                realHeight = size.y;
            } else if (android.os.Build.VERSION.SDK_INT < 17
                    && android.os.Build.VERSION.SDK_INT >= 14) {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                realWidth = (Integer) mGetRawW.invoke(display);
                realHeight = (Integer) mGetRawH.invoke(display);
            } else {
                realWidth = metrics.widthPixels;
                realHeight = metrics.heightPixels;
            }

            inch = formatDouble(Math.sqrt((realWidth / metrics.xdpi) * (realWidth / metrics.xdpi) + (realHeight / metrics.ydpi) * (realHeight / metrics.ydpi)), 1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return inch;
    }

    /**
     * Double类型保留指定位数的小数，返回double类型（四舍五入）
     * newScale 为指定的位数
     */
    private static double formatDouble(double d, int newScale) {
        BigDecimal bd = new BigDecimal(d);
        return bd.setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
