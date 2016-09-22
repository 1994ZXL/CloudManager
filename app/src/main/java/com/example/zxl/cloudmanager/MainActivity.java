package com.example.zxl.cloudmanager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zxl.cloudmanager.tabBar.AboutAppFragment;
import com.example.zxl.cloudmanager.tabBar.CompanyContactionFragment;
import com.example.zxl.cloudmanager.tabBar.CompanyMemberListFragment;
import com.example.zxl.cloudmanager.tabBar.CustomerContactionFragment;
import com.example.zxl.cloudmanager.tabBar.CustomerItemFragment;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private LinearLayout bottomHomeBar;
    private LinearLayout bottomAboutBar;
    private LinearLayout bottomContent;
    private FragmentManager fragmentManager;
    private MainFragment mainFragment;
    private CompanyContactionFragment companyMemberListFragment;
    private CustomerContactionFragment customerListFragment;
    private AboutAppFragment aboutAppFragment;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();

        bindViews();
        bottomHomeBar.performClick(); //模拟一次点击,使进入时就选择第一项
    }
    private void bindViews(){
        //初始化所有组件
        bottomHomeBar = (LinearLayout)findViewById(R.id.bottom_menu_home_bar);
        bottomAboutBar = (LinearLayout)findViewById(R.id.bottom_menu_about_bar);
        bottomContent = (LinearLayout)findViewById(R.id.bottom_menu_content);

        bottomHomeBar.setOnClickListener(this);
        bottomAboutBar.setOnClickListener(this);

    }

    private void setSelected(){
        //重置所有文本地选中状态
        bottomHomeBar.setSelected(false);
        bottomAboutBar.setSelected(false);
    }

    private void hideAllFragment(FragmentTransaction ft){
        if(mainFragment != null)
            ft.hide(mainFragment);
        if(companyMemberListFragment != null)
            ft.hide(companyMemberListFragment);
        if(customerListFragment != null)
            ft.hide(customerListFragment);
        if(aboutAppFragment != null)
            ft.hide(aboutAppFragment);
    }
    @Override
    public void onClick(View v){
        FragmentTransaction ft = fragmentManager.beginTransaction();
        hideAllFragment(ft);
        switch (v.getId()){
            case R.id.bottom_menu_home_bar:
                setSelected();
                bottomHomeBar.setSelected(true);
                if(mainFragment == null){
                    mainFragment = new MainFragment();
                    //fragmentManager.beginTransaction().replace(R.id.fragmentContiner, mainFragment).commit();
                    ft.add(R.id.fragmentContiner,mainFragment);
                    //ft.addToBackStack(null);
                }else {
                    Log.e(TAG,"mainFragment : " + "显示已有的mainFragment");
                    //fragmentManager.beginTransaction().replace(R.id.fragmentContiner, mainFragment).commit();
                    ft.show(mainFragment);
                }
                break;
            case R.id.bottom_menu_about_bar:
                setSelected();
                bottomAboutBar.setSelected(true);
                if(aboutAppFragment == null){
                    aboutAppFragment = new AboutAppFragment();
                    ft.add(R.id.fragmentContiner,aboutAppFragment);
                }else {
                    //fragmentManager.beginTransaction().replace(R.id.fragmentContiner, aboutAppFragment).commit();
                    ft.show(aboutAppFragment);

                }
                break;
        }
        ft.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
