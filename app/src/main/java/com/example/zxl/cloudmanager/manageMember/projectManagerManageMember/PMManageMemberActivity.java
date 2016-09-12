package com.example.zxl.cloudmanager.manageMember.projectManagerManageMember;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

public class PMManageMemberActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment(){
        return new ManageMemberSearchFragment();
    }

}
