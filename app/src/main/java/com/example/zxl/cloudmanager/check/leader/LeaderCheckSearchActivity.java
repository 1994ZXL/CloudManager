package com.example.zxl.cloudmanager.check.leader;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.check.myCheck.SearchCheckFragment;

public class LeaderCheckSearchActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new SearchCheckFragment();
    }

}
