package com.ironman.largeimage.adapter;

import com.ironman.largeimage.LargeImageConfig;
import com.ironman.largeimage.method.GlideMethodAdapter;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LargeImageClassAdapter extends ClassVisitor {
    /**
     * 当前类名
     */
    private String className;

    public LargeImageClassAdapter(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
        if (!LargeImageConfig.getInstance().isLargeImagePluginSwitch()) {
            return mv;
        }
        if (className.equals("com/bumptech/glide/request/SingleRequest") && name.equals("<init>") && descriptor != null) {
            return mv == null ? null : new GlideMethodAdapter(mv, access, name, descriptor);
        }
        return mv;
    }
}
