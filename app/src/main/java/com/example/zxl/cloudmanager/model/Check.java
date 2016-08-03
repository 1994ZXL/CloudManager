package com.example.zxl.cloudmanager.model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/11.
 */
public class Check {
    private String date;


    private String mem_name; //员工姓名
    private String project;
    private String punch_address; //考勤地址
    private String punch_mgr; //考勤主管
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
    private static final String JSON_MGR = "punch_mgr";
    private static final String JSON_NAME = "mem_name";
    private static final String JSON_ADDRESS = "punch_address";

    public Check(JSONObject json) throws JSONException {
        if (json.has(JSON_MGR))
            punch_mgr = json.getString(JSON_MGR);
        if (json.has(JSON_ADDRESS))
            punch_address = json.getString(JSON_ADDRESS);
        if (json.has(JSON_NAME))
            mem_name = json.getString(JSON_NAME);
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_MGR, punch_mgr);
        json.put(JSON_ADDRESS, punch_address);
        json.put(JSON_NAME,mem_name);
        return json;
    }
    public String[] getContent() {
        return content;
    }

    public void setContent(String[] content) {
        this.content = content;
        setMem_name(content[0]);
         /*setProject(content[1]);*/
        setCheckLocation(content[2]);
        setPunch_mgr(content[3]);
        /*setStipulationOnDutyTime(content[4]);
        setStipulationOffDutyTime(content[5]);
        setDutyTime(content[6]);
        setOffDutyTime(content[7]);
        setOndutyDisposeTime(content[8]);
        setOffDutyDisposeTime(content[9]);
        setStatus(content[10]);
        setLeave(content[11]);
        setOverTime(content[12]);
        setTravel(content[13]);
        setLate(content[14]);*/
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

    public String getMem_name() {
        return mem_name;
    }

    public void setMem_name(String mem_name) {
        this.mem_name = mem_name;
    }

    public String getPunch_mgr() {
        return punch_mgr;
    }

    public void setPunch_mgr(String punch_mgr) {
        this.punch_mgr = punch_mgr;
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
        return punch_address;
    }

    public void setCheckLocation(String checkLocation) {
        this.punch_address = checkLocation;
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
