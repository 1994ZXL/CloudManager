package com.example.zxl.cloudmanager.leave;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Leave;

/**
 * Created by ZXL on 2016/7/11.
 */
public class MyLeaveDetailFragment extends Fragment {
    private TextView name;
    private TextView leaveBeginTime,leaveEndTime,leaveKind,leaveReason,leaveApplyTime;
    private EditText leaveSuggestion;
    private TextView leaveDealTime;
    private Spinner leaveState;

    private static final String EXTRA_OBJECT = "leave";

    private static Leave mLeave = new Leave();

    private ArrayAdapter<String> stateAdapter;
    private static final String[] stateList = {"批准","不批准"};

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
    }

    public static MyLeaveDetailFragment newInstance(Object data) {
        mLeave = (Leave) data;
        MyLeaveDetailFragment fragment = new MyLeaveDetailFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.cm_leave_deal, parent, false);
        getActivity().getActionBar().setTitle("请假处理");
        stateAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leaveState.setAdapter(stateAdapter);
        init(v);
        control();

        return v;
    }

    private void init(View view) {
        name = (TextView)view.findViewById(R.id.cm_employer_leave_name);
        leaveKind = (TextView)view.findViewById(R.id.cm_leave_deal_type);
        leaveBeginTime = (TextView)view.findViewById(R.id.cm_leave_deal_begin_time);
        leaveEndTime = (TextView)view.findViewById(R.id.cm_leave_deal_end_time);
        leaveReason = (TextView)view.findViewById(R.id.cm_leave_deal_ask_reason);
        leaveApplyTime = (TextView)view.findViewById(R.id.cm_leave_deal_apply_time);
        leaveSuggestion = (EditText) view.findViewById(R.id.cm_leave_deal_suggestion);
        leaveState = (Spinner) view.findViewById(R.id.cm_leave_deal_state);
        leaveDealTime = (TextView)view.findViewById(R.id.cm_leave_deal_time);
    }

    private void control() {
        name.setText(mLeave.getName());
        leaveKind.setText(mLeave.getType());
        leaveBeginTime.setText(mLeave.getBeginTime());
        leaveEndTime.setText(mLeave.getEndTime());
        leaveSuggestion.setText(mLeave.getSuggestion());
        //leaveState.setText(mLeave.getState());
        leaveApplyTime.setText(mLeave.getApplyTime());
        leaveReason.setText(mLeave.getResion());
        leaveDealTime.setText(mLeave.getDisposeTime());
    }
}
