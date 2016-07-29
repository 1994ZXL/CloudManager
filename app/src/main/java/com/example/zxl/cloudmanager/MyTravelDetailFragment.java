package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxl.cloudmanager.model.Travel;

/**
 * Created by ZXL on 2016/7/25.
 */
public class MyTravelDetailFragment extends Fragment {
    private static Travel sTravel = new Travel();

    private TextView mName;
    private TextView mBeginTime;
    private TextView mEndTime;
    private TextView mBackBeginTime;
    private TextView mBackEndTime;
    private TextView mContent;
    private TextView mAdd;
    private TextView mAddress;
    private TextView mState;


    public static MyTravelDetailFragment newInstance(Travel travel){
        sTravel = travel;
        MyTravelDetailFragment fragment = new MyTravelDetailFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.travel_details, container, false);

        getActivity().getActionBar().setTitle("出差详情");
        init(view);
        control();

        return view;
    }

    private void init(View view) {
        mName = (TextView) view.findViewById(R.id.travel_details_name);
        mBeginTime = (TextView) view.findViewById(R.id.travel_details_begin_time);
        mEndTime = (TextView) view.findViewById(R.id.travel_details_end_time);
        mBackBeginTime = (TextView) view.findViewById(R.id.travel_details_back_beginTime);
        mBackEndTime = (TextView) view.findViewById(R.id.travel_details_back_endTime);
        mContent = (TextView) view.findViewById(R.id.travel_details_content);
        mAdd = (TextView) view.findViewById(R.id.travel_details_add);
        mAddress = (TextView) view.findViewById(R.id.travel_details_address);
        mState = (TextView) view.findViewById(R.id.travel_details_state);
    }

    private void control() {
        mName.setText(sTravel.getName());
        mBeginTime.setText(sTravel.getBeginTime());
        mEndTime.setText(sTravel.getEndTime());
        mBackBeginTime.setText(sTravel.getBackBeginTime());
        mBackEndTime.setText(sTravel.getBackEndTime());
        mContent.setText(sTravel.getTravelContent());
        mAdd.setText(sTravel.getTravelAdd());
        mAddress.setText(sTravel.getTravelAddress());
        mState.setText(sTravel.getTravelState());
    }
}
