package com.example.zxl.cloudmanager.model;

/**
 * Created by ZXL on 2016/7/13.
 */
public class Travel {


    private String name;
    private String beginTime;
    private String endTime;
    private String backBeginTime;
    private String backEndTime;
    private String travelContent;
    private String travelAdd;
    private String travelAddress;
    private String travelState;

    private String[] mContent;

    public Travel(String[] content) {
        mContent = content;
        set(mContent);
    }

    public Travel() {

    }

    public void set(String[] content) {
        setName(content[0]);
        setBeginTime(content[1]);
        setEndTime(content[2]);
        setBackBeginTime(content[3]);
        setBackEndTime(content[4]);
        setTravelContent(content[5]);
        setTravelAdd(content[6]);
        setTravelAddress(content[7]);
        setTravelState(content[8]);
    }

    public String[] get() {
        return mContent;
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

    public String getBackBeginTime() {
        return backBeginTime;
    }

    public void setBackBeginTime(String backBeginTime) {
        this.backBeginTime = backBeginTime;
    }

    public String getBackEndTime() {
        return backEndTime;
    }

    public void setBackEndTime(String backEndTime) {
        this.backEndTime = backEndTime;
    }

    public String getTravelAdd() {
        return travelAdd;
    }

    public void setTravelAdd(String travelAdd) {
        this.travelAdd = travelAdd;
    }

    public String getTravelAddress() {
        return travelAddress;
    }

    public void setTravelAddress(String travelAddress) {
        this.travelAddress = travelAddress;
    }

    public String getTravelContent() {
        return travelContent;
    }

    public void setTravelContent(String travelContent) {
        this.travelContent = travelContent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTravelState() {
        return travelState;
    }

    public void setTravelState(String travleState) {
        this.travelState = travleState;
    }
}
