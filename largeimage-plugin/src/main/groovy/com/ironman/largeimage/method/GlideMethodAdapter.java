package com.ironman.largeimage.method;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class GlideMethodAdapter extends AdviceAdapter {

    public GlideMethodAdapter(MethodVisitor methodVisitor, int access, String name, String descriptor) {
        super(Opcodes.ASM5, methodVisitor, access, name, descriptor);
    }

    /**
     * 当构造方法退出时调用，往 requestListeners 变量中添加一个自定义的 listener
     */
    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        mv.visitVarInsn(ALOAD, 0);  //访问局部变量指令，var 是行数
        mv.visitFieldInsn(GETFIELD, "com/bumptech/glide/request/SingleRequest", "requestListeners", "Ljava/util/List;");
        mv.visitMethodInsn(INVOKESTATIC, "com/avengers/ironman/largeimage/aop/GlideHook", "process", "(Ljava/util/List;)Ljava/util/List;", false);
    }
}
