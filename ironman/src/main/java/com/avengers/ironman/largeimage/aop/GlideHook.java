package com.avengers.ironman.largeimage.aop;

import android.app.Activity;
import android.view.View;

import com.avengers.ironman.largeimage.LargeImageManager;
import com.avengers.ironman.utils.ViewUtils;
import com.bumptech.glide.request.RequestListener;

import java.util.ArrayList;
import java.util.List;

public class GlideHook {

    /**
     * 拿到图片的url，尺寸，大小的等
     */
    public static List<RequestListener> hookSingleRequest(List<RequestListener> requestListeners) {
        if (requestListeners == null) {
            requestListeners = new ArrayList<>();
        }
        requestListeners.add(new GlideLargeImageListener());
        return requestListeners;
    }

    /**
     * 拿到图片的控件信息,ViewTarget 的构造方法会先于SingleRequest执行
     */
    public static void hookViewTarget(View view) {
        if (view != null) {
            String layoutLevel = ViewUtils.getLayoutLevel(view);
            Activity activity = null;
            if (view.getContext() != null && view.getContext() instanceof Activity) {
                activity = (Activity) view.getContext();
            }
            LargeImageManager.getInstance().saveViewTargetInfo(view, layoutLevel, activity);
        }
    }


}
