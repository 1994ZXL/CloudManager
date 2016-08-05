package com.example.zxl.cloudmanager.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ZXL on 2016/7/12.
 */
public class Post {
    private String mem_name; //员工名
    private int create_time; //创建时间
    private int report_time; //日报提交时间
    private String content; //日报内容
    private String daily_id; //日报id


    private static final String JSON_MEM_NAME = "mem_name";
    private static final String JSON_CREATE_TIME = "create_time";
    private static final String JSON_REPORT_TIME = "report_time";
    private static final String JSON_CONTENT = "content";

    public Post() {

    }

    public void set(String[] content) {
//        setName(content[0]);
//        setContent(content[1]);
//        setPostTime(content[2]);
    }

    public Post(JSONObject json) throws JSONException {
        if (json.has(JSON_MEM_NAME))
            mem_name = json.getString(JSON_MEM_NAME);
        if (json.has(JSON_CREATE_TIME))
            create_time = json.getInt(JSON_CREATE_TIME);
        if (json.has(JSON_REPORT_TIME))
            report_time = json.getInt(JSON_REPORT_TIME);
        if (json.has(JSON_CONTENT))
            content = json.getString(JSON_CONTENT);
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_MEM_NAME, mem_name);
        json.put(JSON_CREATE_TIME, create_time);
        json.put(JSON_REPORT_TIME, report_time);
        json.put(JSON_CONTENT, content);
        return json;
    }

    public String getMem_name() {
        return mem_name;
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

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
