package com.example.zxl.cloudmanager.schedule.PMSchedule;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

/**
 * Created by ZXL on 2016/9/5.
 */
public class PMScheduleActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment(){
        return new PMScheduleSearchFragment();
    }
}
