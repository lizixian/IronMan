package com.avengers.ironman.largeimage;

public class LargeImageInfo {
    private String framework = "other";
    private String url;
    private double fileSize;
    private double memorySize;
    private int width;
    private int height;
    private String activity;
    private String layoutLevel;
    private int layoutId;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public double getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(double memorySize) {
        this.memorySize = memorySize;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getLayoutLevel() {
        return layoutLevel;
    }

    public void setLayoutLevel(String layoutLevel) {
        this.layoutLevel = layoutLevel;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public String toString() {
        return "LargeImageInfo{" +
                "framework='" + framework + '\'' +
                ", url='" + url + '\'' +
                ", fileSize=" + fileSize +
                ", memorySize=" + memorySize +
                ", width=" + width +
                ", height=" + height +
                ", activity='" + activity + '\'' +
                ", layoutLevel='" + layoutLevel + '\'' +
                ", layoutId='" + layoutId + '\'' +
                '}';
    }
}
