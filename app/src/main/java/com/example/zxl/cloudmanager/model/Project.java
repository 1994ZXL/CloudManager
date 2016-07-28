package com.example.zxl.cloudmanager.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZXL on 2016/7/28.
 */
public class Project {
    private String project_name; //项目名称
    private String header; //项目负责人
    private String content; //项目内容
    private String part_a; //甲方单位名称
    private String contact_name; //甲方联系人名称
    private String contact_mob; //甲方联系电话
    private int project_cycle; //项目周期
    private int status; //项目状态

    private static final String JSON_PROJECT_NAME = "project_name";
    private static final String JSON_HEDER = "header";
    private static final String JSON_CONTENT = "content";
    private static final String JSON_PART_A = "part_a";
    private static final String JSON_CONTACT_NAME = "contact_name";
    private static final String JSON_CONTACT_MOB = "contact_mob";
    private static final String JSON_PROJECT_CYCLE = "project_cycle";
    private static final String JSON_STATUS = "status";

    public Project() {

    }

    public Project(JSONObject json) throws JSONException {
        if (json.has(JSON_PROJECT_NAME))
            project_name = json.getString(JSON_PROJECT_NAME);
        if (json.has(JSON_HEDER))
            header = json.getString(JSON_HEDER);
        if (json.has(JSON_CONTENT))
            content = json.getString(JSON_CONTENT);
        if (json.has(JSON_PART_A))
            part_a = json.getString(JSON_PART_A);
        if (json.has(JSON_CONTACT_NAME))
            contact_name = json.getString(JSON_CONTACT_NAME);
        if (json.has(JSON_CONTACT_MOB))
            contact_mob = json.getString(JSON_CONTACT_MOB);
        if (json.has(JSON_PROJECT_CYCLE))
            project_cycle = json.getInt(JSON_PROJECT_CYCLE);
        if (json.has(JSON_STATUS))
            status = json.getInt(JSON_STATUS);
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_PROJECT_NAME, project_name);
        json.put(JSON_HEDER, header);
        json.put(JSON_CONTENT, content);
        json.put(JSON_PART_A, part_a);
        json.put(JSON_CONTACT_NAME, contact_name);
        json.put(JSON_CONTACT_MOB, contact_mob);
        json.put(JSON_PROJECT_CYCLE, project_cycle);
        json.put(JSON_STATUS, status);
        return json;
    }

    public String get() {
        return "get_list";
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPart_a() {
        return part_a;
    }

    public void setPart_a(String part_a) {
        this.part_a = part_a;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_mob() {
        return contact_mob;
    }

    public void setContact_mob(String contact_mob) {
        this.contact_mob = contact_mob;
    }

    public int getProject_cycle() {
        return project_cycle;
    }

    public void setProject_cycle(int project_cycle) {
        this.project_cycle = project_cycle;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
