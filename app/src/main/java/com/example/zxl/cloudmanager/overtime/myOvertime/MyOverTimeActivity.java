package com.example.zxl.cloudmanager.overtime.myOvertime;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

/**
 * Created by ZXL on 2016/7/13.
 */
public class MyOverTimeActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new MyOverTimeFragment();
    }
}
