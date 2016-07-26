package com.example.zxl.cloudmanager.leave;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Check;
import com.example.zxl.cloudmanager.model.Leave;

/**
 * Created by ZXL on 2016/7/11.
 */
public class MyLeaveDetailFragment extends Fragment{
    private TextView name;
    private TextView leaveBeginTime,leaveEndTime,leaveKind,leaveReason,leaveSuggestion,leaveApplyTime;
    private TextView leaveState,leaveDealTime;

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
        View v = layoutInflater.inflate(R.layout.my_leave_details, parent, false);

        init(v);
        control();

        return v;
    }

    private void init(View view) {
        name = (TextView)view.findViewById(R.id.my_leave_details_name);
        leaveKind = (TextView)view.findViewById(R.id.my_leave_details_type);
        leaveBeginTime = (TextView)view.findViewById(R.id.leave_begin_time);
        leaveEndTime = (TextView)view.findViewById(R.id.leave_end_time);
        leaveReason = (TextView)view.findViewById(R.id.leave_details_reason);
        leaveApplyTime = (TextView)view.findViewById(R.id.leave_details_apply_time);
        leaveSuggestion = (TextView)view.findViewById(R.id.leave_details_suggestion);
        leaveState = (TextView)view.findViewById(R.id.leave_details_state);
        leaveDealTime = (TextView)view.findViewById(R.id.leave_details_deal_time);
    }

    private void control() {
        name.setText(mLeave.getName());
        leaveKind.setText(mLeave.getType());
        leaveBeginTime.setText(mLeave.getBeginTime().toString());
        leaveEndTime.setText(mLeave.getEndTime().toString());
        leaveSuggestion.setText(mLeave.getSuggestion());
        leaveState.setText(mLeave.getState());
        leaveApplyTime.setText(mLeave.getApplyTime().toString());
        leaveReason.setText(mLeave.getResion());
        leaveDealTime.setText(mLeave.getDisposeTime().toString());
    }
}
