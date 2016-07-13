package com.example.zxl.cloudmanager;

import android.app.Fragment;

/**
 * Created by ZXL on 2016/7/13.
 */
public class MyUseCaseActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new MyUseCaseFragment();
    }
}
