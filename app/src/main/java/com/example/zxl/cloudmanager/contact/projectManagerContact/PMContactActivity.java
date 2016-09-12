package com.example.zxl.cloudmanager.contact.projectManagerContact;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

public class PMContactActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new ContactSearchFragment();
    }
}
