package com.example.zxl.cloudmanager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment fragment = null;
        FragmentManager fragmentManager = getFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.fragmentContiner);

        if (null == fragment){
            fragment = new LoginFragment();
            fragmentManager.beginTransaction().replace(R.id.fragmentContiner, fragment).commit();
        }

    }


}
