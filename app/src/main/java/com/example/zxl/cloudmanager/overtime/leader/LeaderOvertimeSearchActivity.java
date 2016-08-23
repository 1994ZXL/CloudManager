package com.example.zxl.cloudmanager.overtime.leader;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.overtime.checkManagerOverTime.ManagerOverTimeSearchFragment;

public class LeaderOvertimeSearchActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new ManagerOverTimeSearchFragment();
    }

}
