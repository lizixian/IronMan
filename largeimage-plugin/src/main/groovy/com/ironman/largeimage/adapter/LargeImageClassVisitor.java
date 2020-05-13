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
        boolean isConstructor = methodName.equals("init") || methodName.equals("<init>");
        if (isConstructor && descriptor != null) {
            switch (className) {
                case "com/bumptech/glide/request/SingleRequest":
                case "com/bumptech/glide/request/target/ViewTarget":
                    return mv == null ? null : new GlideMethodAdapter(mv, access, className, methodName, descriptor);
                case "com/squareup/picasso/Request":
                    return mv == null ? null : new PicassoMethodAdapter(mv, access, methodName, descriptor);
                case "com/facebook/imagepipeline/request/ImageRequest":
                    return mv == null ? null : new FrescoMethodAdapter(mv, access, methodName, descriptor);
                case "com/nostra13/universalimageloader/core/ImageLoadingInf":
                    return mv == null ? null : new ImageLoaderMethodAdapter(mv, access, methodName, descriptor);
                default:
                    return mv;
            }
        }
        return mv;
    }
}
