package com.example.zxl.cloudmanager.projectManager.PMAdressBook;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;

/**
 * Created by ZXL on 2016/7/21.
 */
public class ProjectManagerDetailFragment extends Fragment {


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
    private static final String[] stateList={"启动", "进行中","维护期","已结束"};

    private Fragment mFragment;



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

}
