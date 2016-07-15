package com.example.zxl.cloudmanager.publicSearch.memberList;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

public class MemberListActivity extends SingleFragmentActivity {

    /*public PublicSearchActivity(int i){
        switch ()
    }*/
    @Override
    protected Fragment createFragment(){
        return new MemberListFragment();
    }

}
