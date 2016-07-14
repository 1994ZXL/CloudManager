package com.example.zxl.cloudmanager;

import android.app.Fragment;

/**
 * Created by ZXL on 2016/7/14.
 */
public class ManagerCheckQueryAcitvity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new ManagerCheckQueryFragment();
    }
}
