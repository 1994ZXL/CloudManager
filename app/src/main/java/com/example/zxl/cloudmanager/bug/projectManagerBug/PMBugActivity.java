package com.example.zxl.cloudmanager.bug.projectManagerBug;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.bug.publicSearchBug.BugSearchFragment;

public class PMBugActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment(){
        return new BugSearchFragment();
    }

}
