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
    private int project_state; //项目状态
    private int ready_time; //准备开始时间
    private int finished_time; //结束时间

    private static final String JSON_PROJECT_NAME = "project_name";
    private static final String JSON_HEDER = "goon_technical";
    private static final String JSON_CONTENT = "project_summary";
    private static final String JSON_PART_A = "belong_unit";
    private static final String JSON_CONTACT_NAME = "custom_name";
    private static final String JSON_CONTACT_MOB = "custom_phone";
    private static final String JSON_PROJECT_CYCLE = "project_cycle";
    private static final String JSON_STATUS = "status";
    private static final String JSON_PROJECT_STATE = "project_state";
    private static final String JSON_READY_TIME = "ready_time";
    private static final String JSON_FINISHED_TIME = "finished_time";

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
        if (json.has(JSON_PROJECT_STATE))
            project_state = json.getInt(JSON_PROJECT_STATE);
        if (json.has(JSON_READY_TIME))
            project_cycle = json.getInt(JSON_READY_TIME);
        if (json.has(JSON_FINISHED_TIME))
            status = json.getInt(JSON_FINISHED_TIME);
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
        json.put(JSON_PROJECT_STATE, project_state);
        json.put(JSON_READY_TIME, ready_time);
        json.put(JSON_FINISHED_TIME, finished_time);
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

    public int getReady_time() {
        return ready_time;
    }

    public void setReady_time(int ready_time) {
        this.ready_time = ready_time;
    }

    public int getFinished_time() {
        return finished_time;
    }

    public void setFinished_time(int finished_time) {
        this.finished_time = finished_time;
    }

    public String getProject_state() {
        if(project_state == 0)
            return "取消";
        if(project_state == 1)
            return "准备";
        if(project_state == 2)
            return "开发";
        if(project_state == 3)
            return "维护";
        if(project_state == 4)
            return "结束";
        return null;
    }

    public void setProject_state(int project_state) {
        this.project_state = project_state;
    }
}
