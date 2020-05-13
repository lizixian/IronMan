package com.avengers.ironman.largeimage.aop;

import android.graphics.Bitmap;
import android.net.Uri;

import com.avengers.ironman.IronMan;
import com.avengers.ironman.largeimage.LargeImageManager;
import com.squareup.picasso.Transformation;

public class PicassoTransformation implements Transformation {

    private Uri mUri;
    private int mResourceId;

    PicassoTransformation(Uri uri, int resourceId) {
        this.mUri = uri;
        this.mResourceId = resourceId;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        try {
            if (IronMan.get().largeImageConfig().isLargeImgOpen()) {
                if (mUri != null) {
                    LargeImageManager.getInstance().transform(mUri.toString(), source, "Picasso");
                } else {
                    LargeImageManager.getInstance().transform("" + mResourceId, source, "Picasso");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return source;
    }

    @Override
    public String key() {
        return "IronMan&Picasso&LargeBitmapTransformation";
    }
}
