package com.example.zxl.cloudmanager;

import android.app.Fragment;

/**
 * Created by ZXL on 2016/7/15.
 */
public class ManagerLeaveActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new ManagerLeaveQueryFragment();
    }
}

