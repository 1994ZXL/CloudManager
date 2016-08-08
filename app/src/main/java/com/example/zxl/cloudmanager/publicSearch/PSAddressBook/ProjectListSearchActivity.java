package com.example.zxl.cloudmanager.publicSearch.PSAddressBook;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

public class ProjectListSearchActivity extends SingleFragmentActivity {

    /*public PublicSearchActivity(int i){
        switch ()
    }*/
    @Override
    protected Fragment createFragment(){
        return new ListSearchFragment();
    }

}
