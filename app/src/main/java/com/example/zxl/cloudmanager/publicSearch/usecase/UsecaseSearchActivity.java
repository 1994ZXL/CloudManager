package com.example.zxl.cloudmanager.publicSearch.usecase;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

public class UsecaseSearchActivity extends SingleFragmentActivity {

    /*public PublicSearchActivity(int i){
        switch ()
    }*/
    @Override
    protected Fragment createFragment(){
        return new UsecaseFragment();
    }

}
