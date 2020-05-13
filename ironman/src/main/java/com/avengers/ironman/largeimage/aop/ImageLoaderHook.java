package com.avengers.ironman.largeimage.aop;

import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageLoaderHook {
    public static ImageLoadingListener proxy(ImageLoadingListener imageLoadingListener) {
        return new IronManImageLoadingListener(imageLoadingListener);
    }
}
