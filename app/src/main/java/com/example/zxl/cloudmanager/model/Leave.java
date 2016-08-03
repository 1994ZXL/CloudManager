package com.example.zxl.cloudmanager.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZXL on 2016/7/12.
 */
public class Leave {
    private String mem_name; //员工姓名
    private int leave_type; //请假类型:0:没有请假，1:事假,2:病假,3:休假,4:婚假,5:其他
    private int status; //状态:1:待批准,2:已批准,3:拒绝

    private int start_time;
    private int end_time;

    private String leave_reason; //请假原因
    private String handle_opinion; //处理意见
    private String handle_time; //处理时间

    private static final String JSON_NAME = "mem_name";
    private static final String JSON_TYPE = "leave_type";
    private static final String JSON_STATE = "status";
    private static final String JSON_BEGINTIME = "start_time";
    private static final String JSON_ENDTIME = "end_time";
    private static final String JSON_LEAVE_RESON = "leave_reason";
    private static final String JSON_HANDLE_OPTION = "handle_opinion";
    private static final String JSON_HANDLE_TIME = "handle_time";

    private String[] content;

    public Leave() {

    }
    
    public void set(String[] content) {
//        setType(content[1]);
//        setState(content[2]);
//        setStart_time(content[3]);
//        setEnd_time(content[4]);
        setLeave_reason(content[5]);
        setHandle_opinion(content[6]);
//        setHandle_time(content[8]);
    }

    public Leave(JSONObject json) throws JSONException {
        if (json.has(JSON_NAME))
            mem_name = json.getString(JSON_NAME);
        if (json.has(JSON_TYPE))
            leave_type = json.getInt(JSON_TYPE);
        if (json.has(JSON_STATE))
            status = json.getInt(JSON_STATE);
        if (json.has(JSON_BEGINTIME))
            start_time = json.getInt(JSON_BEGINTIME);
        if (json.has(JSON_ENDTIME))
            end_time = json.getInt(JSON_ENDTIME);
        if (json.has(JSON_HANDLE_OPTION))
            handle_opinion = json.getString(JSON_HANDLE_OPTION);
        if (json.has(JSON_LEAVE_RESON))
            leave_reason = json.getString(JSON_LEAVE_RESON);
        if (json.has(JSON_HANDLE_TIME))
            handle_time = json.getString(JSON_HANDLE_TIME);
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_NAME, mem_name);
        json.put(JSON_TYPE, leave_type);
        json.put(JSON_STATE, status);
        json.put(JSON_BEGINTIME, start_time);
        json.put(JSON_ENDTIME, end_time);
        json.put(JSON_LEAVE_RESON, leave_reason);
        json.put(JSON_HANDLE_OPTION, handle_opinion);
        json.put(JSON_HANDLE_TIME, handle_time);
        return json;
    }


    public String getLeave_type() {
        //请假类型:0:没有请假，1:事假,2:病假,3:休假,4:婚假,5:其他
        if (leave_type == 0) {
            return "没有请假";
        } else if (leave_type == 1) {
            return "事假";
        } else if (leave_type == 2) {
            return "病假";
        } else if (leave_type == 3) {
            return "休假";
        } else if (leave_type == 4) {
            return "婚假";
        } else if (leave_type == 5) {
            return "其他";
        }
        return null;
    }

    public void setLeave_type(int leave_type) {
        this.leave_type = leave_type;
    }

    public String getStatus() {
        //状态:1:待批准,2:已批准,3:拒绝
        if (status == 1) {
            return "待批准";
        } else if (status == 2) {
            return "已批准";
        } else if (status == 3) {
            return "拒绝";
        }
        return null;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMem_name() {
        return mem_name;
    }

    public void setMem_name(String mem_name) {
        this.mem_name = mem_name;
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

    public String[] getContent() {
        return content;
    }

    public void setContent(String[] content) {
        this.content = content;
    }

    public String getLeave_reason() {
        return leave_reason;
    }

    public void setLeave_reason(String leave_reason) {
        this.leave_reason = leave_reason;
    }

    public String getHandle_opinion() {
        return handle_opinion;
    }

    public void setHandle_opinion(String handle_opinion) {
        this.handle_opinion = handle_opinion;
    }

    public String getHandle_time() {
        return handle_time;
    }

    public void setHandle_time(String handle_time) {
        this.handle_time = handle_time;
    }
}
