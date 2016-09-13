package com.example.zxl.cloudmanager.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZXL on 2016/7/28.
 */
public class Project {
    private String pm_id; //项目id
    private String project_name; //项目名称
    private String goon_technical; //技术负责人
    private String goon_business; //客服
    private String project_summary; //项目内容
    private String belong_unit; //客户单位
    private String custom_name; //客户联系人
    private String custom_phone; //客户手机
    private String pm_master_name; //项目主管名字
    private int ready_time; //准备开始时间
    private int goon_time; //运维开始时间
    private int todo_time; //开发开始时间
    private int cancel_time; //取消时间
    private int finshed_time; //结束时间
    private int goon_actual_start_time; //运维实际开始时间
    private int goon_actual_end_time; //运维实际结束时间
    private int project_state; //项目状态(0:取消，1:准备，2:开发，3:维护，4:结束)

    public Project(JSONObject json) throws JSONException {
        if (json.has("pm_id"))
            pm_id = json.getString("pm_id");
        if (json.has("project_name"))
            project_name = json.getString("project_name");
        if (json.has("goon_technical"))
            goon_technical = json.getString("goon_technical");
        if (json.has("goon_business"))
            goon_business = json.getString("goon_business");
        if (json.has("project_summary"))
            project_summary = json.getString("project_summary");
        if (json.has("belong_unit"))
            belong_unit = json.getString("belong_unit");
        if (json.has("custom_name"))
            custom_name = json.getString("custom_name");
        if (json.has("custom_phone"))
            custom_phone = json.getString("custom_phone");
        if (json.has("pm_master_name"))
            pm_master_name = json.getString("pm_master_name");
        if (json.has("ready_time"))
            ready_time = json.getInt("ready_time");
        if (json.has("goon_time"))
            goon_time = json.getInt("goon_time");
        if (json.has("todo_time"))
            todo_time = json.getInt("todo_time");
        if (json.has("cancel_time"))
            cancel_time = json.getInt("cancel_time");
        if (json.has("finshed_time"))
            finshed_time = json.getInt("finshed_time");
        if (json.has("goon_actual_start_time"))
            goon_actual_start_time = json.getInt("goon_actual_start_time");
        if (json.has("goon_actual_end_time"))
            goon_actual_end_time = json.getInt("goon_actual_end_time");
        if (json.has("project_state"))
            project_state = json.getInt("project_state");
    }

    public Project() {

    }

    public String getPm_master_name() {
        return pm_master_name;
    }

    public void setPm_master_name(String pm_master_name) {
        this.pm_master_name = pm_master_name;
    }

    public String getPm_id() {
        return pm_id;
    }

    public void setPm_id(String pm_id) {
        this.pm_id = pm_id;
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

    public int getGoon_actual_end_time() {
        return goon_actual_end_time;
    }

    public void setGoon_actual_end_time(int goon_actual_end_time) {
        this.goon_actual_end_time = goon_actual_end_time;
    }

    public int getGoon_actual_start_time() {
        return goon_actual_start_time;
    }

    public void setGoon_actual_start_time(int goon_actual_start_time) {
        this.goon_actual_start_time = goon_actual_start_time;
    }

    public int getFinshed_time() {
        return finshed_time;
    }

    public void setFinshed_time(int finshed_time) {
        this.finshed_time = finshed_time;
    }

    public int getCancel_time() {
        return cancel_time;
    }

    public void setCancel_time(int cancel_time) {
        this.cancel_time = cancel_time;
    }

    public int getTodo_time() {
        return todo_time;
    }

    public void setTodo_time(int todo_time) {
        this.todo_time = todo_time;
    }

    public int getGoon_time() {
        return goon_time;
    }

    public void setGoon_time(int goon_time) {
        this.goon_time = goon_time;
    }

    public int getReady_time() {
        return ready_time;
    }

    public void setReady_time(int ready_time) {
        this.ready_time = ready_time;
    }

    public String getCustom_phone() {
        return custom_phone;
    }

    public void setCustom_phone(String custom_phone) {
        this.custom_phone = custom_phone;
    }

    public String getCustom_name() {
        return custom_name;
    }

    public void setCustom_name(String custom_name) {
        this.custom_name = custom_name;
    }

    public String getBelong_unit() {
        return belong_unit;
    }

    public void setBelong_unit(String belong_unit) {
        this.belong_unit = belong_unit;
    }

    public String getProject_summary() {
        return project_summary;
    }

    public void setProject_summary(String project_summary) {
        this.project_summary = project_summary;
    }

    public String getGoon_business() {
        return goon_business;
    }

    public void setGoon_business(String goon_business) {
        this.goon_business = goon_business;
    }

    public String getGoon_technical() {
        return goon_technical;
    }

    public void setGoon_technical(String goon_technical) {
        this.goon_technical = goon_technical;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }
}
