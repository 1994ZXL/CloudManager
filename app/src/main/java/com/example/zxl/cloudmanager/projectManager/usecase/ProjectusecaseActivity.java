package com.example.zxl.cloudmanager.projectManager.usecase;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.publicSearch.bug.BugSearchFragment;
import com.example.zxl.cloudmanager.publicSearch.usecase.UsecaseFragment;

public class ProjectUsecaseActivity extends SingleFragmentActivity {

    /*public PublicSearchActivity(int i){
        switch ()
    }*/
    @Override
    protected Fragment createFragment(){
        return new UsecaseFragment();
    }

}
