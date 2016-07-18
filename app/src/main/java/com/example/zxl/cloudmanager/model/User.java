package com.example.zxl.cloudmanager.model;

import android.content.Context;

/**
 * Created by ZXL on 2016/7/18.
 */
public class User {
    private String name;
    private String phone;
    private String qq;
    private String wechat;
    private String address;
    private static User sUser;

    private Context context;

    private User(Context context) {
        this.context = context;
        name = "张三";
    }

    public static User newInstance(Context context) {
        if (null == sUser) {
            sUser = new User(context.getApplicationContext());
        }
        return sUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
