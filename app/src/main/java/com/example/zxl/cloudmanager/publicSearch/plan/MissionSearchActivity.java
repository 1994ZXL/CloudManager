package com.example.zxl.cloudmanager.publicSearch.plan;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

public class MissionSearchActivity extends SingleFragmentActivity {

    /*public PublicSearchActivity(int i){
        switch ()
    }*/
    @Override
    protected Fragment createFragment(){
        return new PlanFragment();
    }
}
