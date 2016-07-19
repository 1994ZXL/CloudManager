package com.example.zxl.cloudmanager.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/19.
 */
public class LeaveMyLab {
    private Leave mLeave = new Leave();
    private static LeaveMyLab sLeave;
    private ArrayList<Leave> mLeaves = new ArrayList<Leave>();

    private Context context;

    private String[] content;

    private LeaveMyLab(Context context) {
        this.context = context;

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

    public void set(ArrayList<Leave> mLeaves) {
        this.mLeaves = mLeaves;
    }
}
