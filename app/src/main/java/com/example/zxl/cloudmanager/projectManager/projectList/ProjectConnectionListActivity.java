package com.example.zxl.cloudmanager.projectManager.projectList;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.publicSearch.bug.BugSearchFragment;
import com.example.zxl.cloudmanager.publicSearch.list.ListSearchFragment;

public class ProjectConnectionListActivity extends SingleFragmentActivity {

    /*public PublicSearchActivity(int i){
        switch ()
    }*/
    @Override
    protected Fragment createFragment(){
        return new ListSearchFragment();
    }

}