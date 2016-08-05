package com.example.zxl.cloudmanager.projectManager.usecase;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.publicSearch.list.ListSearchFragment;

public class ProjectManagerUsecaseActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new UsecaseSearchFragment();
    }

}
