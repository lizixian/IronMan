package com.avengers.ironman.largeimage;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.avengers.ironman.IronMan;
import com.avengers.ironman.utils.ConvertUtils;

public class LargeImageManager {
    private LargeImageManager() {
    }

    public static LargeImageManager getInstance() {
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        private static final LargeImageManager sInstance = new LargeImageManager();
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
        mMainHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(IronMan.getContext(), "imageUrl = ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    Handler mMainHandler = new Handler(Looper.getMainLooper());

}
