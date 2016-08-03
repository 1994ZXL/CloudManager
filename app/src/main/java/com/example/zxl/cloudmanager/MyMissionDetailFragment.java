package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxl.cloudmanager.model.Mission;

/**
 * Created by ZXL on 2016/7/21.
 */
public class MyMissionDetailFragment extends Fragment {
    private TextView mName;
    private TextView mContent;
    private TextView mLevel;
    private TextView mDetailContent;
    private TextView mBeginTime;
    private TextView mEndTime;
    private TextView mProgress;
    private TextView mState;
    private TextView mDisposeSuggestion;

    private static Mission sMission = new Mission();

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.mission_details, parent, false);

        init(view);
        contorl();

        return view;
    }

    public static MyMissionDetailFragment newInstance(Object data) {
        sMission = (Mission) data;
        MyMissionDetailFragment fragment = new MyMissionDetailFragment();
        return fragment;
    }

    private void init(View view) {
        mName = (TextView) view.findViewById(R.id.mission_details_name);
        mContent = (TextView) view.findViewById(R.id.mission_details_content);
        mLevel = (TextView) view.findViewById(R.id.mission_details_level);
        mDetailContent = (TextView) view.findViewById(R.id.mission_details_content_details);
        mBeginTime = (TextView) view.findViewById(R.id.mission_details_begin_time);
        mEndTime = (TextView) view.findViewById(R.id.mission_details_end_time);
        mProgress = (TextView) view.findViewById(R.id.mission_details_mission_progress);
        mState = (TextView) view.findViewById(R.id.mission_details_state);
        mDisposeSuggestion = (TextView) view.findViewById(R.id.mission_details_dispose_suggestion);
    }

    private void contorl() {
        mName.setText(sMission.getName());
        mContent.setText(sMission.getContent());
        mLevel.setText(sMission.getLevel());
        mDetailContent.setText(sMission.getDetailContent());
       /* mBeginTime.setText(sMission.getStart_time().toString());
        mEndTime.setText(sMission.getOver_time().toString());*/
        mProgress.setText(sMission.getProgress());
        mState.setText(sMission.getStatus());
        mDisposeSuggestion.setText(sMission.getDisposeSuggestion());
    }

}
