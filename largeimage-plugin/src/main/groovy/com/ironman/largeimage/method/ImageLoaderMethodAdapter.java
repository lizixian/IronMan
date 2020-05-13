package com.ironman.largeimage.method;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class ImageLoaderMethodAdapter extends AdviceAdapter {

    public ImageLoaderMethodAdapter(MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM5, mv, access, name, desc);
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        //加载第6个参数 ImageLoadingListener
        mv.visitVarInsn(ALOAD, 6);
        mv.visitMethodInsn(INVOKESTATIC, "com/avengers/ironman/largeimage/aop/ImageLoaderHook", "proxy", "(Lcom/nostra13/universalimageloader/core/listener/ImageLoadingListener;)Lcom/nostra13/universalimageloader/core/listener/ImageLoadingListener;", false);
        //重新赋值给第6个参数
        mv.visitVarInsn(ASTORE, 6);
    }
}
