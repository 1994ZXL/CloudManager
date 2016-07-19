package com.example.zxl.cloudmanager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.Leave;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ZXL on 2016/7/12.
 */
public class MyLeaveApplyFragment extends Fragment {
    private Leave leave = new Leave();
    private ArrayList<Leave> mLeaves = new ArrayList<Leave>();

    private ArrayAdapter<String> adapter;
    private static final String[] list={"病假", "事假", "婚假", "丧假", "产假", "年休假"};
    private String type;

    private Spinner mTypeSpinner;
    private Button mBeginTime;
    private Button mEndTime;
    private EditText mReson;

    private Date beginTime;
    private Date endTime;
    private String reson;

    private Button mCommitBtn;

    private static final String TAG = "MyLeaveApplyFragment";


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.main_fragment_my_leave_apply, parent, false);

        initVariable(view);

        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeSpinner.setAdapter(adapter);
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = list[i];
                adapterView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mBeginTime.setEnabled(true);//设置按钮可点
        mBeginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 12);
                fragment.setTargetFragment(MyLeaveApplyFragment.this, 12);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyLeaveApplyFragment");
            }
        });


        mEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 13);
                fragment.setTargetFragment(MyLeaveApplyFragment.this, 13);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyLeaveApplyFragment");
            }
        });

        mReson.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                reson = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mCommitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit();
            }
        });

        return view;
    }

    public void initVariable(View view) {
        mTypeSpinner = (Spinner)view.findViewById(R.id.main_fragment_my_leave_typeSprinner);
        mBeginTime = (Button)view.findViewById(R.id.main_fragment_my_leave_begin_time);
        mEndTime = (Button)view.findViewById(R.id.main_fragment_my_leave_end_time);
        mCommitBtn = (Button)view.findViewById(R.id.main_fragment_my_leave_apply_commitButton);
        mReson = (EditText)view.findViewById(R.id.main_fragment_my_leave_resonEditText);
    }

    public void commit() {
        leave.setType(type);
        leave.setBeginTime(beginTime);
        leave.setEndTime(endTime);
        leave.setResion(reson);
        mLeaves.add(leave);
        MyLeaveQueryFragment fragment = new MyLeaveQueryFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.blankActivity, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "进入回调，未进入判断 " + " resultCode:" + requestCode);
        if (resultCode != Activity.RESULT_OK){
            Log.d(TAG, "进入回调");
            return;
        }else if (requestCode == 12) {
            beginTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            leave.setBeginTime(beginTime);
            Log.d(TAG, leave.getBeginTime().toString());
            updateBeginDate();
        }else if (requestCode == 13) {
            endTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            leave.setEndTime(endTime);
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
