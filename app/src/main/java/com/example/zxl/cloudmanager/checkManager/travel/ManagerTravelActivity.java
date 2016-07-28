package com.example.zxl.cloudmanager.checkManager.travel;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.zxl.cloudmanager.MyPostFragment;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.Refresh.PullToRefreshView;
import com.example.zxl.cloudmanager.SingleFragmentActivity;
import com.example.zxl.cloudmanager.check.SearchCheckFragment;
import com.example.zxl.cloudmanager.travel.TravelSearchFragment;

public class ManagerTravelActivity extends Activity {

    Fragment fragment;
    FragmentManager fm = getFragmentManager();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cm_travel_activity);

        fragment = fm.findFragmentById(R.id.cmTravelActivity);
        if (null == fragment) {
            fragment = new ManagerTravelSearchFragment();
            fm.beginTransaction().add(R.id.cmTravelActivity, fragment).commit();
        } else {
            //什么都不做
        }
    }
}
