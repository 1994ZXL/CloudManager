package com.example.zxl.cloudmanager.manageMember.publicSearchManageMember;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.manageMember.projectManagerManageMember.ManageMemberSearchFragment;

public class PSManageMemberActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment(){
        return new ManageMemberSearchFragment();
    }

}
