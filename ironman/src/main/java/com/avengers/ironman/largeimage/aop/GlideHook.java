package com.avengers.ironman.largeimage.aop;

import android.app.Activity;
import android.view.View;

import com.avengers.ironman.largeimage.LargeImageManager;
import com.avengers.ironman.utils.ViewUtils;
import com.blankj.utilcode.util.ReflectUtils;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.SingleRequest;

import java.util.ArrayList;
import java.util.List;

public class GlideHook {

    /**
     * 拿到图片的url，尺寸，大小的等
     */
    public static void hookSingleRequest(Object singleRequest) {
        try {
            //拿到SingleRequest的requestListeners变量，往里面加一个回调
            List<RequestListener> requestListeners = null;
            if (singleRequest instanceof SingleRequest) {
                requestListeners = ReflectUtils.reflect(singleRequest).field("requestListeners").get();
            }
            //可能存在用户没有引入okhttp的情况
            if (requestListeners == null) {
                requestListeners = new ArrayList<>();
                requestListeners.add(new GlideLargeImageListener());
            } else {
                requestListeners.add(new GlideLargeImageListener());
            }
            if (singleRequest instanceof SingleRequest) {
                ReflectUtils.reflect(singleRequest).field("requestListeners", requestListeners);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 拿到图片的控件信息,ViewTarget 的构造方法会先于SingleRequest执行
     */
    public static void hookViewTarget(View view) {
        if (view != null) {
            String layoutLevel = ViewUtils.getLayoutLevel(view);
            String viewId = ViewUtils.getIdText(view);
            Activity activity = null;
            String packageName = "";
            if (view.getContext() != null && view.getContext() instanceof Activity) {
                activity = (Activity) view.getContext();
            }
            LargeImageManager.getInstance().saveViewTargetInfo(view, viewId, layoutLevel, activity);
        }
    }


}
