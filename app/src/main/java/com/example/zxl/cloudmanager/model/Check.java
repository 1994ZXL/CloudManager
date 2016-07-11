package com.example.zxl.cloudmanager.model;

/**
 * Created by ZXL on 2016/7/11.
 */
public class Check {
    private String date;
    private String checkLocation;
    private String dutyTime;
    private String offDutyTime;

    public Check(String date, String checkLocation, String dutyTime, String offDutyTime) {
        this.date = date;
        this.checkLocation = checkLocation;
        this.dutyTime = dutyTime;
        this.offDutyTime = offDutyTime;
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
