package com.example.zxl.cloudmanager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;


public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment;
        FragmentManager fragmentManager = getFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.main_fragment);

        if (null == fragment) {
            fragment = new MainFragment();
            fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).commit();
        }

    }
}
