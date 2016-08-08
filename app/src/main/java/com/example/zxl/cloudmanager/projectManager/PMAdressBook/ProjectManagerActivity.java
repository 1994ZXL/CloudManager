package com.example.zxl.cloudmanager.projectManager.PMAdressBook;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

public class ProjectManagerActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new ProjectManagerSearchFragment();
    }

}
