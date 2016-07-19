package com.example.zxl.cloudmanager.leave;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

/**
 * Created by ZXL on 2016/7/19.
 */
public class LeaveSearchActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new LeaveSearchFragment();
    }
}
