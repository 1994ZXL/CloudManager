package com.example.zxl.cloudmanager.model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZXL on 2016/7/18.
 */
public class User {
    private String mem_name;
    private int gender; //性别
    private String phone; //手机
    private String qq; //qq
    private String wchat; //微信
    private String email; //邮箱
    private String address; //地址
    private String detail_addr; // 详细地址
    private String card; //身份证号
    private String service_state; //在职状态 0在职 1休假 2离职
    private String punch_mgr; //考勤主管
    private String comp_name; //公司名
    private String mem_job; //职位(1.领导 2.项目负责人 3.一般员工)
    private String user_type; //用户类型
    private String user_id; //用户id

    private static final String JSON_USER_NAME = "mem_name";
    private static final String JSON_GENDER = "gender";
    private static final String JSON_PHONE = "phone";
    private static final String JSON_QQ = "qq";
    private static final String JSON_WCHAT = "wchat";
    private static final String JSON_EMAIL = "email";
    private static final String JSON_ADDRESS = "address";
    private static final String JSON_DETAIL_ADDR = "detail_addr";
    private static final String JSON_CARD = "card";
    private static final String JSON_SERVICE_STATE = "service_state";
    private static final String JSON_PUNCH_MGR = "punch_mgr";
    private static final String JSON_COMP_NAME = "comp_name";
    private static final String JSON_MEM_JOB = "mem_job";
    private static final String JSON_USER_TYPE = "user_type";
    private static final String JSON_USER_ID = "user_id";

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
            mem_name = json.getString(JSON_USER_NAME);
        if (json.has(JSON_GENDER))
            gender = json.getInt(JSON_GENDER);
        if (json.has(JSON_PHONE))
            phone = json.getString(JSON_PHONE);
        if (json.has(JSON_QQ))
            qq = json.getString(JSON_QQ);
        if (json.has(JSON_WCHAT))
            wchat = json.getString(JSON_WCHAT);
        if (json.has(JSON_EMAIL))
            email = json.getString(JSON_EMAIL);
        if (json.has(JSON_ADDRESS))
            address = json.getString(JSON_ADDRESS);
        if (json.has(JSON_DETAIL_ADDR))
            detail_addr = json.getString(JSON_DETAIL_ADDR);
        if (json.has(JSON_CARD))
            card = json.getString(JSON_CARD);
        if (json.has(JSON_SERVICE_STATE)){
            //在职状态 0在职 1休假 2离职
            if (json.getInt(JSON_SERVICE_STATE) == 0) {
                service_state = "在职";
            } else if (json.getInt(JSON_SERVICE_STATE) == 1){
                service_state = "休假";
            } else if (json.getInt(JSON_SERVICE_STATE) == 2) {
                service_state = "离职";
            }
        }
        if (json.has(JSON_PUNCH_MGR))
            punch_mgr = json.getString(JSON_PUNCH_MGR);
        if (json.has(JSON_COMP_NAME))
            comp_name = json.getString(JSON_COMP_NAME);
        if (json.has(JSON_MEM_JOB))
            mem_job = json.getString(JSON_MEM_JOB);
        if (json.has(JSON_USER_TYPE))
            user_type = json.getString(JSON_USER_TYPE);
        if (json.has(JSON_USER_ID))
            user_id = json.getString(JSON_USER_ID);
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

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
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

    public String getDetail_addr() {
        return detail_addr;
    }

    public void setDetail_addr(String detail_addr) {
        this.detail_addr = detail_addr;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getService_state() {
        return service_state;
    }

    public void setService_state(String service_state) {
        this.service_state = service_state;
    }

    public String getPunch_mgr() {
        return punch_mgr;
    }

    public void setPunch_mgr(String punch_mgr) {
        this.punch_mgr = punch_mgr;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
