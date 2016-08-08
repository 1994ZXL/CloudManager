package com.example.zxl.cloudmanager.check.checkManager;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

/**
 * Created by ZXL on 2016/7/14.
 */
public class ManagerCheckAcitvity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new ManagerCheckQueryFragment();
    }
}
