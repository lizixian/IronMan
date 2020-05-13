package com.avengers.ironman.largeimage.aop;

import android.graphics.Bitmap;
import android.view.View;

import androidx.annotation.Nullable;

import com.avengers.ironman.IronMan;
import com.avengers.ironman.largeimage.LargeImageManager;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class IronManImageLoadingListener implements ImageLoadingListener {

    @Nullable
    private ImageLoadingListener mOriginalImageLoadingListener;

    public IronManImageLoadingListener(ImageLoadingListener imageLoadingListener) {
        this.mOriginalImageLoadingListener = imageLoadingListener;
    }

    @Override
    public void onLoadingStarted(String imageUri, View view) {
        if (mOriginalImageLoadingListener != null) {
            mOriginalImageLoadingListener.onLoadingStarted(imageUri, view);
        }
    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        if (mOriginalImageLoadingListener != null) {
            mOriginalImageLoadingListener.onLoadingFailed(imageUri, view, failReason);
        }
    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        try {
            if (IronMan.get().largeImageConfig().isLargeImgOpen()) {
                LargeImageManager.getInstance().transform(imageUri, loadedImage, "ImageLoader", loadedImage.getWidth(), loadedImage.getHeight());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mOriginalImageLoadingListener != null) {
            mOriginalImageLoadingListener.onLoadingComplete(imageUri, view, loadedImage);
        }
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {
        if (mOriginalImageLoadingListener != null) {
            mOriginalImageLoadingListener.onLoadingCancelled(imageUri, view);
        }
    }
}
