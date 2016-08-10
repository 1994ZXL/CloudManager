package com.example.zxl.cloudmanager.model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/11.
 */
public class Check {
    private String mem_name; //员工姓名
    private String puncher_name; //考勤地址
    private String master_id; //考勤主管id
    private String att_id; //考勤id
    private int s_time; //规定上班时间
    private int e_time; //规定下班时间
    private int s_att_time; //上班签到时间
    private int e_att_time; //下班签到时间
    private int att_date; //签到日期
    private String master_name; //考勤主管名
    private boolean has_my_leave; //是否请假
    private String leave_id; //请假id
    private int late_min; //迟到分钟数
    private int early_min; //早退分钟数
    private boolean has_my_work; //是否加班
    private String work_id; //加班id
    private boolean has_my_trip; //是否出差
    private String trip_id; //出差id

    private static final String JSON_NAME = "mem_name";
    private static final String JSON_PUNCHER_NAME = "puncher_name";
    private static final String JSON_MASTER_ID = "master_id";
    private static final String JSON_ATT_ID = "att_id";
    private static final String JSON_S_TIME = "s_time";
    private static final String JSON_E_TIME = "e_time";
    private static final String JSON_S_ATT_TIME = "s_att_time";
    private static final String JSON_E_ATT_TIME = "e_att_time";
    private static final String JSON_ATT_DATE = "att_date";
    private static final String JSON_MASTER_NAME = "master_name";
    private static final String JSON_HAS_MY_LEAVE = "has_my_leave";
    private static final String JSON_LEAVE_ID = "leave_id";
    private static final String JSON_LATE_MIN = "late_min";
    private static final String JSON_EARLY_MIN = "early_min";
    private static final String JSON_HAS_MY_WORK = "has_my_work";
    private static final String JSON_WORK_ID = "work_id";
    private static final String JSON_HAS_MY_TRIP = "has_my_trip";
    private static final String JSON_TRIP_ID = "trip_id";

    public Check() {

    }

    public Check(JSONObject json) throws JSONException {
        if (json.has(JSON_NAME))
            mem_name = json.getString(JSON_NAME);
        if (json.has(JSON_PUNCHER_NAME))
            puncher_name = json.getString(JSON_PUNCHER_NAME);
        if (json.has(JSON_MASTER_ID))
            master_id = json.getString(JSON_MASTER_ID);
        if (json.has(JSON_ATT_ID))
            att_id = json.getString(JSON_ATT_ID);
        if (json.has(JSON_S_TIME))
            s_time = json.getInt(JSON_S_TIME);
        if (json.has(JSON_E_TIME))
            e_time = json.getInt(JSON_E_TIME);
        if (json.has(JSON_S_ATT_TIME))
            s_att_time = json.getInt(JSON_S_ATT_TIME);
        if (json.has(JSON_E_ATT_TIME))
            e_att_time = json.getInt(JSON_E_ATT_TIME);
        if (json.has(JSON_ATT_DATE))
            att_date = json.getInt(JSON_ATT_DATE);
        if (json.has(JSON_MASTER_NAME))
            master_name = json.getString(JSON_MASTER_NAME);
        if (json.has(JSON_HAS_MY_LEAVE))
            has_my_leave = json.getBoolean(JSON_HAS_MY_LEAVE);
        if (json.has(JSON_LEAVE_ID))
            leave_id = json.getString(JSON_LEAVE_ID);
        if (json.has(JSON_LATE_MIN))
            late_min = json.getInt(JSON_LATE_MIN);
        if (json.has(JSON_EARLY_MIN))
            early_min = json.getInt(JSON_EARLY_MIN);
        if (json.has(JSON_HAS_MY_WORK))
            has_my_work = json.getBoolean(JSON_HAS_MY_WORK);
        if (json.has(JSON_WORK_ID))
            work_id = json.getString(JSON_WORK_ID);
        if (json.has(JSON_HAS_MY_TRIP))
            has_my_trip = json.getBoolean(JSON_HAS_MY_TRIP);
        if (json.has(JSON_TRIP_ID))
            trip_id = json.getString(JSON_TRIP_ID);
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_NAME,mem_name);

        return json;
    }

    public int getAtt_date() {
        return att_date;
    }

    public void setAtt_date(int att_date) {
        this.att_date = att_date;
    }

    public String getMem_name() {
        return mem_name;
    }

    public void setMem_name(String mem_name) {
        this.mem_name = mem_name;
    }

    public String getPuncher_name() {
        return puncher_name;
    }

    public void setPuncher_name(String puncher_name) {
        this.puncher_name = puncher_name;
    }

    public String getMaster_id() {
        return master_id;
    }

    public void setMaster_id(String master_id) {
        this.master_id = master_id;
    }

    public String getAtt_id() {
        return att_id;
    }

    public void setAtt_id(String att_id) {
        this.att_id = att_id;
    }

    public int getS_time() {
        return s_time;
    }

    public void setS_time(int s_time) {
        this.s_time = s_time;
    }

    public int getE_time() {
        return e_time;
    }

    public void setE_time(int e_time) {
        this.e_time = e_time;
    }

    public int getS_att_time() {
        return s_att_time;
    }

    public void setS_att_time(int s_att_time) {
        this.s_att_time = s_att_time;
    }

    public String getMaster_name() {
        return master_name;
    }

    public void setMaster_name(String master_name) {
        this.master_name = master_name;
    }

    public int getE_att_time() {
        return e_att_time;
    }

    public void setE_att_time(int e_att_time) {
        this.e_att_time = e_att_time;
    }

    public String getLeave_id() {
        return leave_id;
    }

    public void setLeave_id(String leave_id) {
        this.leave_id = leave_id;
    }

    public boolean isHas_my_leave() {
        return has_my_leave;
    }

    public void setHas_my_leave(boolean has_my_leave) {
        this.has_my_leave = has_my_leave;
    }

    public int getEarly_min() {
        return early_min;
    }

    public void setEarly_min(int early_min) {
        this.early_min = early_min;
    }

    public int getLate_min() {
        return late_min;
    }

    public void setLate_min(int late_min) {
        this.late_min = late_min;
    }

    public boolean isHas_my_work() {
        return has_my_work;
    }

    public void setHas_my_work(boolean has_my_work) {
        this.has_my_work = has_my_work;
    }

    public String getWork_id() {
        return work_id;
    }

    public void setWork_id(String work_id) {
        this.work_id = work_id;
    }

    public boolean isHas_my_trip() {
        return has_my_trip;
    }

    public void setHas_my_trip(boolean has_my_trip) {
        this.has_my_trip = has_my_trip;
    }

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

}
