package com.example.zxl.cloudmanager.projectManager.memberManager;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.publicSearch.bug.BugSearchFragment;

public class ProjectMemberManagerActivity extends SingleFragmentActivity {

    /*public PublicSearchActivity(int i){
        switch ()
    }*/
    @Override
    protected Fragment createFragment(){
        return new MemberSearchFragment();
    }

}
