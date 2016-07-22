package com.example.zxl.cloudmanager.projectManager.usecase;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.publicSearch.list.ListSearchFragment;

public class ProjectManagerUsecaseActivity extends SingleFragmentActivity {

    /*public PublicSearchActivity(int i){
        switch ()
    }*/
    @Override
    protected Fragment createFragment(){
        return new ListSearchFragment();
    }

}
