package com.avengers.ironman.largeimage.aop;

import android.view.View;

import com.bumptech.glide.request.RequestListener;

import java.util.List;

/**
 * 查看ASM代码用
 */
public class TestAop {
    List<RequestListener> requestListeners;
    View view;

    public void test() {
//        GlideHook.hookSingleRequest(requestListeners);
        GlideHook.hookViewTarget(view);
    }
}
