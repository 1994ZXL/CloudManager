package com.example.zxl.cloudmanager.model;

import android.content.Context;

/**
 * Created by ZXL on 2016/7/18.
 */
public class User {
    private String user_name;
    private String password;
    private String phone;
    private String qq;
    private String wechat;
    private String address;
    private String user_type;
    private static User sUser;

    private Context context;

    private User(Context context) {
        this.context = context;

    }

    public static User newInstance(Context context) {
        if (null == sUser) {
            sUser = new User(context.getApplicationContext());
        }
        return sUser;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
