package com.example.zxl.cloudmanager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class LoginActivity extends Activity {
    private LinearLayout linearLayout;
    private View v;
    private LinearLayout linearLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout)findViewById(R.id.bottom_menu_bar);
        linearLayout.setVisibility(View.INVISIBLE);
        v = (View)findViewById(R.id.div_tab_bar);
        v.setVisibility(View.INVISIBLE);
        linearLayout2 = (LinearLayout)findViewById(R.id.bottom_menu_content);
        linearLayout2.setVisibility(View.INVISIBLE);

        Fragment fragment = null;
        FragmentManager fragmentManager = getFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.fragmentContiner);

        if (null == fragment){
            fragment = new LoginFragment();
            fragmentManager.beginTransaction().replace(R.id.fragmentContiner, fragment).commit();
        }

    }


}
