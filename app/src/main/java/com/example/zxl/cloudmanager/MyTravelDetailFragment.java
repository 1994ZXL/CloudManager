package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.os.Bundle;
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

    private TextView mem_id;
    private TextView start_time_s;
    private TextView start_time_e;
    private TextView over_time_s;
    private TextView over_time_e;
    private TextView trip_reason;
    private TextView detail_addr;
    private TextView address;
    private TextView status;


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
        mem_id = (TextView) view.findViewById(R.id.travel_details_name);
        start_time_s = (TextView) view.findViewById(R.id.travel_details_begin_time);
        start_time_e = (TextView) view.findViewById(R.id.travel_details_end_time);
        over_time_s = (TextView) view.findViewById(R.id.travel_details_back_beginTime);
        over_time_e = (TextView) view.findViewById(R.id.travel_details_back_endTime);
        trip_reason = (TextView) view.findViewById(R.id.travel_details_content);
        detail_addr = (TextView) view.findViewById(R.id.travel_details_add);
        address = (TextView) view.findViewById(R.id.travel_details_address);
        status = (TextView) view.findViewById(R.id.travel_details_state);
    }

    private void control() {
        mem_id.setText(sTravel.getMem_id());
        start_time_s.setText(sTravel.getStart_time_s());
        start_time_e.setText(sTravel.getStart_time_e());
        over_time_s.setText(sTravel.getOver_time_s());
        over_time_e.setText(sTravel.getOver_time_e());
        trip_reason.setText(sTravel.getTrip_reason());
        detail_addr.setText(sTravel.getDetail_addr());
        address.setText(sTravel.getAddress());
        status.setText(sTravel.getStatus());
    }
}
