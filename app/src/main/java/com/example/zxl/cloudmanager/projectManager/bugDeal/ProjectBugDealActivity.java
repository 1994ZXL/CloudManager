package com.example.zxl.cloudmanager.projectManager.bugDeal;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.publicSearch.bug.BugSearchFragment;

public class ProjectBugDealActivity extends SingleFragmentActivity {

    /*public PublicSearchActivity(int i){
        switch ()
    }*/
    @Override
    protected Fragment createFragment(){
        return new BugSearchFragment();
    }

}
