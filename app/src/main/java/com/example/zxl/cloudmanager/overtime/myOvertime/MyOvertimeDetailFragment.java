package com.example.zxl.cloudmanager.overtime.myOvertime;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.OverTime;


public class MyOvertimeDetailFragment extends Fragment {
    private static OverTime sOverTime = new OverTime();

    private TextView mEmployerNameTV;
    private TextView mProjectName;
    private TextView mBeginTimeTV;
    private TextView mEndTimeTV;
    private TextView mOvertimeReasonTV;
    private TextView mWorkTime;
    private TextView mWorkStatus;

    private TextView mBack;

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    public static MyOvertimeDetailFragment newInstance(Object data) {
        sOverTime = (OverTime) data;
        MyOvertimeDetailFragment fragment = new MyOvertimeDetailFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_overtime_detail, container, false);

        init(v);
        control();

        return v;
    }

    private void init(View v){
        mEmployerNameTV = (TextView) v.findViewById(R.id.manager_overtime_name);
        mProjectName = (TextView) v.findViewById(R.id.manager_overtime_project);
        mBeginTimeTV = (TextView) v.findViewById(R.id.manager_overtime_begin_time_textview);
        mEndTimeTV = (TextView) v.findViewById(R.id.manager_overtime_end_time_textview);
        mOvertimeReasonTV = (TextView) v.findViewById(R.id.manager_overtime_reason_textview);
        mWorkTime = (TextView) v.findViewById(R.id.manager_overtime_this_time_textview);
        mWorkStatus = (TextView) v.findViewById(R.id.manager_overtime_status);

        mBack = (TextView) v.findViewById(R.id.my_overtime_details_back);
    }

    private void control() {
        mEmployerNameTV.setText(sOverTime.getMem_name());
        mProjectName.setText(sOverTime.getWork_pm());
        mBeginTimeTV.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(sOverTime.getStart_time()));
        mEndTimeTV.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(sOverTime.getEnd_time()));
        mOvertimeReasonTV.setText(sOverTime.getWork_resaon());
        mWorkTime.setText(sOverTime.getWork_time());
        mWorkStatus.setText(sOverTime.getStatus());

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });
    }
}
