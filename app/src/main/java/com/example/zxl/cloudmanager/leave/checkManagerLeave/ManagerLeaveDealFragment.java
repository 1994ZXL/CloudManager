package com.example.zxl.cloudmanager.leave.checkManagerLeave;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Leave;

/**
 * Created by ZXL on 2016/7/11.
 */
public class ManagerLeaveDealFragment extends Fragment {
    private TextView name;
    private TextView leaveBeginTime,leaveEndTime,leaveKind,leaveReason,leaveApplyTime;
    private TextView leaveSuggestion;
    private TextView leaveDealTime;
    private Spinner leaveState;
    private Button mSubmitBtn;

    private static final String EXTRA_OBJECT = "leave";
    private static final String[] leaveStateList={"批准", "不批准"};
    private ArrayAdapter<String> spinnerAdapter;

    private static Leave mLeave = new Leave();

    private Fragment mFragment;
    private static final String TAG = "MLeaveDeallFragment";

    @Override
    public void onCreate(Bundle saveInstanceState) {
        Log.e(EXTRA_OBJECT,"CM_leave : " + "执行");
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
    }

    public static ManagerLeaveDealFragment newInstance(Object data) {
        mLeave = (Leave) data;
        ManagerLeaveDealFragment fragment = new ManagerLeaveDealFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.cm_leave_deal, parent, false);
        Log.e(EXTRA_OBJECT,"CM_leave : " + "初始化View");
        init(v);
        spinnerAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, leaveStateList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leaveState.setAdapter(spinnerAdapter);
        control();

        return v;
    }

    private void init(View view) {
        Log.e(EXTRA_OBJECT,"CM_leave : " + "初始化控件");
        name = (TextView)view.findViewById(R.id.cm_employer_leave_name);
        leaveKind = (TextView)view.findViewById(R.id.cm_leave_deal_type);
        leaveBeginTime = (TextView)view.findViewById(R.id.cm_leave_deal_begin_time);
        leaveEndTime = (TextView)view.findViewById(R.id.cm_leave_deal_end_time);
        leaveReason = (TextView)view.findViewById(R.id.cm_leave_deal_ask_reason);
        leaveApplyTime = (TextView)view.findViewById(R.id.cm_leave_deal_apply_time);
        leaveSuggestion = (TextView) view.findViewById(R.id.cm_leave_deal_suggestion);
        leaveState = (Spinner) view.findViewById(R.id.cm_leave_deal_state);
        leaveDealTime = (TextView)view.findViewById(R.id.cm_leave_deal_time);

        mSubmitBtn = (Button) view.findViewById(R.id.cm_leave_submit_button);
    }

    private void control() {
        name.setText(mLeave.getMem_name());
        leaveKind.setText(mLeave.getLeave_type());
        leaveBeginTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(mLeave.getStart_time()));
        Log.d(TAG, "beginTime: " + leaveBeginTime.getText());
        leaveEndTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(mLeave.getEnd_time()));
        leaveSuggestion.setText(mLeave.getHandle_opinion());
        leaveReason.setText(mLeave.getLeave_reason());
        if ("null" != mLeave.getHandle_time()) {
            Log.d(TAG, "Handle_time1: " + mLeave.getHandle_time());
            leaveDealTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(Integer.parseInt(mLeave.getHandle_time())));
        } else {
            Log.d(TAG, "Handle_time2: " + mLeave.getHandle_time());
            leaveDealTime.setText("——");
        }
    }
}