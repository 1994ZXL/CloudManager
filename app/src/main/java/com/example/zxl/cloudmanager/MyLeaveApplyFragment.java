package com.example.zxl.cloudmanager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.Leave;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ZXL on 2016/7/12.
 */
public class MyLeaveApplyFragment extends Fragment {
    private Leave leave = new Leave();

    private ArrayAdapter<String> adapter;
    private static final String[] list={"病假", "事假", "婚假", "丧假", "产假", "年休假"};

    private Spinner mTypeSpinner;
    private Button mBeginTime;
    private Button mEndTime;


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.main_fragment_my_leave_apply, parent, false);

        initVariable(view);

        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeSpinner.setAdapter(adapter);

        mBeginTime.setEnabled(true);//设置按钮可点
        mBeginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date());
                fragment.setTargetFragment(MyLeaveApplyFragment.this, 12);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyLeaveApplyFragment");
            }
        });


        mEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date());
                fragment.setTargetFragment(MyLeaveApplyFragment.this, 13);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyLeaveApplyFragment");
            }
        });


        return view;
    }

    public void initVariable(View view) {
        mTypeSpinner = (Spinner)view.findViewById(R.id.main_fragment_my_leave_typeSprinner);
        mBeginTime = (Button)view.findViewById(R.id.main_fragment_my_leave_begin_time);
        mEndTime = (Button)view.findViewById(R.id.main_fragment_my_leave_end_time);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == 12) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            leave.setBeginTime(date);
            updateBeginDate();
        }
        if (requestCode == 13) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            leave.setEndTime(date);
            updateEndDate();
        }
    }

    private void updateBeginDate(){
        mBeginTime.setText(android.text.format.DateFormat.format("yyyy年M月dd日", leave.getBeginTime()));
        Log.d("BeginDate", leave.getBeginTime().toString());
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
    }
}
