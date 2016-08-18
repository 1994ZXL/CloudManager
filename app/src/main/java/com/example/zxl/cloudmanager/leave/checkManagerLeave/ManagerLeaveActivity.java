package com.example.zxl.cloudmanager.leave.checkManagerLeave;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.leave.myLeave.MyLeaveSearchFragment;

/**
 * Created by ZXL on 2016/7/15.
 */
public class ManagerLeaveActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new MyLeaveSearchFragment();
    }
}

