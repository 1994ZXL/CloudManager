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

    private TextView mProjectName;
    private TextView mBugVersion;
    private TextView mBugState;
    private TextView mBugContent;
    private TextView mFoundTime;
    private TextView mFoundMan;
    private TextView mEditTime;
    private TextView mEditMan;


    public static PMBugDetailFragment newInstance(Object data) {
        sBug = (Bug) data;
        PMBugDetailFragment myBugDetailFragment = new PMBugDetailFragment();
        return myBugDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.bug_details, parent, false);
        init(view);
        contorl();
        return view;
    }

    private void init(View view) {
        mProjectName = (TextView) view.findViewById(R.id.bug_details_projectName);
        mBugVersion = (TextView) view.findViewById(R.id.bug_details_level);
        mBugState = (TextView) view.findViewById(R.id.bug_details_state);
        mBugContent = (TextView) view.findViewById(R.id.bug_details_content);
        mFoundTime = (TextView) view.findViewById(R.id.bug_details_found_time);
        mFoundMan = (TextView) view.findViewById(R.id.bug_details_found_man);
        mEditTime = (TextView) view.findViewById(R.id.bug_details_edit_time);
        mEditMan = (TextView) view.findViewById(R.id.bug_details_edit_man);

    }

    private void contorl() {
        mProjectName.setText(sBug.getProject_name());
        mBugVersion.setText(sBug.getLevel());
        mBugState.setText(sBug.getStatus());
        mBugContent.setText(sBug.getProject_name());
        if (sBug.getSubmit_time() != 0)
            mFoundTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(sBug.getSubmit_time()));
        mFoundMan.setText(sBug.getSubmitter());
        if (sBug.getModify_time() != 0)
            mEditTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(sBug.getModify_time()));
        mFoundMan.setText(sBug.getSubmitter());
        mEditMan.setText(sBug.getEditMan());

    }
}
