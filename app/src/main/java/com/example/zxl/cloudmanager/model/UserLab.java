package com.example.zxl.cloudmanager.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/18.
 */
public class UserLab {
    private ArrayList<User> mUsers = new ArrayList<User>();
    private static UserLab sUserLab;

    private Context context;

    private UserLab(Context context) {
//        this.context = context;
//        User user = new User();
//        user.setName("张三");
//        user.setAddress("成都");
//        user.setPhone("12345678987");
//        user.setQq("123456");
//        user.setWechat("12345678987");
//        mUsers.add(user);
    }

    public static UserLab newInstance(Context context) {
        if (null == sUserLab) {
            sUserLab = new UserLab(context.getApplicationContext());
        }
        return sUserLab;
    }

    public ArrayList<User> getUsers() {
        return mUsers;
    }
}
