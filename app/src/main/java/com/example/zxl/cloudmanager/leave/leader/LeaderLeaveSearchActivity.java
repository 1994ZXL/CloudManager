package com.example.zxl.cloudmanager.leave.leader;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.leave.myLeave.MyLeaveSearchFragment;

public class LeaderLeaveSearchActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new MyLeaveSearchFragment();
    }

}
