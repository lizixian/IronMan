package com.avengers.ironman.largeimage.aop;

import com.bumptech.glide.request.RequestListener;

import java.util.ArrayList;
import java.util.List;

public class GlideHook {

    public static List<RequestListener> process(List<RequestListener> requestListeners) {
        if (requestListeners == null) {
            requestListeners = new ArrayList<>();
        }
        requestListeners.add(new GlideLargeImageListener());
        return requestListeners;
    }
}
