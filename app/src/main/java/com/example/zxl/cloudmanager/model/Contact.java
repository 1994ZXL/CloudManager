package com.example.zxl.cloudmanager.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZXL on 2016/9/13.
 */
public class Contact {
    private String contact_name; //联系人姓名
    private String contact_position; //联系人职务
    private String contact_company; //联系人公司
    private String project_name; //项目名称
    private String qq;
    private String wchat;
    private String phone;
    private String remark;
    private String pmcon_id;

    public Contact(JSONObject json) throws JSONException {
        if (json.has("contact_name"))
            contact_name = json.getString("contact_name");
        if (json.has("contact_position"))
            contact_position = json.getString("contact_position");
        if (json.has("contact_company"))
            contact_company = json.getString("contact_company");
        if (json.has("project_name"))
            project_name = json.getString("project_name");
        if (json.has("qq"))
            qq = json.getString("qq");
        if (json.has("wchat"))
            wchat = json.getString("wchat");
        if (json.has("phone"))
            phone = json.getString("phone");
        if (json.has("remark"))
            remark = json.getString("remark");
        if (json.has("pmcon_id"))
            pmcon_id = json.getString("pmcon_id");
    }

    public Contact () {

    }

    public String getContact_name() {
        return contact_name;
    }

    public String getContact_position() {
        return contact_position;
    }

    public String getContact_company() {
        return contact_company;
    }

    public String getProject_name() {
        return project_name;
    }

    public String getQq() {
        return qq;
    }

    public String getWchat() {
        return wchat;
    }

    public String getPhone() {
        return phone;
    }

    public String getRemark() {
        return remark;
    }

    public String getPmcon_id() {
        return pmcon_id;
    }
}
