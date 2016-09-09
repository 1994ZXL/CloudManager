package com.example.zxl.cloudmanager.operation;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Operation;

/**
 * Created by ZXL on 2016/9/9.
 */
public class MyOperationDetailsFragment extends Fragment {
    private static Operation sOperation;

    private TextView mProjectName;
    private TextView mContactName;
    private TextView mContactMob;
    private TextView mGoonTechnical;
    private TextView mGoonBusiness;
    private TextView mProjectStatus;
    private TextView mGoonTime;
    private TextView mGoonActualStartTime;
    private TextView mGoonActualEndTime;

    private TextView mBack;

    private Fragment mFragment;

    public static MyOperationDetailsFragment newInstance(Object data) {
        sOperation = (Operation) data;
        MyOperationDetailsFragment fragment = new MyOperationDetailsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.my_operation_details, container, false);

        init(v);
        control();

        return v;
    }

    private void init(View v) {
        mProjectName = (TextView) v.findViewById(R.id.operation_details_project_name);
        mContactName = (TextView) v.findViewById(R.id.operation_details_contact_name);
        mContactMob = (TextView) v.findViewById(R.id.operation_details_contact_mob);
        mGoonTechnical = (TextView) v.findViewById(R.id.operation_details_goon_technical);
        mGoonBusiness = (TextView) v.findViewById(R.id.operation_details_goon_business);
        mProjectStatus = (TextView) v.findViewById(R.id.operation_details_porject_status);
        mGoonTime = (TextView) v.findViewById(R.id.operation_details_goon_time);
        mGoonActualStartTime = (TextView) v.findViewById(R.id.operation_details_goon_actual_start_time);
        mGoonActualEndTime = (TextView) v.findViewById(R.id.operation_details_goon_actual_end_time);
        mBack = (TextView) v.findViewById(R.id.operation_details_back);
    }

    private void control() {
        mProjectName.setText(sOperation.getProject_name());
        mContactName.setText(sOperation.getContact_name());
        mContactMob.setText(sOperation.getContact_mob());
        mGoonTechnical.setText(sOperation.getGoon_technical());
        mGoonBusiness.setText(sOperation.getGoon_business());
        mProjectStatus.setText(sOperation.getProject_state());
        if (sOperation.getGoon_time() != 0)
            mGoonTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(sOperation.getGoon_time()));
        else mGoonTime.setText("--");
        if (sOperation.getGoon_actual_start_time() != 0)
            mGoonActualStartTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(sOperation.getGoon_actual_start_time()));
        else mGoonActualStartTime.setText("--");
        if (sOperation.getGoon_actual_end_time() != 0)
            mGoonActualEndTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(sOperation.getGoon_actual_end_time()));
        else mGoonActualEndTime.setText("--");
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });
    }
}
