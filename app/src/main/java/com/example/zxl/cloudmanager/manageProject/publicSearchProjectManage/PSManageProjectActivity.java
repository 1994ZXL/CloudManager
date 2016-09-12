package com.example.zxl.cloudmanager.manageProject.publicSearchProjectManage;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.manageProject.projectManagerProjectManage.ManageProjectSearchFragment;

public class PSManageProjectActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment(){
        return new ManageProjectSearchFragment();
    }

}
