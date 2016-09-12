package com.example.zxl.cloudmanager.usecase.publicSearchUseCase;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

public class UsecaseSearchActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new UsecaseFragment();
    }

}
