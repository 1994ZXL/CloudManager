package com.example.zxl.cloudmanager.model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZXL on 2016/7/12.
 */
public class Mission {
    private String mem_name; //成员名字
    private String title; //项目标题
    private String content; //内容
    private String level;
    private String detailContent;
    private int start_time;
    private int over_time;
    private int percent; //进度
    private String progress;
    private int status; //项目任务状态
    private String evaluete; //评价
    private String missionWorker;

    private static Mission sMission;

    private static final String JSON_MEM= "mem_name";
    //private static final String JSON_PERCENT = "percent";
    private static final String JSON_TITLE = "title";
    private static final String JSON_CONTENT = "content";
    private static final String JSON_STATE = "status";
    private static final String JSON_BEGINTIME = "start_time";
    private static final String JSON_ENDTIME = "over_time";
    private static final String JSON_EVALUETE = "evaluete";

    public Mission(){

    }
    public static Mission newInstance(Context context) {
        if (null == sMission) {
            sMission = new Mission();
        }
        return sMission;
    }

    public void setData(String[] data) {
        setName(data[0]);
        setContent(data[1]);
        setLevel(data[2]);
        setDetailContent(data[3]);
        /*setStart_time(data[4]);
        setOver_time(data[5]);*/
        setProgress(data[6]);
        /*setStatus(data[7]);*/
        setDisposeSuggestion(data[8]);
        //setMissionWorker(data[9]);
    }

    public Mission(JSONObject json) throws JSONException {
        if (json.has(JSON_TITLE))
            title = json.getString(JSON_TITLE);
        if (json.has(JSON_CONTENT))
            content = json.getString(JSON_CONTENT);
        if (json.has(JSON_STATE))
            status = json.getInt(JSON_STATE);
        if (json.has(JSON_BEGINTIME))
            start_time = json.getInt(JSON_BEGINTIME);
        if (json.has(JSON_ENDTIME))
            over_time = json.getInt(JSON_ENDTIME);
        if (json.has(JSON_EVALUETE))
            evaluete = json.getString(JSON_EVALUETE);
        if (json.has(JSON_MEM))
            mem_name = json.getString(JSON_MEM);
        /*if (json.has(JSON_PERCENT))
            percent = json.getInt(JSON_PERCENT);*/
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_TITLE, title);
        json.put(JSON_CONTENT, content);
        json.put(JSON_STATE, status);
        json.put(JSON_BEGINTIME, start_time);
        json.put(JSON_ENDTIME, over_time);
        json.put(JSON_EVALUETE, evaluete);
        json.put(JSON_MEM, mem_name);
        //json.put(JSON_PERCENT, percent);
        return json;
    }
    public String getMissionWorker() {
        return missionWorker;
    }

    public void setMissionWorker(String missionWorker) {
        this.missionWorker = missionWorker;
    }

    public String getMem_name() {
        return mem_name;
    }

    public void setMem_name(String mem_name) {
        this.mem_name = mem_name;
    }

    public String getEvaluete() {
        return evaluete;
    }

    public void setEvaluete(String evaluete) {
        this.evaluete = evaluete;
    }

    public String getPercent() {
        return percent + "%";
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return evaluete;
    }

    public void setDisposeSuggestion(String disposeSuggestion) {
        this.evaluete = disposeSuggestion;
    }

    public Mission(int start_time, int over_time) {
        this.start_time = start_time;
        this.over_time = over_time;
    }

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getOver_time() {
        return over_time;
    }

    public void setOver_time(int over_time) {
        this.over_time = over_time;
    }

    public String getStatus() {
        if(status == 0)
            return "待完成";
        if(status == 1)
            return "已完成";
        return null;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
