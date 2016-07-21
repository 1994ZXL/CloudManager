package com.example.zxl.cloudmanager.model;

/**
 * Created by ZXL on 2016/7/12.
 */
public class Mission {
    private String name;
    private String content;
    private String level;
    private String detailContent;
    private String missionBeginTime;
    private String missionEndTime;
    private String progress;
    private String state;
    private String disposeSuggestion;


    public Mission(){

    }

    public void setData(String[] data) {
        setName(data[0]);
        setContent(data[1]);
        setLevel(data[2]);
        setDetailContent(data[3]);
        setMissionBeginTime(data[4]);
        setMissionEndTime(data[5]);
        setProgress(data[6]);
        setState(data[7]);
        setDisposeSuggestion(data[8]);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDetailContent() {
        return detailContent;
    }

    public void setDetailContent(String detailContent) {
        this.detailContent = detailContent;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getDisposeSuggestion() {
        return disposeSuggestion;
    }

    public void setDisposeSuggestion(String disposeSuggestion) {
        this.disposeSuggestion = disposeSuggestion;
    }

    public Mission(String missionBeginTime, String missionEndTime) {
        this.missionBeginTime = missionBeginTime;
        this.missionEndTime = missionEndTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMissionBeginTime() {
        return missionBeginTime;
    }

    public void setMissionBeginTime(String missionBeginTime) {
        this.missionBeginTime = missionBeginTime;
    }

    public String getMissionEndTime() {
        return missionEndTime;
    }

    public void setMissionEndTime(String missionEndTime) {
        this.missionEndTime = missionEndTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
