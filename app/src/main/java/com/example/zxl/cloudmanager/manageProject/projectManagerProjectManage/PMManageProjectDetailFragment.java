package com.example.zxl.cloudmanager.manageProject.projectManagerProjectManage;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Project;

/**
 * Created by ZXL on 2016/7/21.
 */
public class PMManageProjectDetailFragment extends Fragment {


    private TextView mProjectName;
    private TextView mContent;
    private TextView mManager;
    private TextView mCustomerCompany;
    private TextView mBeginTime;
    private TextView mEndTime;
    private TextView mCustomerPhone;
    private Spinner  mState;
    private TextView mCustomerContactor;

    private ArrayAdapter<String> adapter;
    private static final String[] stateList={"取消", "准备","开发","维护","结束"};

    private Fragment mFragment;

    private static Project mProject = new Project();
    public static PMManageProjectDetailFragment newInstance(Object data) {
        mProject = (Project) data;
        PMManageProjectDetailFragment fragment = new PMManageProjectDetailFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.pm_project_edit_or_add, parent, false);

        init(view);


        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, stateList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mState.setAdapter(adapter);
        control();

        return view;
    }

    private void init(View view) {
        mProjectName = (TextView) view.findViewById(R.id.pm_project_name);
        mContent = (TextView) view.findViewById(R.id.pm_content);
        mManager = (TextView) view.findViewById(R.id.pm_manager_name);
        mCustomerCompany = (TextView) view.findViewById(R.id.pm_customer_company);
        mBeginTime = (TextView) view.findViewById(R.id.pm_begin_time);
        mEndTime = (TextView) view.findViewById(R.id.pm_end_time);
        mCustomerPhone = (TextView) view.findViewById(R.id.pm_contactor_phone);
        mState = (Spinner) view.findViewById(R.id.pm_state);
        mCustomerContactor = (TextView) view.findViewById(R.id.pm_customer_contacter);
    }

    private void control(){
        mProjectName.setText(mProject.getProject_name());
        mManager.setText(mProject.getHeader());
        mCustomerContactor.setText(mProject.getContact_name());
        mContent.setText(mProject.getContent());
        mCustomerCompany.setText(mProject.getPart_a());
        mCustomerPhone.setText(mProject.getContact_mob());
        //mState.setText(mCheck.getMaster_name());

        if (mProject.getReady_time() != 0) {
            mBeginTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(mProject.getReady_time()+28800));
        } else {
            mBeginTime.setText("——");
        }

        if (mProject.getFinished_time() != 0){
            mEndTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(mProject.getFinished_time()+28800));
        } else {
            mEndTime.setText("——");
        }

    }
}
