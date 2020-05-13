package com.avengers.ironman.largeimage.aop;

import com.blankj.utilcode.util.ReflectUtils;
import com.squareup.picasso.Request;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

public class PicassoHook {
    /**
     * 注入到com.squareup.picasso.Request 构造方法中
     */
    public static void proxy(Object request) {
        try {
            if (request instanceof Request) {
                Request requestObj = (Request) request;
                List<Transformation> transformations = requestObj.transformations;
                if (transformations == null) {
                    transformations = new ArrayList<>();
                    transformations.add(new PicassoTransformation(requestObj.uri, requestObj.resourceId));
                } else {
                    transformations.clear();
                    transformations.add(new PicassoTransformation(requestObj.uri, requestObj.resourceId));
                }
                ReflectUtils.reflect(request).field("transformations", transformations);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
