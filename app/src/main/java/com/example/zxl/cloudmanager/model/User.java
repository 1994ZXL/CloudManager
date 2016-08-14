package com.example.zxl.cloudmanager.model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZXL on 2016/7/18.
 */
public class User {
    private String user_name; //用户名
    private String mem_name; //员工名
    private String mem_sex; //性别
    private String phone; //手机
    private String qq; //qq
    private String wchat; //微信
    private String email; //邮箱
    private String mem_region; //地址
    private String mem_addr; // 详细地址
    private String card; //身份证号
    private String mem_state; //在职状态 0在职 1休假 2离职
    private String puncher_name; //考勤地址
    private String puncher_master; //考勤主管
    private String comp_name; //公司名
    private int join_time; //入职时间
    private String is_exception; //是否考勤
    private String mem_job; //职位(1.领导 2.项目负责人 3.一般员工)
    private String user_type; //用户类型
    private String user_id; //用户id
    private String comp_id; //公司id

    private static final String JSON_USER_NAME = "user_name";
    private static final String JSON_MEM_NAME = "mem_name";
    private static final String JSON_MEM_SEX = "mem_sex";
    private static final String JSON_PHONE = "phone";
    private static final String JSON_QQ = "qq";
    private static final String JSON_WCHAT = "wchat";
    private static final String JSON_EMAIL = "email";
    private static final String JSON_MEM_REGION = "mem_region";
    private static final String JSON_MEM_ADDR = "mem_addr";
    private static final String JSON_CARD = "card";
    private static final String JSON_MEM_STATE = "mem_state";
    private static final String JSON_PUNCHER_NAME = "puncher_name";
    private static final String JSON_PUNCHER_MASTER = "puncher_master";
    private static final String JSON_COMP_NAME = "comp_name";
    private static final String JSON_JOIN_TIME = "join_time";
    private static final String JSON_IS_EXCEPTION = "is_exception";
    private static final String JSON_MEM_JOB = "mem_job";
    private static final String JSON_USER_TYPE = "user_type";
    private static final String JSON_USER_ID = "user_id";
    private static final String JSON_COMP_ID = "comp_id";

    private static User sUser;

    private User() {

    }

    public static User newInstance() {
        if (null == sUser) {
            sUser = new User();
        }
        return sUser;
    }

    public void setUser(JSONObject json) throws JSONException {
        if (json.has(JSON_USER_NAME))
            user_name = json.getString(JSON_USER_NAME);
        if (json.has(JSON_MEM_NAME))
            mem_name = json.getString(JSON_MEM_NAME);
        if (json.has(JSON_MEM_SEX))
            mem_sex = json.getString(JSON_MEM_SEX);
        if (json.has(JSON_PHONE))
            phone = json.getString(JSON_PHONE);
        if (json.has(JSON_QQ))
            qq = json.getString(JSON_QQ);
        if (json.has(JSON_WCHAT))
            wchat = json.getString(JSON_WCHAT);
        if (json.has(JSON_EMAIL))
            email = json.getString(JSON_EMAIL);
        if (json.has(JSON_MEM_REGION))
            mem_region = json.getString(JSON_MEM_REGION);
        if (json.has(JSON_MEM_ADDR))
            mem_addr = json.getString(JSON_MEM_ADDR);
        if (json.has(JSON_CARD))
            card = json.getString(JSON_CARD);
        if (json.has(JSON_MEM_STATE))
            mem_state = json.getString(JSON_MEM_STATE);
        if (json.has(JSON_PUNCHER_NAME))
            puncher_name = json.getString(JSON_PUNCHER_NAME);
        if (json.has(JSON_PUNCHER_MASTER))
            puncher_master = json.getString(JSON_PUNCHER_MASTER);
        if (json.has(JSON_COMP_NAME))
            comp_name = json.getString(JSON_COMP_NAME);
        if (json.has(JSON_JOIN_TIME))
            join_time = json.getInt(JSON_JOIN_TIME);
        if (json.has(JSON_IS_EXCEPTION))
            is_exception = json.getString(JSON_IS_EXCEPTION);
        if (json.has(JSON_MEM_JOB))
            mem_job = json.getString(JSON_MEM_JOB);
        if (json.has(JSON_USER_TYPE))
            user_type = json.getString(JSON_USER_TYPE);
        if (json.has(JSON_USER_ID))
            user_id = json.getString(JSON_USER_ID);
        if (json.has(JSON_COMP_ID))
            comp_id = json.getString(JSON_COMP_ID);
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMem_name() {
        return mem_name;
    }

    public void setMem_name(String mem_name) {
        this.mem_name = mem_name;
    }

    public String getComp_id() {
        return comp_id;
    }

    public void setComp_id(String comp_id) {
        this.comp_id = comp_id;
    }

    public String getWchat() {
        return wchat;
    }

    public void setWchat(String wchat) {
        this.wchat = wchat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMem_sex() {
        return mem_sex;
    }

    public void setMem_sex(String mem_sex) {
        this.mem_sex = mem_sex;
    }

    public void setIs_exception(String is_exception) {
        this.is_exception = is_exception;
    }

    public String getMem_region() {
        return mem_region;
    }

    public void setMem_region(String mem_region) {
        this.mem_region = mem_region;
    }

    public String getMem_addr() {
        return mem_addr;
    }

    public void setMem_addr(String mem_addr) {
        this.mem_addr = mem_addr;
    }

    public String getMem_state() {
        return mem_state;
    }

    public void setMem_state(String mem_state) {
        this.mem_state = mem_state;
    }

    public String getPuncher_name() {
        return puncher_name;
    }

    public void setPuncher_name(String puncher_name) {
        this.puncher_name = puncher_name;
    }

    public String getPuncher_master() {
        return puncher_master;
    }

    public void setPuncher_master(String puncher_master) {
        this.puncher_master = puncher_master;
    }

    public int getJoin_time() {
        return join_time;
    }

    public void setJoin_time(int join_time) {
        this.join_time = join_time;
    }

    public String getIs_exception() {
        return is_exception;
    }

    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    public String getMem_job() {
        return mem_job;
    }

    public void setMem_job(String mem_job) {
        this.mem_job = mem_job;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }


}
