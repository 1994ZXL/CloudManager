package com.example.zxl.cloudmanager.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZXL on 2016/7/13.
 */
public class Travel {


    private String mem_id; //员工姓名
    private int start_time_e; //开始时间（大）
    private int start_time_s; //开始时间（小）
    private int over_time_e; //回归时间（大）
    private int over_time_s; //回归时间（小）
    private int status; //出差状态 0：等待，1：确认，2：取消

    private int start_time;
    private int over_time;
    private String address;
    private String detail_addr;
    private String trip_reason;

    private static final String JSON_NAME = "mem_name";
    private static final String JSON_STATUS = "status";
    private static final String JSON_ADDRESS = "address";
    private static final String JSON_DETAIL_ADDR = "detail_addr";
    private static final String JSON_BEGIN_TIME = "start_time";
    private static final String JSON_END_TIME = "end_time";
    private static final String JSON_TRIP_RESON = "trip_reason";

    private String[] mContent;

    public Travel(String[] content) {
        mContent = content;
        set(mContent);
    }

    public Travel() {

    }

    public void set(String[] content) {
        setMem_id(content[0]);
       /* setStart_time(content[1]);
        setOver_time(content[2]);*/
        setAddress(content[3]);
        setDetail_addr(content[4]);
        setTrip_reason(content[5]);
    }

    public Travel(JSONObject json) throws JSONException {
        if (json.has(JSON_NAME))
            mem_id = json.getString(JSON_NAME);
        if (json.has(JSON_ADDRESS))
            address = json.getString(JSON_ADDRESS);
        if (json.has(JSON_STATUS))
            status = json.getInt(JSON_STATUS);
        if (json.has(JSON_BEGIN_TIME))
            start_time = json.getInt(JSON_BEGIN_TIME);
        if (json.has(JSON_END_TIME))
            over_time = json.getInt(JSON_END_TIME);
        if (json.has(JSON_DETAIL_ADDR))
            detail_addr = json.getString(JSON_DETAIL_ADDR);
        if (json.has(JSON_TRIP_RESON))
            trip_reason = json.getString(JSON_TRIP_RESON);

    }
    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_NAME, mem_id);
        json.put(JSON_STATUS, status);
        json.put(JSON_ADDRESS,address);
        json.put(JSON_DETAIL_ADDR, detail_addr);
        json.put(JSON_END_TIME, over_time);
        json.put(JSON_TRIP_RESON, trip_reason);
        json.put(JSON_BEGIN_TIME, start_time);
        json.put(JSON_BEGIN_TIME, start_time);
        return json;
    }

    public String getStatus() {
        //状态:0:等待,1:确认,2:取消
        if (status == 0) {
            return "等待";
        } else if (status == 1) {
            return "确认";
        } else if (status == 2) {
            return "取消";
        }
        return null;
    }
    public String[] get() {
        return mContent;
    }

    public String getMem_id() {
        return mem_id;
    }

    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }

    public int getStart_time_e() {
        return start_time_e;
    }

    public void setStart_time_e(int start_time_e) {
        this.start_time_e = start_time_e;
    }

    public int getOver_time_s() {
        return over_time_s;
    }

    public void setOver_time_s(int over_time_s) {
        this.over_time_s = over_time_s;
    }

    public int getOver_time_e() {
        return over_time_e;
    }

    public void setOver_time_e(int over_time_e) {
        this.over_time_e = over_time_e;
    }

    public int getStart_time_s() {
        return start_time_s;
    }

    public void setStart_time_s(int start_time_s) {
        this.start_time_s = start_time_s;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getOver_time() {
        return over_time;
    }

    public void setOver_time(int over_time) {
        this.over_time = over_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetail_addr() {
        return detail_addr;
    }

    public void setDetail_addr(String detail_addr) {
        this.detail_addr = detail_addr;
    }

    public String getTrip_reason() {
        return trip_reason;
    }

    public void setTrip_reason(String trip_reason) {
        this.trip_reason = trip_reason;
    }


}
