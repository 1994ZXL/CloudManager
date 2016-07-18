package com.example.zxl.cloudmanager.model;

/**
 * Created by ZXL on 2016/7/11.
 */
public class Check {
    private String date;
    private String checkLocation;
    private String dutyTime;
    private String offDutyTime;

    private String name;
    private String checkManager;
    private String stipulationOnDutyTime;
    private String stipulationOffDutyTime;
    private String state;

    public Check(String date, String checkLocation, String dutyTime, String offDutyTime) {
        this.date = date;
        this.checkLocation = checkLocation;
        this.dutyTime = dutyTime;
        this.offDutyTime = offDutyTime;
    }

    public Check(){
        state = "未处理";
        checkLocation = "公司";
        checkManager = "领导";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCheckManager() {
        return checkManager;
    }

    public void setCheckManager(String checkManager) {
        this.checkManager = checkManager;
    }

    public String getStipulationOnDutyTime() {
        return stipulationOnDutyTime;
    }

    public void setStipulationOnDutyTime(String stipulationOnDutyTime) {
        this.stipulationOnDutyTime = stipulationOnDutyTime;
    }

    public String getStipulationOffDutyTime() {
        return stipulationOffDutyTime;
    }

    public void setStipulationOffDutyTime(String stipulationOffDutyTime) {
        this.stipulationOffDutyTime = stipulationOffDutyTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCheckLocation() {
        return checkLocation;
    }

    public void setCheckLocation(String checkLocation) {
        this.checkLocation = checkLocation;
    }

    public String getDutyTime() {
        return dutyTime;
    }

    public void setDutyTime(String dutyTime) {
        this.dutyTime = dutyTime;
    }

    public String getOffDutyTime() {
        return offDutyTime;
    }

    public void setOffDutyTime(String offDutyTime) {
        this.offDutyTime = offDutyTime;
    }
}
