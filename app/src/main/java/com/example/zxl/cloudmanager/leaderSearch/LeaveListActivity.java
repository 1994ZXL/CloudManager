package com.example.zxl.cloudmanager.leaderSearch;

import android.app.Fragment;

import com.example.zxl.cloudmanager.MyLeaveFragment;
import com.example.zxl.cloudmanager.MyLeaveQueryFragment;
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
