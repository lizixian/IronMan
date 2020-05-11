package com.ironman.largeimage.transform;

import com.ironman.largeimage.adapter.LargeImageClassAdapter;
import com.quinn.hunter.transform.HunterTransform;
import com.quinn.hunter.transform.asm.BaseWeaver;

import org.gradle.api.Project;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

public class LargeImageTransform extends HunterTransform {

    public LargeImageTransform(Project project) {
        super(project);
        this.bytecodeWeaver = new LargeImageWeaver();
    }

    private static class LargeImageWeaver extends BaseWeaver {
        @Override
        protected ClassVisitor wrapClassWriter(ClassWriter classWriter) {
            return new LargeImageClassAdapter(classWriter);
        }
    }
}
