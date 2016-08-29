package com.example.zxl.cloudmanager.bug.publicSearchBug;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

public class PublicBugSearchActivity extends SingleFragmentActivity {

    
    @Override
    protected Fragment createFragment(){
        return new BugSearchFragment();
    }

}
