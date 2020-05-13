package com.ironman.largeimage.method;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class PicassoMethodAdapter extends AdviceAdapter {

    public PicassoMethodAdapter(MethodVisitor mv, int access, String name, String desc) {
        super(Opcodes.ASM5, mv, access, name, desc);
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, "com/avengers/ironman/largeimage/aop/PicassoHook", "proxy", "(Ljava/lang/Object;)V", false);
    }
}
