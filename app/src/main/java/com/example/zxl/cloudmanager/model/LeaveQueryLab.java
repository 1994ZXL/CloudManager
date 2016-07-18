package com.example.zxl.cloudmanager.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/15.
 */
public class LeaveQueryLab {
    private Leave mLeave = new Leave();
    private static LeaveQueryLab sLeave;
    private ArrayList<Leave> mLeaves = new ArrayList<Leave>();
    private int i = 0;
    private int j = i;

    private LeaveQueryLab(Context context) {

        for (i = 0; i < 3; i++) {
            if (j == 0) {
                mLeave.getApplyTime();
                mLeaves.add(mLeave);
            } else if (j == 1) {
                mLeave.getType();
                mLeaves.add(mLeave);
            } else if (j == 2) {
                mLeave.getState();
                mLeaves.add(mLeave);
            }
        }
    }

    public static LeaveQueryLab newInstance(Context context) {
        if (null == sLeave) {
            sLeave = new LeaveQueryLab(context.getApplicationContext());
        }
        return sLeave;
    }

    public ArrayList<Leave> getLeaveQuery() {
        return mLeaves;
    }
}
