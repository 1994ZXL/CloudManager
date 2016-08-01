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
import com.example.zxl.cloudmanager.model.LeaveMyLab;
import com.example.zxl.cloudmanager.model.LeaveQueryLab;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ZXL on 2016/7/12.
 */
public class MyLeaveApplyFragment extends Fragment {
    private Leave leave = new Leave();

    private ArrayAdapter<String> adapter;
    private static final String[] list={"病假", "事假", "婚假", "丧假", "产假", "年休假"};
    private String type;

    private Spinner mTypeSpinner;
    private Button mBeginTime;
    private Button mEndTime;
    private EditText mReson;

    private Date beginTime;
    private String bgtime;
    private Date endTime;
    private String edtime;
    private String reson;

    private Button mCommitBtn;

    private static final String TAG = "MyLeaveApplyFragment";
    private static final String ARRAY = "array";

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        mFragment = this;
    }


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
//        leave.setType(type);
        leave.setStart_time(bgtime);
        leave.setEnd_time(edtime);
        leave.setLeave_reason(reson);
        LeaveMyLab.newInstance(mFragment.getActivity()).add(leave);
        Intent intent = new Intent(mFragment.getActivity(), MyLeaveActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "进入回调，未进入判断 " + " resultCode:" + requestCode);
        if (resultCode != Activity.RESULT_OK){
            Log.d(TAG, "进入回调");
            return;
        }else if (requestCode == 12) {
            beginTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            bgtime = android.text.format.DateFormat.format("yyyy年M月dd日", beginTime).toString();
            leave.setStart_time(bgtime);
            updateBeginDate();
        }else if (requestCode == 13) {
            endTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            edtime = android.text.format.DateFormat.format("yyyy年M月dd日", endTime).toString();
            leave.setEnd_time(edtime);
            updateEndDate();
        }
    }

    private void updateBeginDate(){
        mBeginTime.setText(bgtime);
        Log.d("BeginDate", leave.getStart_time());
    }
    private void updateEndDate(){
        if (endTime.after(beginTime)) {
            mEndTime.setText(edtime);
            Log.d("EndDate", leave.getEnd_time());
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
