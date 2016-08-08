package com.example.zxl.cloudmanager.overtime.checkManagerOverTime;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

public class ManagerOvertimeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new ManagerOverTimeSearchFragment();
    }

}
