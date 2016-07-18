package com.example.zxl.cloudmanager.leaderSearch;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.check.SearchCheckFragment;
import com.example.zxl.cloudmanager.publicSearch.project.ProjectSearchFragment;

public class LeaderCheckSearchActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new SearchCheckFragment();
    }

}
