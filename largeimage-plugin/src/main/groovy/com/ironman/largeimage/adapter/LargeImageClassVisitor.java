package com.ironman.largeimage.adapter;

import com.ironman.largeimage.LargeImageConfig;
import com.ironman.largeimage.method.FrescoMethodAdapter;
import com.ironman.largeimage.method.GlideMethodAdapter;
import com.ironman.largeimage.method.ImageLoaderMethodAdapter;
import com.ironman.largeimage.method.PicassoMethodAdapter;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LargeImageClassVisitor extends ClassVisitor {
    /**
     * 当前类名
     */
    private String className;

    public LargeImageClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String methodName, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, methodName, descriptor, signature, exceptions);
        if (!LargeImageConfig.getInstance().isLargeImagePluginSwitch()) {
            return mv;
        }
        //拦截 glide 的 SingleRequest 类的构造方法
        boolean interceptSingleRequest = className.equals("com/bumptech/glide/request/SingleRequest") &&
                (methodName.equals("init") || methodName.equals("<init>"));
        //拦截 glide 的 ViewTarget 类的构造方法
        boolean interceptViewTarget = className.equals("com/bumptech/glide/request/target/ViewTarget") &&
                (methodName.equals("init") || methodName.equals("<init>"));
        if ((interceptSingleRequest || interceptViewTarget) && descriptor != null) {
            return mv == null ? null : new GlideMethodAdapter(mv, access, className, methodName, descriptor);
        }

        //对picasso的Request类的构造方法进行字节码修改
        if (className.equals("com/squareup/picasso/Request") && methodName.equals("<init>") && descriptor != null) {
            return mv == null ? null : new PicassoMethodAdapter(mv, access, methodName, descriptor);
        }

        //Fresco字节码替换
        if (className.equals("com/facebook/imagepipeline/request/ImageRequest") && methodName.equals("<init>") && descriptor != null) {
            //创建MethodVisitor代理
            return mv == null ? null : new FrescoMethodAdapter(mv, access, methodName, descriptor);
        }

        //imageLoader字节码替换
        if (className.equals("com/nostra13/universalimageloader/core/ImageLoadingInfo") && methodName.equals("<init>") && descriptor != null) {
            //创建MethodVisitor代理
            return mv == null ? null : new ImageLoaderMethodAdapter(mv, access, methodName, descriptor);
        }

        return mv;
    }
}
