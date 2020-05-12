package com.avengers.ironman.largeimage.aop;

import com.bumptech.glide.request.RequestListener;

import java.util.List;

/**
 * 查看ASM代码用
 */
public class TestAop {
    List<RequestListener> requestListeners;

    public void test() {
        GlideHook.process(requestListeners);
    }
}
