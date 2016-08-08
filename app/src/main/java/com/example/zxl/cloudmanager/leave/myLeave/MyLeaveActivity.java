package com.example.zxl.cloudmanager.leave.myLeave;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

/**
 * Created by ZXL on 2016/7/12.
 */
public class MyLeaveActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new MyLeaveFragment();
    }
}
