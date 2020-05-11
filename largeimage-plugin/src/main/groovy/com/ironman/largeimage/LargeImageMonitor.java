package com.ironman.largeimage;

import com.android.build.gradle.AppExtension;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.List;


public class LargeImageMonitor implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        //获取执行的task名，比如执行 clean task ，就输出 clean
        List<String> taskNames = project.getGradle().getStartParameter().getTaskNames();
        //如果是Release版本，则不进行字节码替换
        for (String task : taskNames) {
            if (task.contains("Release")) {
                return;
            }
        }

        AppExtension appExtension = (AppExtension) project.getProperties().get("android");
        //创建自定义扩展，用的时候：
        //largeImageMonitor{
        //    largeImagePluginSwitch = true
        //}
        project.getExtensions().create("largeImageMonitor", LargeImageExtension.class);
        project.afterEvaluate(project1 -> {
            LargeImageExtension extension = project1.getExtensions().getByType(LargeImageExtension.class);
            LargeImageConfig.getInstance().init(extension); //把配置的值保存起来
        });
        //注册 自定义Transform


    }
}
