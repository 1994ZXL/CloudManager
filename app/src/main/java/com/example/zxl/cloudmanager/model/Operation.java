package com.example.zxl.cloudmanager.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZXL on 2016/9/9.
 */
public class Operation {
    private String project_name; //项目名称
    private String contact_name; //甲方姓名
    private String contact_mob; //联系电话
    private String goon_technical; //运维技术负责人
    private String goon_business; //运维客服负责人
    private int project_state; //运维状态
    private int goon_time; //运维开始时间
    private int goon_actual_start_time; //运维实际开始时间
    private int goon_actual_end_time; //运维实际结束时间

    public Operation(JSONObject json) throws JSONException {
        if (json.has("project_name"))
            project_name = json.getString("project_name");
        if (json.has("contact_name"))
            contact_name = json.getString("contact_name");
        if (json.has("contact_mob"))
            contact_mob = json.getString("contact_mob");
        if (json.has("goon_technical"))
            goon_technical = json.getString("goon_technical");
        if (json.has("goon_business"))
            goon_business = json.getString("goon_business");
        if (json.has("project_state"))
            project_state = json.getInt("project_state");
        if (json.has("goon_time"))
            goon_time = json.getInt("goon_time");
        if (json.has("goon_actual_start_time"))
            goon_actual_start_time = json.getInt("goon_actual_start_time");
        if (json.has("goon_actual_end_time"))
            goon_actual_end_time = json.getInt("goon_actual_end_time");
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
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

    public String getGoon_technical() {
        return goon_technical;
    }

    public void setGoon_technical(String goon_technical) {
        this.goon_technical = goon_technical;
    }

    public String getGoon_business() {
        return goon_business;
    }

    public void setGoon_business(String goon_business) {
        this.goon_business = goon_business;
    }

    public String getProject_state() {
        if (project_state == 0)
            return "取消";
        if (project_state == 1)
            return "准备";
        if (project_state == 2)
            return "开发";
        if (project_state == 3)
            return "维护";
        if (project_state == 4)
            return "结束";
        return null;
    }

    public void setProject_state(int project_state) {
        this.project_state = project_state;
    }

    public int getGoon_time() {
        return goon_time;
    }

    public void setGoon_time(int goon_time) {
        this.goon_time = goon_time;
    }

    public int getGoon_actual_start_time() {
        return goon_actual_start_time;
    }

    public void setGoon_actual_start_time(int goon_actual_start_time) {
        this.goon_actual_start_time = goon_actual_start_time;
    }

    public int getGoon_actual_end_time() {
        return goon_actual_end_time;
    }

    public void setGoon_actual_end_time(int goon_actual_end_time) {
        this.goon_actual_end_time = goon_actual_end_time;
    }
}
