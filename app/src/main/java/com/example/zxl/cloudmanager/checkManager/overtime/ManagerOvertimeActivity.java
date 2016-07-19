package com.example.zxl.cloudmanager.checkManager.overtime;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

public class ManagerOvertimeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new OverTimeSearchFragment();
    }

}
