package com.example.zxl.cloudmanager.model;

/**
 * Created by ZXL on 2016/7/13.
 */
public class OverTime {
    private String name;
    private String project;
    private String beginTime;
    private String endTime;
    private String thisTime;
    private String reson;
    private String totalTime;

    public OverTime() {

    }

    public void set(String[] content) {
        setName(content[0]);
        setProject(content[1]);
        setBeginTime(content[2]);
        setEndTime(content[3]);
        setThisTime(content[4]);
        setReson(content[5]);
        setTotalTime(content[6]);
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getThisTime() {
        return thisTime;
    }

    public void setThisTime(String thisTime) {
        this.thisTime = thisTime;
    }

    public String getReson() {
        return reson;
    }

    public void setReson(String reson) {
        this.reson = reson;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
