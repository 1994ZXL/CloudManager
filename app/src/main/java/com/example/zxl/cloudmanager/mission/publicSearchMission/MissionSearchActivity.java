package com.example.zxl.cloudmanager.mission.publicSearchMission;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

public class MissionSearchActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment(){
        return new PlanFragment();
    }
}
