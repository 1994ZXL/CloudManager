package com.example.zxl.cloudmanager.mission.projectManagerMission;

import android.app.Fragment;
import com.example.zxl.cloudmanager.SingleFragmentActivity;

public class ProjectMissionManagerActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new MissionSearchFragment();
    }

}
