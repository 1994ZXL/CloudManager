package com.example.zxl.cloudmanager.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZXL on 2016/7/12.
 */
public class Post {
    private String mem_name; //员工名
    private int report_time; //日报提交时间
    private String content; //日报内容
    private String daily_id; //日报id
    private int daily_date; //日报日期
    private int state; //日报状态
    private int level; //日报评分
    private String opinion; //日报评价

    private static final String JSON_MEM_NAME = "mem_name";
    private static final String JSON_REPORT_TIME = "report_time";
    private static final String JSON_CONTENT = "content";
    private static final String JSON_STATE = "state";
    private static final String JSON_DAILY_ID = "daily_id";
    private static final String JSON_DAILY_DATE = "daily_date";
    private static final String JSON_LEVEL = "level";
    private static final String JSON_OPINION = "opinion";

    public Post() {

    }

    public Post(JSONObject json) throws JSONException {
        if (json.has(JSON_MEM_NAME))
            mem_name = json.getString(JSON_MEM_NAME);
        if (json.has(JSON_REPORT_TIME))
            report_time = json.getInt(JSON_REPORT_TIME);
        if (json.has(JSON_CONTENT))
            content = json.getString(JSON_CONTENT);
        if (json.has(JSON_STATE))
            state = json.getInt(JSON_STATE);
        if (json.has(JSON_DAILY_ID))
            daily_id = json.getString(JSON_DAILY_ID);
        if (json.has(JSON_LEVEL))
            level = json.getInt(JSON_LEVEL);
        if (json.has(JSON_OPINION))
            opinion = json.getString(JSON_OPINION);
        if (json.has(JSON_DAILY_DATE))
            daily_date = json.getInt(JSON_DAILY_DATE);
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_MEM_NAME, mem_name);
        json.put(JSON_REPORT_TIME, report_time);
        json.put(JSON_CONTENT, content);
        json.put(JSON_STATE, state);
        json.put(JSON_DAILY_ID, daily_id);
        return json;
    }

    public String getMem_name() {
        return mem_name;
    }

    public String getState() {
        if (state == 0)
            return "没提交";
        if (state == 1)
            return "提交";
        return null;
    }

    public int getDaily_date() {
        return daily_date;
    }

    public void setDaily_date(int daily_date) {
        this.daily_date = daily_date;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getLevel() {
        if (level == 1)
            return "好";
        if (level == 2)
            return "中";
        if (level == 3)
            return "差";
        return null;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public void setMem_name(String mem_name) {
        this.mem_name = mem_name;
    }

    public int getReport_time() {
        return report_time;
    }

    public void setReport_time(int report_time) {
        this.report_time = report_time;
    }

    public String getDaily_id() {
        return daily_id;
    }

    public void setDaily_id(String daily_id) {
        this.daily_id = daily_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
