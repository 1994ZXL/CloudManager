package com.example.zxl.cloudmanager;

import android.app.Fragment;

import com.example.zxl.cloudmanager.Memo.MemoFragment;


/**
 * Created by ZXL on 2016/7/11.
 */
public class MemoActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new MemoFragment();
    }
}
