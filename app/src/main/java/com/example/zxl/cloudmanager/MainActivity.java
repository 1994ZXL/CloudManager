package com.example.zxl.cloudmanager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zxl.cloudmanager.tabBar.AboutAppFragment;
import com.example.zxl.cloudmanager.tabBar.CompanyContactionFragment;
import com.example.zxl.cloudmanager.tabBar.CompanyMemberListFragment;
import com.example.zxl.cloudmanager.tabBar.CustomerItemFragment;


public class MainActivity extends Activity implements View.OnClickListener{

    private TextView bottomHomeBar;
    private TextView bottomAddressListBar;
    private TextView bottomCustomerBar;
    private TextView bottomAboutBar;
    private LinearLayout bottomContent;
    private FragmentManager fragmentManager;
    private MainFragment mainFragment;
    private CompanyContactionFragment companyMemberListFragment;
    private CustomerItemFragment customerListFragment;
    private AboutAppFragment aboutAppFragment;

    private Fragment fragment;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();

        //fragment = fragmentManager.findFragmentById(R.id.fragmentContiner);
        /*
        fragment = fragmentManager.findFragmentById(R.id.fragmentContiner);
        if (null == fragment) {
            fragment = new MainFragment();
            fragmentManager.beginTransaction().replace(R.id.fragmentContiner, fragment).commit();
        }*/
        bindViews();
        bottomHomeBar.performClick(); //模拟一次点击,使进入时就选择第一项
    }
    private void bindViews(){
        //初始化所有组件
        bottomHomeBar = (TextView)findViewById(R.id.bottom_menu_home_bar);
        bottomAddressListBar = (TextView)findViewById(R.id.bottom_menu_addresslist_bar);
        bottomCustomerBar = (TextView)findViewById(R.id.bottom_menu_customer_bar);
        bottomAboutBar = (TextView)findViewById(R.id.bottom_menu_about_bar);
        bottomContent = (LinearLayout)findViewById(R.id.bottom_menu_content);

        bottomHomeBar.setOnClickListener(this);
        bottomAddressListBar.setOnClickListener(this);
        bottomCustomerBar.setOnClickListener(this);
        bottomAboutBar.setOnClickListener(this);

    }

    private void setSelected(){
        //重置所有文本地选中状态
        bottomHomeBar.setSelected(false);
        bottomAddressListBar.setSelected(false);
        bottomCustomerBar.setSelected(false);
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
                if(fragment == null){
                    mainFragment = new MainFragment();
                    //fragmentManager.beginTransaction().replace(R.id.fragmentContiner, mainFragment).commit();
                    ft.add(R.id.fragmentContiner,mainFragment);
                }else {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContiner, mainFragment).commit();
                }
                break;
            case R.id.bottom_menu_addresslist_bar:
                setSelected();
                bottomAddressListBar.setSelected(true);
                if(fragment == null){
                    companyMemberListFragment = new CompanyContactionFragment();
                    ft.add(R.id.fragmentContiner,companyMemberListFragment);
                }else {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContiner, companyMemberListFragment).commit();
                }
                break;
            case R.id.bottom_menu_customer_bar:
                setSelected();
                bottomCustomerBar.setSelected(true);
                if(fragment == null){
                    customerListFragment = new CustomerItemFragment();
                    ft.add(R.id.fragmentContiner,customerListFragment);
                }else {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContiner, customerListFragment).commit();
                }
                break;
            case R.id.bottom_menu_about_bar:
                setSelected();
                bottomAboutBar.setSelected(true);
                if(fragment == null){
                    aboutAppFragment = new AboutAppFragment();
                    ft.add(R.id.fragmentContiner,aboutAppFragment);
                }else {
                    fragmentManager.beginTransaction().replace(R.id.fragmentContiner, aboutAppFragment).commit();
                }
                break;
        }
        ft.commit();
    }
}
