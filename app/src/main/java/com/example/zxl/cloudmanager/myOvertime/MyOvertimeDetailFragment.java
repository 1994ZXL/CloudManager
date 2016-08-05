package com.example.zxl.cloudmanager.myOvertime;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.OverTime;

import org.w3c.dom.Text;


public class MyOvertimeDetailFragment extends Fragment {
    private static OverTime sOverTime = new OverTime();

    private TextView mEmployerNameTV;
    private TextView mProjectNameTV;
    private TextView mBeginTimeTV;
    private TextView mEndTimeTV;
    private TextView mOvertimeReasonTV;
    private TextView mTotalTimeTV;
    private TextView mAllTimeTV;

    public MyOvertimeDetailFragment() {
        // Required empty public constructor
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
        mEmployerNameTV = (TextView) v.findViewById(R.id.manager_employer_name_textview);
        mProjectNameTV = (TextView) v.findViewById(R.id.manager_overtime_project_textview);
        mBeginTimeTV = (TextView) v.findViewById(R.id.manager_overtime_begin_time_textview);
        mEndTimeTV = (TextView) v.findViewById(R.id.manager_overtime_end_time_textview);
        mOvertimeReasonTV = (TextView) v.findViewById(R.id.manager_overtime_reason_textview);
        mTotalTimeTV = (TextView) v.findViewById(R.id.manager_overtime_total_time_textview);
        mAllTimeTV = (TextView) v.findViewById(R.id.manager_overtime_all_time_textview);
    }

    private void control() {
        mEmployerNameTV.setText(sOverTime.getMem_id());
        mProjectNameTV.setText(sOverTime.getWork_name());
        mBeginTimeTV.setText(sOverTime.getStart_time());
        mEndTimeTV.setText(sOverTime.getEnd_time());
        mOvertimeReasonTV.setText(sOverTime.getWork_reason());
        mTotalTimeTV.setText(sOverTime.getThisTime());
        mAllTimeTV.setText(sOverTime.getTotalTime());
    }
}
