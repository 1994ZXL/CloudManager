package com.example.zxl.cloudmanager.usecase.myUseCase;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

/**
 * Created by ZXL on 2016/7/13.
 */
public class MyUseCaseActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new MyUseCaseFragment();
    }
}
