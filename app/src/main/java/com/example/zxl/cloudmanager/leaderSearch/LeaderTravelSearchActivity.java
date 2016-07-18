package com.example.zxl.cloudmanager.leaderSearch;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.check.SearchCheckFragment;
import com.example.zxl.cloudmanager.travel.TravelSearchFragment;

public class LeaderTravelSearchActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new TravelSearchFragment();
    }

}
