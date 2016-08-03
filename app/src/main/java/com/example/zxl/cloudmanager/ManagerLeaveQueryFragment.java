package com.example.zxl.cloudmanager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.checkManager.leave.LeaveListFragment;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.Leave;
import com.example.zxl.cloudmanager.model.LeaveQueryLab;
import com.example.zxl.cloudmanager.model.Link;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ZXL on 2016/7/15.
 */
public class ManagerLeaveQueryFragment extends ListFragment{
    private ArrayList<Leave> mLeaves;
    private static final String[] leaveTypeList={"未选择", "没有请假", "事假", "病假", "休假", "婚假", "其他"};
    private static final String[] leaveStateList={"未选择", "待批准", "已批准", "拒绝"};
    private ArrayAdapter<String> spinnerAdapter;

    private TextView textView;
    private Button beginTimeButton, endTimeButton;
    private Spinner spinner;
    private Button button;

    private Fragment mFragment;

    private static final String TAG = "MLeaveQueryFragment";

    private String leaveType;
    private int type;
    private String leaveStatus;
    private int status;
    private Date beginTime;
    private String bgtime;
    private Date endTime;
    private String edtime;


    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setHasOptionsMenu(true);
        mFragment = this;

        mLeaves = LeaveQueryLab.newInstance(getActivity()).getLeaveQuery();
        LeaveAdapter adapter = new LeaveAdapter(mLeaves);
        setListAdapter(adapter);
    }

    private class LeaveAdapter extends ArrayAdapter<Leave> {

        public LeaveAdapter(ArrayList<Leave> Leaves){
            super(getActivity(), 0, Leaves);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if( null == convertView){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.main_fragment_manager_leave_query_item, null);
            }

            Leave leave = getItem(position);

            textView = (TextView) convertView.findViewById(R.id.main_fragment_manager_leave_query);
            beginTimeButton = (Button) convertView.findViewById(R.id.main_fragment_manager_leave_query_begin_time);
            endTimeButton = (Button) convertView.findViewById(R.id.main_fragment_manager_leave_query_end_time);
            spinner = (Spinner) convertView.findViewById(R.id.main_fragment_manager_leave_query_Sprinner);
            button = (Button) convertView.findViewById(R.id.main_fragment_manager_leave_query_button);

            if (0 == position) {
                textView.setText(R.string.apply_time);
                spinner.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
            } else if (1 == position) {
                textView.setText(R.string.leave_type);
                spinnerAdapter = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, leaveTypeList);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        leaveType = leaveTypeList[i];
                        //请假类型:0:没有请假，1:事假,2:病假,3:休假,4:婚假,5:其他
                        if (leaveType == "没有请假") {
                            type = 0;
                        } else if (leaveType == "事假") {
                            type = 1;
                        } else if (leaveType == "病假") {
                            type = 2;
                        } else if (leaveType == "休假") {
                            type = 3;
                        } else if (leaveType == "婚假") {
                            type = 4;
                        } else if (leaveType == "其他") {
                            type = 5;
                        } else if (leaveType == "未选择") {
                            type = -1;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                beginTimeButton.setVisibility(View.GONE);
                endTimeButton.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
            } else if (2 == position) {
                textView.setText(R.string.leave_state);
                spinnerAdapter = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, leaveStateList);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                beginTimeButton.setVisibility(View.GONE);
                endTimeButton.setVisibility(View.GONE);
                spinner.setAdapter(spinnerAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        leaveStatus = leaveStateList[i];
                        //状态:1:待批准,2:已批准,3:拒绝
                        if (leaveStatus == "待批准") {
                            status =  1;
                        } else if (leaveStatus == "已批准") {
                            status =  2;
                        } else if (leaveStatus == "拒绝") {
                            status =  3;
                        } else if (leaveStatus == "未选择") {
                            status = -1;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            beginTimeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 12);
                    fragment.setTargetFragment(ManagerLeaveQueryFragment.this, 12);
                    fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                    fragment.show(getFragmentManager(), "ManagerLeaveQueryFragment");
                }
            });

            endTimeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 13);
                    fragment.setTargetFragment(ManagerLeaveQueryFragment.this, 13);
                    fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                    fragment.show(getFragmentManager(), "ManagerLeaveQueryFragment");
                }
            });


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new LeaveListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(Link.leave_type, type);
                    bundle.putInt(Link.status, status);
                    if (bgtime != null) {
                        bundle.putInt(Link.start_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(bgtime));
                        Log.d(TAG, "GeLinWeiZhi: " + DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(bgtime));
                    } else {
                        bundle.putInt(Link.start_time, -1);
                    }
                    if (edtime != null) {
                        bundle.putInt(Link.end_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(edtime));
                        Log.d(TAG, "GeLinWeiZhi: " + DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(edtime));
                    } else {
                        bundle.putInt(Link.end_time, -1);
                    }
                    Log.d(TAG, "选择条件：" + " type: " + type
                            + " status: " + status
                            + " start_time_s: " + bgtime
                            + " end_time_s: " + edtime);
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.blankActivity, fragment);
                    transaction.commit();
                }
            });

            return convertView;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "进入回调 " + " resultCode:" + requestCode);
        if (resultCode != Activity.RESULT_OK){
            Log.d(TAG, "未进入判断");
            return;
        } else if (requestCode == 12) {
            beginTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateBeginDate();
        } else if (requestCode == 13) {
            endTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateEndDate();
        }
    }

    private void updateBeginDate(){
        bgtime = android.text.format.DateFormat.format("yyyy年MM月dd", beginTime).toString();
        Log.d(TAG, "bgtime: " + bgtime);
        beginTimeButton.setText(bgtime);
        Log.d(TAG, "beginTimeButton: " + beginTimeButton.getText());
    }
    private void updateEndDate(){
        if (endTime.after(beginTime)) {
            edtime = android.text.format.DateFormat.format("yyyy年MM月dd", endTime).toString();
            Log.d(TAG, "edtime: " + edtime);
            endTimeButton.setText(edtime);
            Log.d(TAG, "endTimeButton: " + endTimeButton.getText());
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
