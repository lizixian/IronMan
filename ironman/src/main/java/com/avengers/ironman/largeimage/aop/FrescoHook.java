package com.avengers.ironman.largeimage.aop;

import android.net.Uri;

import com.facebook.imagepipeline.request.Postprocessor;

public class FrescoHook {

    public static Postprocessor proxy(Uri uri, Postprocessor postprocessor) {
        return new FrescoPostprocessor(uri, postprocessor);
    }

}
