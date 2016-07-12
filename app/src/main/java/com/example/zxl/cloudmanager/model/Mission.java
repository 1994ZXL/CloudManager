package com.example.zxl.cloudmanager.model;

import java.util.Date;

/**
 * Created by ZXL on 2016/7/12.
 */
public class Mission {
    private String name;
    private String priority;
    private Date missionBeginTime;
    private Date missionEndTime;
    private String state;

    public Mission(){

    }

    public Mission(Date missionBeginTime, Date missionEndTime) {
        this.missionBeginTime = missionBeginTime;
        this.missionEndTime = missionEndTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getMissionBeginTime() {
        return missionBeginTime;
    }

    public void setMissionBeginTime(Date missionBeginTime) {
        this.missionBeginTime = missionBeginTime;
    }

    public Date getMissionEndTime() {
        return missionEndTime;
    }

    public void setMissionEndTime(Date missionEndTime) {
        this.missionEndTime = missionEndTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
