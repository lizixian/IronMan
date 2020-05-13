package com.avengers.ironman.largeimage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.View;

import com.avengers.ironman.IronMan;
import com.avengers.ironman.MainLooper;
import com.avengers.ironman.utils.ConvertUtils;
import com.avengers.ironman.utils.MD5;
import com.blankj.utilcode.util.LogUtils;

import java.util.HashMap;
import java.util.Map;

public class LargeImageManager {

    public static Map<String, LargeImageInfo> lagerImageCache = new HashMap<>();
    private View view;
    private String layoutLevel;
    private Activity activity;

    private LargeImageManager() {
    }

    public static LargeImageManager getInstance() {
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        private static final LargeImageManager sInstance = new LargeImageManager();
    }

    public void saveViewTargetInfo(View view, String layoutLevel, Activity activity) {
        this.view = view;
        this.layoutLevel = layoutLevel;
        this.activity = activity;
    }

    public Bitmap transform(String imageUrl, BitmapDrawable bitmapDrawable, String framework, int targetWidth, int targetHeight) {
        Bitmap sourceBitmap = ConvertUtils.drawable2Bitmap(bitmapDrawable);
        return transform(imageUrl, sourceBitmap, framework, targetWidth, targetHeight);
    }

    public Bitmap transform(String imageUrl, Bitmap sourceBitmap, String framework, int targetWidth, int targetHeight) {
        if (null == sourceBitmap) {
            return null;
        }
        saveImageInfo(imageUrl, sourceBitmap.getByteCount(), sourceBitmap.getWidth(), sourceBitmap.getHeight(), framework,
                targetWidth, targetHeight, sourceBitmap);
        return sourceBitmap;
    }

    private void saveImageInfo(String imageUrl, int byteCount, int width, int height, String framework, int targetWidth, int targetHeight, Bitmap sourceBitmap) {
        if (byteCount <= 0) {
            return;
        }
        //图片大小
        double memorySize = ConvertUtils.byte2MemorySize(byteCount, ConvertUtils.MB);
        double fileSizeThreshold = IronMan.get().largeImageConfig().getFileSizeThreshold();
        double memorySizeThreshold = IronMan.get().largeImageConfig().getMemorySizeThreshold();
        LargeImageInfo largeImageInfo;
        if (lagerImageCache.containsKey(imageUrl)) {
            largeImageInfo = lagerImageCache.get(imageUrl);
            if (largeImageInfo != null && (memorySize > memorySizeThreshold || largeImageInfo.getFileSize() > fileSizeThreshold)) {
                createLargeImageInfo(imageUrl, width, height, framework, memorySize, largeImageInfo);
                lagerImageCache.put(imageUrl, largeImageInfo);
            } else {
                lagerImageCache.remove(imageUrl);
            }
        } else {
            if (memorySize > memorySizeThreshold) {
                largeImageInfo = new LargeImageInfo();
                createLargeImageInfo(imageUrl, width, height, framework, memorySize, largeImageInfo);
                lagerImageCache.put(imageUrl, largeImageInfo);
            }
        }
    }

    private void createLargeImageInfo(final String imageUrl, final int width, final int height, final String framework, final double memorySize, final LargeImageInfo largeImageInfo) {
        //post一下主要是想获取宽高
        MainLooper.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                largeImageInfo.setId(MD5.hexdigest(imageUrl));
                largeImageInfo.setFramework(framework);
                largeImageInfo.setUrl(imageUrl);
                largeImageInfo.setMemorySize(memorySize);
                largeImageInfo.setWidth(width);
                largeImageInfo.setHeight(height);
                largeImageInfo.setViewWidth(view != null ? view.getMeasuredWidth() : -1);
                largeImageInfo.setViewHeight(view != null ? view.getMeasuredHeight() : -1);
                largeImageInfo.setLayoutId(view != null ? view.getId() : -1);
                largeImageInfo.setLayoutLevel(!TextUtils.isEmpty(layoutLevel) ? layoutLevel : "can not get layoutLevel");
                largeImageInfo.setActivity(activity != null ? activity.getClass().getSimpleName() : "can not get activity name");
            }
        });
    }


}
