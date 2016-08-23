package com.example.zxl.cloudmanager.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/22.
 */
public class OverTimeLab {
    private static OverTimeLab sOverTimeLab;
    private ArrayList<OverTime> mOverTimes = new ArrayList<OverTime>();
    private OverTime mOverTime = new OverTime();

    private Context context;

    private String[] content;
    private String[] content2;
    private int index;

    private OverTimeLab(Context context) {
        this.context = context;
        /*content = new String[]{
                "张三",
                "项目名称",
                "2016.7.22",
                "2016.7.23",
                "1天",
                "作死",
                "2天"};

        content2 = new String[]{
                "张三",
                "项目名称",
                "2016.7.23",
                "2016.7.24",
                "1天",
                "作死",
                "2天"};

        set(content);
        set(content2);*/
    }

    public static OverTimeLab newInstance(Context context) {
        if (null == sOverTimeLab) {
            sOverTimeLab = new OverTimeLab(context.getApplicationContext());
        }
        return sOverTimeLab;
    }

    public ArrayList<OverTime> get() {
        return mOverTimes;
    }

    public void set(String[] content) {
        OverTime overTime = new OverTime();
        mOverTimes.add(overTime);
    }
}
