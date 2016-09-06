package com.example.zxl.cloudmanager.post.myPost;

import android.app.Fragment;

import com.example.zxl.cloudmanager.SingleFragmentActivity;

/**
 * Created by ZXL on 2016/7/12.
 */
public class MyPostActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new MyPostFragment();
    }
}
