package com.example.zxl.cloudmanager.mission.publicSearchMission;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.mission.projectManagerMission.MissionSearchFragment;

public class PublicSearchMissionActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment(){
        return new MissionSearchFragment();
    }
}
