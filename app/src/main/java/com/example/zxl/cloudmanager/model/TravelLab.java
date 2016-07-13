package com.example.zxl.cloudmanager.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/13.
 */
public class TravelLab {
    private Travel mTravel = new Travel();
    private ArrayList<Travel> mTravels = new ArrayList<Travel>();
    private static TravelLab sTravelLab;

    private TravelLab(Context context) {
        for (int i=0; i<3; i++) {
            mTravel.setName("张三");
            mTravel.setTravleTime("2016.7.7-2013.7.8");
            mTravel.setTravleState("正常");
            mTravels.add(mTravel);
        }
    }

    public static TravelLab newInstance(Context context) {
        if (null == sTravelLab) {
            sTravelLab = new TravelLab(context.getApplicationContext());
        }
        return sTravelLab;
    }

    public ArrayList<Travel> getTravels() {
        return mTravels;
    }
}
