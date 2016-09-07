package com.example.zxl.cloudmanager.model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZXL on 2016/7/12.
 */
public class Mission {

    private String title; //任务名称
    private String content; //内容
    private int start_time;
    private int end_time;
    private int status; //项目任务状态 0:待完成 1:已完成
    private String evaluate; //评价
    private String pmtask_id; //任务id
    private String pm_id; //项目id
    private String mem_id; //成员id
    private String pmsch_id; //任务进度id
    private String percent; //任务务进度


    private static final String JSON_TITLE = "title";
    private static final String JSON_CONTENT = "content";
    private static final String JSON_STATE = "status";
    private static final String JSON_START_TIME = "start_time";
    private static final String JSON_END_TIME = "end_time";
    private static final String JSON_EVALUATE = "evaluate";
    private static final String JSON_PMTASK_ID = "pmtask_id";
    private static final String JSON_PM_ID = "pm_id";
    private static final String JSON_MEM_ID = "mem_id";
    private static final String JSON_PMSCH_ID = "pmsch_id";
    private static final String JSON_PERCENT = "percent";

    public Mission(){

    }

    public Mission(JSONObject json) throws JSONException {
        if (json.has(JSON_TITLE))
            title = json.getString(JSON_TITLE);
        if (json.has(JSON_CONTENT))
            content = json.getString(JSON_CONTENT);
        if (json.has(JSON_STATE))
            status = json.getInt(JSON_STATE);
        if (json.has(JSON_START_TIME))
            start_time = json.getInt(JSON_START_TIME);
        if (json.has(JSON_END_TIME))
            end_time = json.getInt(JSON_END_TIME);
        if (json.has(JSON_EVALUATE))
            evaluate = json.getString(JSON_EVALUATE);
        if (json.has(JSON_PMTASK_ID))
            pmtask_id = json.getString(JSON_PMTASK_ID);
        if (json.has(JSON_PM_ID))
            pm_id = json.getString(JSON_PM_ID);
        if (json.has(JSON_MEM_ID))
            mem_id = json.getString(JSON_MEM_ID);
        if (json.has(JSON_PMSCH_ID))
            pmsch_id = json.getString(JSON_PMSCH_ID);
        if (json.has(JSON_PERCENT))
            percent = json.getString(JSON_PERCENT);
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_TITLE, title);
        json.put(JSON_CONTENT, content);
        json.put(JSON_STATE, status);
        json.put(JSON_START_TIME, start_time);
        json.put(JSON_END_TIME, end_time);
        json.put(JSON_EVALUATE, evaluate);
        return json;
    }

    public String getPmtask_id() {
        return pmtask_id;
    }

    public void setPmtask_id(String pmtask_id) {
        this.pmtask_id = pmtask_id;
    }

    public String getPm_id() {
        return pm_id;
    }

    public void setPm_id(String pm_id) {
        this.pm_id = pm_id;
    }

    public String getMem_id() {
        return mem_id;
    }

    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }

    public String getPmsch_id() {
        return pmsch_id;
    }

    public void setPmsch_id(String pmsch_id) {
        this.pmsch_id = pmsch_id;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
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



    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
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
