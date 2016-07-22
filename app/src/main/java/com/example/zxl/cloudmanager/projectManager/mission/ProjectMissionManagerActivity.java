package com.example.zxl.cloudmanager.projectManager.mission;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.publicSearch.bug.BugSearchFragment;

public class ProjectMissionManagerActivity extends SingleFragmentActivity {

    /*public PublicSearchActivity(int i){
        switch ()
    }*/
    @Override
    protected Fragment createFragment(){
        return new MissionSearchFragment();
    }

}
