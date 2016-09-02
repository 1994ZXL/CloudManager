package com.example.zxl.cloudmanager.leave.myLeave;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Leave;

/**
 * Created by ZXL on 2016/7/11.
 */
public class MyLeaveDetailFragment extends Fragment {
    private TextView name;
    private TextView leaveBeginTime,leaveEndTime,leaveKind,leaveReason,leaveApplyTime;
    private TextView leaveSuggestion;
    private TextView leaveDealTime;
    private TextView leaveState;

    private static final String EXTRA_OBJECT = "leave";

    private static Leave mLeave = new Leave();

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
        View v = layoutInflater.inflate(R.layout.leave_deal, parent, false);


        init(v);
        control();

        return v;
    }

    private void init(View view) {
        name = (TextView)view.findViewById(R.id.employer_leave_name);
        leaveKind = (TextView)view.findViewById(R.id.leave_deal_type);
        leaveBeginTime = (TextView)view.findViewById(R.id.leave_deal_begin_time);
        leaveEndTime = (TextView)view.findViewById(R.id.leave_deal_end_time);
        leaveReason = (TextView)view.findViewById(R.id.leave_deal_ask_reason);
        leaveApplyTime = (TextView)view.findViewById(R.id.leave_deal_apply_time);
        leaveSuggestion = (TextView) view.findViewById(R.id.leave_deal_suggestion);
        leaveState = (TextView) view.findViewById(R.id.leave_deal_state);
        leaveDealTime = (TextView)view.findViewById(R.id.leave_deal_time);

    }

    private void control() {
        name.setText(mLeave.getMem_name());
        leaveKind.setText(mLeave.getLeave_type());
        leaveBeginTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(mLeave.getStart_time()));
        leaveEndTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(mLeave.getEnd_time()));
        leaveSuggestion.setText(mLeave.getHandle_opinion());
        leaveState.setText(mLeave.getStatus());
        leaveReason.setText(mLeave.getLeave_reason());
        leaveDealTime.setText(mLeave.getHandle_time());
    }
}
