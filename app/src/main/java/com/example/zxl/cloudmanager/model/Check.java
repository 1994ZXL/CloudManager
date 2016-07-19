package com.example.zxl.cloudmanager.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/11.
 */
public class Check {
    private String date;

    private String name;
    private String project;
    private String checkLocation;
    private String checkManager;
    private String stipulationOnDutyTime;
    private String stipulationOffDutyTime;
    private String dutyTime;
    private String offDutyTime;
    private String ondutyDisposeTime;
    private String offDutyDisposeTime;
    private String state;
    private String leave;
    private String overTime;
    private String travel;
    private String late;

    private int index;

    private String[] content;

    public Check(){

    }


    public String[] getContent() {
        return content;
    }

    public void setContent(String[] content) {
        this.content = content;
        setName(content[0]);
        setProject(content[1]);
        setCheckLocation(content[2]);
        setCheckManager(content[3]);
        setStipulationOnDutyTime(content[4]);
        setStipulationOffDutyTime(content[5]);
        setDutyTime(content[6]);
        setOffDutyTime(content[7]);
        setOndutyDisposeTime(content[8]);
        setOffDutyDisposeTime(content[9]);
        setState(content[10]);
        setLeave(content[11]);
        setOverTime(content[12]);
        setTravel(content[13]);
        setLate(content[14]);
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getOndutyDisposeTime() {
        return ondutyDisposeTime;
    }

    public void setOndutyDisposeTime(String ondutyDisposeTime) {
        this.ondutyDisposeTime = ondutyDisposeTime;
    }

    public String getOffDutyDisposeTime() {
        return offDutyDisposeTime;
    }

    public void setOffDutyDisposeTime(String offDutyDisposeTime) {
        this.offDutyDisposeTime = offDutyDisposeTime;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public String getTravel() {
        return travel;
    }

    public void setTravel(String travel) {
        this.travel = travel;
    }

    public String getLate() {
        return late;
    }

    public void setLate(String late) {
        this.late = late;
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
