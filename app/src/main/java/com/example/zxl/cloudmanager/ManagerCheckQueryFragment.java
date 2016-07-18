package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by ZXL on 2016/7/14.
 */
public class ManagerCheckQueryFragment extends Fragment {

    private Button mBeginTimeBtn;
    private Spinner mLateSpinner;
    private Spinner mEarlySpinner;
    private Spinner mLeaveSpinner;
    private Spinner mTravelSpinner;
    private Spinner mOverTimeSpinner;
    private Button mQueryButton;

    private static final String[] list={"是", "否"};
    private ArrayAdapter<String> adapter;

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment_manager_check_query, container, false);
        init(v);
        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLateSpinner.setAdapter(adapter);
        mEarlySpinner.setAdapter(adapter);
        mTravelSpinner.setAdapter(adapter);
        mLeaveSpinner.setAdapter(adapter);
        mOverTimeSpinner.setAdapter(adapter);

        mBeginTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ManagerCheckListFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.blankActivity, fragment);
                transaction.commit();
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
        mQueryButton = (Button) v.findViewById(R.id.main_fragment_manager_check_query_button);
    }

}
