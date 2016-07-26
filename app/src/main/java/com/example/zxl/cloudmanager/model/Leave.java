package com.example.zxl.cloudmanager.model;

import java.util.Date;

/**
 * Created by ZXL on 2016/7/12.
 */
public class Leave {
    private String name;
    private String type;
    private String state;

    private String beginTime;
    private String endTime;

    private String resion;
    private String suggestion;
    private String applyTime;
    private String disposeTime;

    private String[] content;

    public Leave() {
        setName("张三");
    }
    
    public void set(String[] content) {
        setType(content[1]);
        setState(content[2]);
        setBeginTime(content[3]);
        setEndTime(content[4]);
        setResion(content[5]);
        setSuggestion(content[6]);
        setApplyTime(content[7]);
        setDisposeTime(content[8]);
    }

    public String[] getContent() {
        return content;
    }

    public void setContent(String[] content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResion() {
        return resion;
    }

    public void setResion(String resion) {
        this.resion = resion;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDisposeTime() {
        return disposeTime;
    }

    public void setDisposeTime(String disposeTime) {
        this.disposeTime = disposeTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
