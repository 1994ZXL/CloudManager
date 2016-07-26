package com.example.zxl.cloudmanager.leaderSearch;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.check.SearchCheckFragment;
import com.example.zxl.cloudmanager.myPost.MyPostSearchFragment;

public class LeaderPostSearchActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new LeaderPostSearchFragment();
    }

}
