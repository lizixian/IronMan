package com.ironman.largeimage.method;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class GlideMethodAdapter extends AdviceAdapter {

    private String className;

    public GlideMethodAdapter(MethodVisitor methodVisitor, int access, String className, String methodName, String descriptor) {
        super(Opcodes.ASM5, methodVisitor, access, methodName, descriptor);
        this.className = className;
    }

    /**
     * 当构造方法退出时调用，往 requestListeners 变量中添加一个自定义的 listener
     */
    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        switch (className) {
            case "com/bumptech/glide/request/SingleRequest":
                mv.visitVarInsn(ALOAD, 0);  //访问局部变量指令，var 是行数
                mv.visitMethodInsn(INVOKESTATIC, "com/avengers/ironman/largeimage/aop/GlideHook", "hookSingleRequest", "(Ljava/lang/Object;)V", false);
                break;
            case "com/bumptech/glide/request/target/ViewTarget":
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, "com/bumptech/glide/request/target/ViewTarget", "view", "Landroid/view/View;");
                mv.visitMethodInsn(INVOKESTATIC, "com/avengers/ironman/largeimage/aop/GlideHook", "hookViewTarget", "(Landroid/view/View;)V", false);
                break;
            default:
                break;
        }
    }
}
