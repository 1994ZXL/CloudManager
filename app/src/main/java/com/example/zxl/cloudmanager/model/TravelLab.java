package com.example.zxl.cloudmanager.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/13.
 */
public class TravelLab {
    private ArrayList<Travel> mTravels = new ArrayList<Travel>();
    private static TravelLab sTravelLab;

    private String[] content;
    private String[] content2;

    private TravelLab(Context context) {
        /*content = new String[] {
                "张三",
                "2016.7.25",
                "2016.7.26",
                "2016.7.27",
                "2016.7.28",
                "出差内容",
                "出差地址",
                "出差详细地址",
                "正常",};

        content2 = new String[] {
                "李四",
                "2016.7.25",
                "2016.7.26",
                "2016.7.27",
                "2016.7.28",
                "出差内容",
                "出差地址",
                "出差详细地址",
                "取消",};
        set(content);
        set(content2);*/
    }

    public static TravelLab newInstance(Context context) {
        if (null == sTravelLab) {
            sTravelLab = new TravelLab(context.getApplicationContext());
        }
        return sTravelLab;
    }

    public ArrayList<Travel> getTravels() {
        return mTravels;
    }


}
