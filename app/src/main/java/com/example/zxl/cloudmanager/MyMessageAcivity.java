package com.example.zxl.cloudmanager;

import android.app.Fragment;

/**
 * Created by ZXL on 2016/7/11.
 */
public class MyMessageAcivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new MyMessageFragment();
    }
}
