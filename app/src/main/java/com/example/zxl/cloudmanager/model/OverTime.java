package com.example.zxl.cloudmanager.model;

/**
 * Created by ZXL on 2016/7/13.
 */
public class OverTime {
    private String name;
    private String overTimeDate;
    private String project;

    public OverTime() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverTimeDate() {
        return overTimeDate;
    }

    public void setOverTimeDate(String overTimeDate) {
        this.overTimeDate = overTimeDate;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
