package com.ironman.largeimage.weaver;

import com.ironman.largeimage.adapter.LargeImageClassVisitor;
import com.quinn.hunter.transform.asm.BaseWeaver;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class LargeImageWeaver extends BaseWeaver {
    @Override
    protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
        return new LargeImageClassVisitor(classWriter);
    }
}