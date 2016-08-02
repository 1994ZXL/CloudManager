package com.example.zxl.cloudmanager.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

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
                mLeaves.add(mLeave);
            } else if (j == 1) {
                mLeave.getLeave_type();
                mLeaves.add(mLeave);
            } else if (j == 2) {
                mLeave.getStatus();
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

    public void setLeaves(ArrayList<Leave> mLeaves) {
        this.mLeaves =  mLeaves;
    }
}
