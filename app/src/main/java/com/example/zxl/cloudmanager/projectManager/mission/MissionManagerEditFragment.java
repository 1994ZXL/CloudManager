package com.example.zxl.cloudmanager.projectManager.mission;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Mission;

/**
 * Created by ZXL on 2016/7/21.
 */
public class MissionManagerEditFragment extends Fragment {
    private Spinner mName;
    private TextView mContent;
    private Spinner mLevel;
    private TextView mDetailContent;
    private TextView mBeginTime;
    private TextView mEndTime;
    private TextView mProgress;
    private Spinner mState;

    private static final String NAME_CHANGE = "项目修改";
    private static final String PHONE_CHANGE = "手机修改";
    private static final String QQ_CHANGE = "QQ修改";
    private static final String WECHAT_CHANGE = "微信修改";
    private static final String ADDRESS_CHANGE = "地址修改";

    private static Mission sMission = new Mission();

    private static final String[] stateList={"全部","待完成", "已完成"};
    private ArrayAdapter<String> stateAdapter;

    private static final String[] pmList={"全部"};
    private ArrayAdapter<String> pmAdapter;

    private static final String[] levelList={"全部","低","中","高"};
    private ArrayAdapter<String> levelAdapter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.pm_mission_edit_or_add, parent, false);

        init(view);
        stateAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mState.setAdapter(stateAdapter);

        pmAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, pmList);
        pmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mName.setAdapter(pmAdapter);

        levelAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, levelList);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLevel.setAdapter(levelAdapter);
        contorl();

        return view;
    }

    public static MissionManagerEditFragment newInstance(Object data) {
        sMission = (Mission) data;
        MissionManagerEditFragment fragment = new MissionManagerEditFragment();
        return fragment;
    }

    private void init(View view) {
        mName = (Spinner) view.findViewById(R.id.pm_mission_name);
        mContent = (TextView) view.findViewById(R.id.pm_mission_content);
        mLevel = (Spinner) view.findViewById(R.id.pm_mission_level);
        mDetailContent = (TextView) view.findViewById(R.id.pm_mission_content_details);
        mBeginTime = (TextView) view.findViewById(R.id.pm_mission_begin_time);
        mEndTime = (TextView) view.findViewById(R.id.pm_mission_end_time);
        mProgress = (TextView) view.findViewById(R.id.pm_mission_progress);
        mState = (Spinner) view.findViewById(R.id.pm_mission_state);

    }

    private void contorl() {
        // mName.setText(sMission.getName());
        mContent.setText(sMission.getContent());
        //mLevel.setText(sMission.getLevel());
        mDetailContent.setText(sMission.getDetailContent());
        mBeginTime.setText(sMission.getMissionBeginTime().toString());
        mEndTime.setText(sMission.getMissionEndTime().toString());
        mProgress.setText(sMission.getProgress());
        // mState.setText(sMission.getState());
    }

}
