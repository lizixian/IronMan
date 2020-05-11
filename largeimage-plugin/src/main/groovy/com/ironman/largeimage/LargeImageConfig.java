package com.ironman.largeimage;

public class LargeImageConfig {
    private LargeImageConfig() {
    }

    public static LargeImageConfig getInstance() {
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        private static final LargeImageConfig sInstance = new LargeImageConfig();
    }

    private boolean largeImagePluginSwitch = true;

    public boolean isLargeImagePluginSwitch() {
        return largeImagePluginSwitch;
    }

    void init(LargeImageExtension extension) {
        if (null != extension) {
            this.largeImagePluginSwitch = extension.largeImagePluginSwitch;
        }
    }
}
