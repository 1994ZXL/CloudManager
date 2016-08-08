package com.example.zxl.cloudmanager.usecase.projectManagerUseCase;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

public class ProjectManagerUsecaseActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new UsecaseSearchFragment();
    }

}
