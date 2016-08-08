package com.example.zxl.cloudmanager.bug.projectManagerBug;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Bug;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;

/**
 * Created by ZXL on 2016/7/22.
 */
public class PMBugDetailFragment extends Fragment {
    private static Bug sBug = new Bug();

    private TextView mFunctionModul;
    private TextView mBugNumber;
    private TextView mBugVersion;
    private TextView mBugState;
    private TextView mBugContent;
    private TextView mUseCaseNumber;
    private TextView mOperationMode;
    private TextView mEntranceMode;
    private TextView mFoundTime;
    private TextView mFoundMan;
    private TextView mEditTime;
    private TextView mEditMan;
    private TextView mUnderProgram;


    public static PMBugDetailFragment newInstance(Object data) {
        sBug = (Bug) data;
        PMBugDetailFragment myBugDetailFragment = new PMBugDetailFragment();
        return myBugDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.bug_details, parent, false);
        getActivity().getActionBar().setTitle("bug详情");
        init(view);
        contorl();
        return view;
    }

    private void init(View view) {
        mFunctionModul = (TextView) view.findViewById(R.id.bug_details_functionModule);
        mBugNumber = (TextView) view.findViewById(R.id.bug_details_number);
        mBugVersion = (TextView) view.findViewById(R.id.bug_details_level);
        mBugState = (TextView) view.findViewById(R.id.bug_details_state);
        mBugContent = (TextView) view.findViewById(R.id.bug_details_content);
        mUseCaseNumber = (TextView) view.findViewById(R.id.bug_details_usecase_number);
        mOperationMode = (TextView) view.findViewById(R.id.bug_details_operation_mode);
        mEntranceMode = (TextView) view.findViewById(R.id.bug_details_entrance_mode);
        mFoundTime = (TextView) view.findViewById(R.id.bug_details_found_time);
        mFoundMan = (TextView) view.findViewById(R.id.bug_details_found_man);
        mEditTime = (TextView) view.findViewById(R.id.bug_details_edit_time);
        mEditMan = (TextView) view.findViewById(R.id.bug_details_edit_man);
        mUnderProgram = (TextView) view.findViewById(R.id.bug_details_under_program);
    }

    private void contorl() {
        mFunctionModul.setText(sBug.getProject_name());
        mBugNumber.setText(sBug.getBugNumber());
        mBugVersion.setText(sBug.getLevel());
        mBugState.setText(sBug.getStatus());
        mBugContent.setText(sBug.getProject_name());
        mUseCaseNumber.setText(sBug.getMem_name());
        mOperationMode.setText(sBug.getCase_mode());
        //mEntranceMode.setText(sBug.getSubmit_time());
        mFoundTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(sBug.getSubmit_time()));
        mEditTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(sBug.getModify_time()));
        mFoundMan.setText(sBug.getSubmitter());
        mEditMan.setText(sBug.getEditMan());
        mUnderProgram.setText(sBug.getUnderProgram());
    }
}