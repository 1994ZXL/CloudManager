package com.example.zxl.cloudmanager.bug.myBug;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;


/**
 * Created by ZXL on 2016/7/13.
 */
public class MyBugActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new MyBugFragment();
    }
}
