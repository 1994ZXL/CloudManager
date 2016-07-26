package com.example.zxl.cloudmanager.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/19.
 */
public class LeaveMyLab {
    private static LeaveMyLab sLeave;
    private ArrayList<Leave> mLeaves = new ArrayList<Leave>();

    private Context context;

    private String[] content;
    private String[] content2;
    private int index;

    private LeaveMyLab(Context context) {
        this.context = context;
        content = new String[] {
                "张三",
                "病假",
                "未批准",
                "2016.7.21",
                "2016.7.26",
                "reson",
                "suggestion",
                "2016.7.21",
                "2016.7.21",};
        content2 = new String[] {
                "张三",
                "丧假",
                "未批准",
                "2016.7.21",
                "2016.7.26",
                "reson",
                "suggestion",
                "2016.7.21",
                "2016.7.21",};
        set(content);
        set(content2);
    }

    public static LeaveMyLab newInstance(Context context) {
        if (null == sLeave) {
            sLeave = new LeaveMyLab(context.getApplicationContext());
        }
        return sLeave;
    }

    public ArrayList<Leave> get() {
        return mLeaves;
    }

    public void add(Leave mLeaves) {
        this.mLeaves.add(mLeaves);
    }

    private void set(String[] content) {
        Leave leave = new Leave();
        leave.set(content);
        mLeaves.add(leave);
    }
}
