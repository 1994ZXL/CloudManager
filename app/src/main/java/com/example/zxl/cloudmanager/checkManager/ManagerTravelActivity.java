package com.example.zxl.cloudmanager.checkManager;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.check.SearchCheckFragment;
import com.example.zxl.cloudmanager.travel.TravelSearchFragment;

public class ManagerTravelActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new TravelSearchFragment();
    }

}
