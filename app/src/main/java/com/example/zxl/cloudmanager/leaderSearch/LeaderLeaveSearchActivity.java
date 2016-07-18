package com.example.zxl.cloudmanager.leaderSearch;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.leave.LeaveSearchFragment;
import com.example.zxl.cloudmanager.myOvertime.OverTimeFragment;

public class LeaderLeaveSearchActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new LeaveSearchFragment();
    }

}
