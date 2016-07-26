package com.example.zxl.cloudmanager.leaderSearch;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

public class LeaderPostSearchActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new LeaderPostSearchFragment();
    }
}
