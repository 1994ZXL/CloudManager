package com.example.zxl.cloudmanager.check.myCheck;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

/**
 * Created by ZXL on 2016/7/11.
 */
public class MyCheckActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new MyCheckFragment();
    }
}
