package com.example.zxl.cloudmanager.operation;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;


/**
 * Created by ZXL on 2016/7/13.
 */
public class MyOperationActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new OperationFragment();
    }
}
