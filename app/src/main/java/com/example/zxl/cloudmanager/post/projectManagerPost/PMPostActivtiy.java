package com.example.zxl.cloudmanager.post.projectManagerPost;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.post.leader.LeaderPostSearchFragment;

/**
 * Created by ZXL on 2016/9/5.
 */
public class PMPostActivtiy extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new LeaderPostSearchFragment();
    }
}
