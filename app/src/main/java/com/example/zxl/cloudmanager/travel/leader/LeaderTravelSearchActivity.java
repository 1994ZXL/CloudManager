package com.example.zxl.cloudmanager.travel.leader;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.travel.myTravel.MyTravelSearchFragment;

public class LeaderTravelSearchActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new MyTravelSearchFragment();
    }

}
