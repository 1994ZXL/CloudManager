package com.example.zxl.cloudmanager.contact.publicSearchContact;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.contact.projectManagerContact.ContactSearchFragment;

public class ProjectListSearchActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment(){
        return new ContactSearchFragment();
    }

}
