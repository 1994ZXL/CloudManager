package com.example.zxl.cloudmanager.check.checkManager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Check;
import com.example.zxl.cloudmanager.model.DatePickerFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ZXL on 2016/7/14.
 */
public class ManagerCheckQueryFragment extends Fragment {

    private Button mBeginTimeBtn;
    private Button mEndTimeBtn;
    private Spinner mLateSpinner;
    private Spinner mEarlySpinner;
    private Spinner mLeaveSpinner;
    private Spinner mTravelSpinner;
    private Spinner mOverTimeSpinner;
    private Button mQueryButton;

    private static final String[] list={"否", "是"};
    private ArrayAdapter<String> adapter;

    private Date checkTime;
    private String ctime;
    private Date checkOffTime;
    private String cotime;
    private String late;
    private String early;
    private String leave;
    private String travel;
    private String overtime;

    private ArrayList<Check> mChecks = new ArrayList<Check>();
    private int index;
    private ArrayList<Integer> sum = new ArrayList<Integer>();
    private static final String SEARCH_KEY = "search_key";

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment_manager_check_query, container, false);

        init(v);
        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLateSpinner.setAdapter(adapter);
        mLateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                late = list[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mEarlySpinner.setAdapter(adapter);
        mEarlySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                early = list[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mTravelSpinner.setAdapter(adapter);
        mTravelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                travel = list[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mLeaveSpinner.setAdapter(adapter);
        mLeaveSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                leave = list[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mOverTimeSpinner.setAdapter(adapter);
        mOverTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                overtime = list[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mBeginTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 12);
                fragment.setTargetFragment(ManagerCheckQueryFragment.this, 12);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "ManagerCheckQueryFragment");
            }
        });
        mEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 13);
                fragment.setTargetFragment(ManagerCheckQueryFragment.this, 13);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "ManagerCheckQueryFragment");
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
        mEndTimeBtn = (Button) v.findViewById(R.id.check_end_time_button);
        mLateSpinner = (Spinner) v.findViewById(R.id.check_late_time_sprinner);
        mLeaveSpinner = (Spinner) v.findViewById(R.id.check_leave_sprinner);
        mEarlySpinner = (Spinner) v.findViewById(R.id.check_leave_early_sprinner);
        mTravelSpinner = (Spinner) v.findViewById(R.id.check_travel_sprinner);
        mOverTimeSpinner = (Spinner) v.findViewById(R.id.check_overtime_sprinner);
        mQueryButton = (Button) v.findViewById(R.id.main_fragment_manager_check_query_button);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        } else if (requestCode == 12) {
            checkTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateCheckDate();
        } else if (requestCode == 13) {
            checkOffTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateCheckOffDate();
        }
    }

    private void updateCheckDate(){
        ctime = android.text.format.DateFormat.format("yyyy.M.dd", checkTime).toString();
        mBeginTimeBtn.setText(ctime);
    }
    private void updateCheckOffDate(){
        cotime = android.text.format.DateFormat.format("yyyy.M.dd", checkOffTime).toString();
        mEndTimeBtn.setText(cotime);
    }

    public static Date ConverToDate(String strDate) throws Exception
    {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        return df.parse(strDate);
    }
}
