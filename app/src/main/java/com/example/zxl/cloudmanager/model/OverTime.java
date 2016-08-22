package com.example.zxl.cloudmanager.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/13.
 */
public class OverTime {
    private String mem_name; //员工名
    private String mem_id; //员工id
    private String work_name; //项目名
    private String work_pm; //项目id
    private int start_time; //加班开始时间
    private int end_time; //加班结束时间
    private int status; //状态 1:等待,2:确认,3:取消，默认为等待
    private String thisTime;
    private String work_reason; //加班原因
    private String totalTime;

    public OverTime() {

    }

    private static final String JSON_NAME = "mem_name";
    private static final String JSON_STATUS = "status";
    private static final String JSON_PROJECT = "work_pm";
    private static final String JSON_BEGIN_TIME = "start_time";
    private static final String JSON_END_TIME = "end_time";
    private static final String JSON_WORK_REASON = "work_reason";

    public void set(String[] content) {
//        setMem_id(content[0]);
//        setPm_id(content[1]);
        /*setStart_time(content[2]);
        setEnd_time(content[3]);*/
        setThisTime(content[4]);
        setWork_reason(content[5]);
        setTotalTime(content[6]);
    }
    public OverTime(JSONObject json) throws JSONException {
        if (json.has(JSON_NAME))
            mem_name = json.getString(JSON_NAME);
        if (json.has(JSON_PROJECT))
            work_pm = json.getString(JSON_PROJECT);
        if (json.has(JSON_STATUS))
            status = json.getInt(JSON_STATUS);
        if (json.has(JSON_BEGIN_TIME))
            start_time = json.getInt(JSON_BEGIN_TIME);
        if (json.has(JSON_END_TIME))
            end_time = json.getInt(JSON_END_TIME);
        if (json.has(JSON_WORK_REASON))
            work_reason = json.getString(JSON_WORK_REASON);

    }
    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
//        json.put(JSON_NAME, mem_id);
        json.put(JSON_STATUS, status);
//        json.put(JSON_PROJECT,work_pm);
        json.put(JSON_END_TIME, end_time);
        json.put(JSON_WORK_REASON, work_reason);
        json.put(JSON_BEGIN_TIME, start_time);
        return json;
    }
    public String getStatus() {
        //状态:1:等待,2:确认,3:取消，默认为等待
        if (status == 1) {
            return "等待";
        } else if (status == 2) {
            return "确认";
        } else if (status == 3) {
            return "取消";
        }
        return null;
    }

    public String getMem_name() {
        return mem_name;
    }

    public void setMem_name(String mem_name) {
        this.mem_name = mem_name;
    }

    public String getMem_id() {
        return mem_id;
    }

    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }

    public String getWork_name() {
        return work_name;
    }

    public void setWork_name(String work_name) {
        this.work_name = work_name;
    }

    public String getWork_pm() {
        return work_pm;
    }

    public void setWork_pm(String work_pm) {
        this.work_pm = work_pm;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public String getThisTime() {
        return thisTime;
    }

    public void setThisTime(String thisTime) {
        this.thisTime = thisTime;
    }

    public String getWork_reason() {
        return work_reason;
    }

    public void setWork_reason(String work_reason) {
        this.work_reason = work_reason;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

}
