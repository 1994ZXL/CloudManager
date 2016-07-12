package com.example.zxl.cloudmanager.model;

import java.util.Date;

/**
 * Created by ZXL on 2016/7/12.
 */
public class Leave {
    private String name;
    private String illness;
    private String time;
    private String state;

    private Date beginTime;
    private Date endTime;

    public Leave(String time) {
        this.time = time;
    }

    public Leave() {

    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
