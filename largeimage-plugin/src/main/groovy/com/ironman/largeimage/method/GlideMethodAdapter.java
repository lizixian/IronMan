package com.ironman.largeimage.method;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

public class GlideMethodAdapter extends AdviceAdapter {
    /**
     * Constructs a new {@link AdviceAdapter}.
     *
     * @param methodVisitor the method visitor to which this adapter delegates calls.
     * @param access        the method's access flags (see {@link Opcodes}).
     * @param name          the method's name.
     * @param descriptor    the method's descriptor (see {@link Type Type}).
     */
    public GlideMethodAdapter(MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(Opcodes.ASM5, methodVisitor, access, name, descriptor);
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        //访问局部变量指令
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, "com/bumptech/glide/request/SingleRequest", "requestListeners", "Ljava/util/List;");
        //访问方法指令
        mv.visitMethodInsn(INVOKESTATIC, "com/avengers/ironman/largeimage/aop/GlideHook", "process", "(Ljava/util/List;)Ljava/util/List;", false);
    }
}
