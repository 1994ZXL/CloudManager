package com.example.zxl.cloudmanager.mission.myMission;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Mission;

/**
 * Created by ZXL on 2016/7/21.
 */
public class MyMissionDetailFragment extends Fragment {
    private TextView mTitle;
    private TextView mContent;
    private TextView mBeginTime;
    private TextView mEndTime;
    private TextView mState;
    private static Mission sMission = new Mission();

    private TextView mBack;

    private Fragment mFrgment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFrgment = this;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.mission_details, parent, false);
        saveInstanceState = getArguments();

        init(view);
        contorl(saveInstanceState);

        return view;
    }

    public static MyMissionDetailFragment newInstance(Object data) {
        sMission = (Mission) data;
        MyMissionDetailFragment fragment = new MyMissionDetailFragment();
        return fragment;
    }

    private void init(View view) {
        mTitle = (TextView) view.findViewById(R.id.mission_details_name);
        mContent = (TextView) view.findViewById(R.id.mission_details_content);
        mBeginTime = (TextView) view.findViewById(R.id.mission_details_begin_time);
        mEndTime = (TextView) view.findViewById(R.id.mission_details_end_time);
        mState = (TextView) view.findViewById(R.id.mission_details_state);
        mBack = (TextView) view.findViewById(R.id.mission_details_back);
    }

    private void contorl(final Bundle bundle) {
        mTitle.setText(sMission.getTitle());
        mContent.setText(sMission.getContent());
        if (sMission.getStart_time() == 0) {
            mBeginTime.setText("——");
        } else {
            mBeginTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(sMission.getStart_time()));
        }

        if (sMission.getStart_time() == 0) {
            mEndTime.setText("——");
        } else {
            mEndTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(sMission.getEnd_time()));
        }
        mState.setText(sMission.getStatus());
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFrgment.getActivity().finish();
            }
        });
    }

}
