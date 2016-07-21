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
    private ArrayList<String[]> contents = new ArrayList<String[]>();
    private int index;

    private LeaveMyLab(Context context) {
        this.context = context;
        content = new String[] {
                "张三",
                "病假",
                "2016.7.21",
                "2016.8.21",
                "未批准"};
        contents.add(content);

        for (index = 0; index < contents.size(); index++) {
            Leave mLeave = new Leave();
            mLeave.setName(contents.get(index)[index]);
            mLeave.setType(contents.get(index)[index + 1]);
            mLeave.setState(contents.get(index)[index + 4]);
            mLeaves.add(mLeave);
        }
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
}
