package com.example.zxl.cloudmanager.leave.leader;

import android.app.Fragment;

import com.example.zxl.cloudmanager.leave.myLeave.MyLeaveQueryFragment;
import com.example.zxl.cloudmanager.SingleFragmentActivity;

/**
 * Created by ZXL on 2016/7/12.
 */
public class LeaveListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new MyLeaveQueryFragment();
    }
}
