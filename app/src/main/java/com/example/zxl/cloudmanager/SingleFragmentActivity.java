package com.example.zxl.cloudmanager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

/**
 * Created by ZXL on 2016/7/11.
 */
public abstract class SingleFragmentActivity extends Activity {
    protected abstract Fragment createFragment();

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment;
        FragmentManager fm = getFragmentManager();
        fragment = fm.findFragmentById(R.id.fragmentContiner);
        if(null == fragment){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragmentContiner, fragment).commit();
        } else {
            //什么都不做
        }
    }
}

