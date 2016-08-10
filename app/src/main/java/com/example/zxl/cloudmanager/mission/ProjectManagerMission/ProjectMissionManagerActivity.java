package com.example.zxl.cloudmanager.mission.ProjectManagerMission;

import android.app.Fragment;
import com.example.zxl.cloudmanager.mission.ProjectManagerMission.*;
import com.example.zxl.cloudmanager.SingleFragmentActivity;

public class ProjectMissionManagerActivity extends SingleFragmentActivity {

    /*public PublicSearchActivity(int i){
        switch ()
    }*/
    @Override
    protected Fragment createFragment(){
        return new MissionSearchFragment();
    }

}
