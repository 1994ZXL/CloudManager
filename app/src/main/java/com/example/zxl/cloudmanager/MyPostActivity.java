package com.example.zxl.cloudmanager;

import android.app.Fragment;

/**
 * Created by ZXL on 2016/7/12.
 */
public class MyPostActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new MyPostFragment();
    }
}
