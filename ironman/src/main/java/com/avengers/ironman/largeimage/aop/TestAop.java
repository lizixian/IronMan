package com.avengers.ironman.largeimage.aop;

import android.view.View;

import com.bumptech.glide.request.SingleRequest;

/**
 * 查看ASM代码用
 */
public class TestAop {
    SingleRequest singleRequest;
    View view;

    public void test() {
        GlideHook.hookSingleRequest(singleRequest);
    }
}
