package com.example.zxl.cloudmanager.manageProject.projectManagerProjectManage;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

public class PMManageProjectActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new ManageProjectSearchFragment();
    }

}
