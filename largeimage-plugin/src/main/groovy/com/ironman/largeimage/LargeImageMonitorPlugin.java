package com.ironman.largeimage;

import com.android.build.gradle.AppExtension;
import com.ironman.largeimage.transform.LargeImageTransform;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.Collections;
import java.util.List;


public class LargeImageMonitorPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        List<String> taskNames = project.getGradle().getStartParameter().getTaskNames();
        for (String task : taskNames) {
            if (task.contains("Release")) {
                return;
            }
        }
        AppExtension appExtension = (AppExtension) project.getProperties().get("android");
        project.getExtensions().create("largeImageMonitor", LargeImageExtension.class);
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {
                LargeImageExtension extension = project.getExtensions().getByType(LargeImageExtension.class);
                LargeImageConfig.getInstance().init(extension); //把配置的值保存起来
            }
        });
        //注册 自定义Transform
        appExtension.registerTransform(new LargeImageTransform(project), Collections.EMPTY_LIST);

    }
}
