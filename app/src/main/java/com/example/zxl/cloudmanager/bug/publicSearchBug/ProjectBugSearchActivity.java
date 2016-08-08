package com.example.zxl.cloudmanager.bug.publicSearchBug;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

public class ProjectBugSearchActivity extends SingleFragmentActivity {

    /*public PublicSearchActivity(int i){
        switch ()
    }*/
    @Override
    protected Fragment createFragment(){
        return new BugSearchFragment();
    }

}
