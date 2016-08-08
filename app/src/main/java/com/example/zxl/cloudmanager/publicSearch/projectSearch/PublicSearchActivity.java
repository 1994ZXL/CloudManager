package com.example.zxl.cloudmanager.publicSearch.projectSearch;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

public class PublicSearchActivity extends SingleFragmentActivity {

    /*public PublicSearchActivity(int i){
        switch ()
    }*/
    @Override
    protected Fragment createFragment(){
        return new ProjectSearchFragment();
    }

}
