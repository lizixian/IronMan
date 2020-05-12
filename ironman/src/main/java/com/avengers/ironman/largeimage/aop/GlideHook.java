package com.avengers.ironman.largeimage.aop;

import android.util.Log;

import com.bumptech.glide.request.RequestListener;

import java.util.ArrayList;
import java.util.List;

public class GlideHook {
    public static List<RequestListener> process(List<RequestListener> requestListener) {
        Log.i("XIAN","GlideHook");
        if (requestListener == null) {
            requestListener = new ArrayList<>();
        }
        requestListener.add(new GlideLargeImageListener());
        return requestListener;
    }
}
