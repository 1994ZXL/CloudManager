package com.example.zxl.cloudmanager.check;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zxl.cloudmanager.MyCheckFragment;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Check;
import com.example.zxl.cloudmanager.model.CheckLab;
import com.example.zxl.cloudmanager.model.DatePickerFragment;

import java.util.ArrayList;
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
    private Button mSearchBtn;

    private static final String[] list={"是", "否"};
    private ArrayAdapter<String> adapter;

    private String lateSearch;
    private String earlySearch;
    private String leaveSearch;
    private String travelSearch;
    private String overTimeSearch;

    private Date beginTime;
    private Date endTime;

    private static final String TAG = "SearchCheckFragment";
    private static final String SEARCH_KEY = "search_key";

    private ArrayList<Check> mChecks = new ArrayList<Check>();

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        this.mFragment = this;
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
        mLateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                lateSearch = list[i];
                adapterView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mEarlySpinner.setAdapter(adapter);
        mEarlySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                earlySearch = list[i];
                adapterView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mTravelSpinner.setAdapter(adapter);
        mTravelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                travelSearch = list[i];
                adapterView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mLeaveSpinner.setAdapter(adapter);
        mLeaveSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                leaveSearch = list[i];
                adapterView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mOverTimeSpinner.setAdapter(adapter);
        mOverTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                overTimeSearch = list[i];
                adapterView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mBeginTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 17);
                fragment.setTargetFragment(SearchCheckFragment.this, 17);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyLeaveApplyFragment");
            }
        });
        mEndTimeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 18);
                fragment.setTargetFragment(SearchCheckFragment.this, 18);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyLeaveApplyFragment");
            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "被点击");
                search();
            }
        });

        return v;
    }

    private void search() {
        mChecks = CheckLab.newInstance(mFragment.getActivity()).get();
        for (int i = 0; i < mChecks.size(); i++) {
            if (mChecks.get(i).getLate().equals(lateSearch) && mChecks.get(i).getLeave().equals(leaveSearch)
                    && mChecks.get(i).getTravel().equals(travelSearch) && mChecks.get(i).getOverTime().equals(overTimeSearch) ) {
                Fragment fragment = new MyCheckFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(SEARCH_KEY, i + 1);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.blankActivity, fragment);
                transaction.commit();
            } else {
                Log.d(TAG, "查询失败模型：" + mChecks.get(i).getLate());
                Log.d(TAG, "查询失败：" + lateSearch);
            }
        }
    }

    private void init(View v){
        mBeginTimeBtn = (Button)v.findViewById(R.id.check_begin_time_button);
        mLateSpinner = (Spinner) v.findViewById(R.id.check_late_time_sprinner);
        mLeaveSpinner = (Spinner) v.findViewById(R.id.check_leave_sprinner);
        mEarlySpinner = (Spinner) v.findViewById(R.id.check_leave_early_sprinner);
        mTravelSpinner = (Spinner) v.findViewById(R.id.check_travel_sprinner);
        mOverTimeSpinner = (Spinner) v.findViewById(R.id.check_overtime_sprinner);
        mEndTimeBtn = (Button) v.findViewById(R.id.check_end_time_button);
        mSearchBtn = (Button) v.findViewById(R.id.search_check_search_button);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "进入回调");
        if (resultCode != Activity.RESULT_OK){
            return;
        }else if (requestCode == 17) {
            beginTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mBeginTimeBtn.setText(android.text.format.DateFormat.format("yyyy.M.dd", beginTime));
        }else if (requestCode == 18) {
            endTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            if (endTime.after(beginTime)) {
                mEndTimeBtn.setText(android.text.format.DateFormat.format("yyyy.M.dd", endTime));
            } else {
                Toast.makeText(getActivity(),
                        R.string.time_erro,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
