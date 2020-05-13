package com.ironman.largeimage.method;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class FrescoMethodAdapter extends AdviceAdapter {

    public FrescoMethodAdapter(MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM5, mv, access, name, desc);
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/facebook/imagepipeline/request/ImageRequestBuilder", "getSourceUri", "()Landroid/net/Uri;", false);
        mv.visitVarInsn(ALOAD, 1);
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/facebook/imagepipeline/request/ImageRequestBuilder", "getPostprocessor", "()Lcom/facebook/imagepipeline/request/Postprocessor;", false);
        mv.visitMethodInsn(INVOKESTATIC, "com/avengers/ironman/largeimage/aop/FrescoHook", "proxy", "(Landroid/net/Uri;Lcom/facebook/imagepipeline/request/Postprocessor;)Lcom/facebook/imagepipeline/request/Postprocessor;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/facebook/imagepipeline/request/ImageRequestBuilder", "setPostprocessor", "(Lcom/facebook/imagepipeline/request/Postprocessor;)Lcom/facebook/imagepipeline/request/ImageRequestBuilder;", false);
    }
}
