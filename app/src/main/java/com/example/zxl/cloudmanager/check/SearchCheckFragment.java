package com.example.zxl.cloudmanager.check;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Check;
import com.example.zxl.cloudmanager.model.DatePickerFragment;

import java.util.Date;

public class SearchCheckFragment extends Fragment {
    private Check check;

    private Button mBeginTimeBtn;
    private Button mEndTimeBtn;
    private Spinner mLateSpinner;
    private Spinner mEarlySpinner;
    private Spinner mLeaveSpinner;
    private Spinner mTravelSpinner;
    private Spinner mOverTimeSpinner;

    private static final String[] list={"是", "否"};
    private ArrayAdapter<String> adapter;

    private static final String TAG = "SearchCheckFragment";

    public SearchCheckFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_check, container, false);
        init(v);
        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLateSpinner.setAdapter(adapter);
        mEarlySpinner.setAdapter(adapter);
        mTravelSpinner.setAdapter(adapter);
        mLeaveSpinner.setAdapter(adapter);
        mOverTimeSpinner.setAdapter(adapter);

        mBeginTimeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 17);
                fragment.setTargetFragment(SearchCheckFragment.this, 17);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyLeaveApplyFragment");
            }
        });
        mEndTimeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 17);
                fragment.setTargetFragment(SearchCheckFragment.this, 17);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyLeaveApplyFragment");
            }
        });
        return v;
    }

    private void init(View v){
        mBeginTimeBtn = (Button)v.findViewById(R.id.check_begin_time_button);
        mLateSpinner = (Spinner) v.findViewById(R.id.check_late_time_sprinner);
        mLeaveSpinner = (Spinner) v.findViewById(R.id.check_leave_sprinner);
        mEarlySpinner = (Spinner) v.findViewById(R.id.check_leave_early_sprinner);
        mTravelSpinner = (Spinner) v.findViewById(R.id.check_travel_sprinner);
        mOverTimeSpinner = (Spinner) v.findViewById(R.id.check_overtime_sprinner);
        mEndTimeBtn = (Button) v.findViewById(R.id.check_end_time_button);
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "进入回调，未进入判断 " + " resultCode:" + requestCode);
        if (resultCode != Activity.RESULT_OK){
            Log.d(TAG, "进入回调");
            return;
        }else if (requestCode == 12) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            leave.setBeginTime(date);
            Log.d(TAG, leave.getBeginTime().toString());
            updateBeginDate();
        }else if (requestCode == 13) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            leave.setEndTime(date);
            updateEndDate();
        }
    }

    private void updateBeginDate(){
        mBeginTimeBtn.setText(DateFormat.format("yyyy.M.dd", check.getBeginTime()));
        Log.d("BeginDate", check.getBeginTime().toString());
    }
    private void updateEndDate(){
        if (leave.getEndTime().after(leave.getBeginTime())) {
            mEndTime.setText(android.text.format.DateFormat.format("yyyy年M月dd日", leave.getEndTime()));
            Log.d("EndDate", leave.getEndTime().toString());
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }*/
}
