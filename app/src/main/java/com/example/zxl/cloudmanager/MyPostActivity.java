package com.example.zxl.cloudmanager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.zxl.cloudmanager.leaderSearch.LeaderPostSearchActivity;
import com.example.zxl.cloudmanager.myPost.MyPostSearchFragment;
import com.example.zxl.cloudmanager.projectManager.memberManager.MemberSearchFragment;
import com.example.zxl.cloudmanager.publicSearch.bug.BugSearchFragment;

/**
 * Created by ZXL on 2016/7/12.
 */
public class MyPostActivity extends Activity {

    Fragment fragment;
    FragmentManager fm = getFragmentManager();
    private ImageButton mSearchBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);



        fragment = fm.findFragmentById(R.id.postActivity);
        if(null == fragment){
            fragment = new MyPostFragment();
            fm.beginTransaction().add(R.id.postActivity, fragment).commit();
        } else {
            //什么都不做
        }

        mSearchBtn = (ImageButton) findViewById(R.id.my_post_activity_searchBtn);
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == fragment) {
                    fragment = new MyPostSearchFragment();
                    fm.beginTransaction().replace(R.id.postActivity, fragment).commit();
                }
            }
        });
    }
}
