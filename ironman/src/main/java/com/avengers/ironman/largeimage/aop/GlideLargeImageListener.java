package com.avengers.ironman.largeimage.aop;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import androidx.annotation.Nullable;

import com.avengers.ironman.IronMan;
import com.avengers.ironman.largeimage.LargeImageManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;


public class GlideLargeImageListener<R> implements RequestListener<R> {
    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<R> target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(R resource, Object model, Target<R> target, DataSource dataSource, boolean isFirstResource) {
        try {
            if (IronMan.get().largeImageConfig().isLargeImgOpen()) {
                if (resource instanceof Bitmap) {
                    LargeImageManager.getInstance().transform(model.toString(), (Bitmap) resource, "Glide");
                } else if (resource instanceof BitmapDrawable) {
                    LargeImageManager.getInstance().transform(model.toString(), (BitmapDrawable) resource, "Glide");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
