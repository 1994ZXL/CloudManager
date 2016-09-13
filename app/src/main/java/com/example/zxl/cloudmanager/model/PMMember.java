package com.example.zxl.cloudmanager.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZXL on 2016/9/13.
 */
public class PMMember {
    private String pmmem_id; //项目成员ID
    private String mem_name; //项目成员名
    private String pm_id; //项目ID
    private String mem_id; //员工ID
    private String role; //身份（1领导；2项目负责人；3一般成员）
    private String member_res; //职责说明
    private String project_name; //项目名称

    public PMMember(JSONObject json) throws JSONException {
        if (json.has("pmmem_id"))
            pmmem_id = json.getString("pmmem_id");
        if (json.has("mem_name"))
            mem_name = json.getString("mem_name");
        if (json.has("pm_id"))
            pm_id = json.getString("pm_id");
        if (json.has("mem_id"))
            mem_id = json.getString("mem_id");
        if (json.has("role"))
            role = json.getString("role");
        if (json.has("member_res"))
            member_res = json.getString("member_res");
        if (json.has("project_name"))
            project_name = json.getString("project_name");
    }

    public PMMember() {}

    public String getPmmem_id() {
        return pmmem_id;
    }

    public String getMem_name() {
        return mem_name;
    }

    public String getPm_id() {
        return pm_id;
    }

    public String getMem_id() {
        return mem_id;
    }

    public String getRole() {
        return role;
    }

    public String getMember_res() {
        return member_res;
    }

    public String getProject_name() {
        return project_name;
    }
}
