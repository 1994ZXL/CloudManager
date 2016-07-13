package com.example.zxl.cloudmanager;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zxl.cloudmanager.Memo.MemoFragment;

public class MainFragment extends Fragment {
    private ImageView myMemoImage;
    private ImageView myMesssageImage;
    private ImageView myCheckImage;
    private ImageView myLeaveImage;
    private ImageView myMissionImage;
    private ImageView myPostImage;
    private ImageView mUseCaseImage;
    private ImageView mBugImage;
    private ImageView mOvertimeImage;
    private ImageView mTravelImage;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.main_fragment, parent, false);
        getActivity().getActionBar().setTitle("企业云");

        init(v);
        onClickListener(myMemoImage, new MemoActivity());
        onClickListener(myMesssageImage, new MyMessageAcivity());
        onClickListener(myCheckImage, new MyCheckActivity());
        onClickListener(myLeaveImage, new MyLeaveActivity());
        onClickListener(myMissionImage, new MyMissionActivity());
        onClickListener(myPostImage, new MyPostActivity());
        onClickListener(mUseCaseImage, new MyUseCaseActivity());
        onClickListener(mBugImage, new MyBugActivity());
        onClickListener(mOvertimeImage, new MyOverTimeActivity());
        onClickListener(mTravelImage, new MyTravelActivity());
        return v;
    }

    private void init(View v) {
        myMemoImage = (ImageView)v.findViewById(R.id.main_fragment_my_memo_image);
        myMesssageImage = (ImageView)v.findViewById(R.id.main_fragment_my_message_image);
        myCheckImage = (ImageView)v.findViewById(R.id.main_fragment_my_check_image);
        myLeaveImage = (ImageView)v.findViewById(R.id.main_fragment_my_leave_image);
        myMissionImage = (ImageView)v.findViewById(R.id.main_fragment_my_mission_image);
        myPostImage = (ImageView)v.findViewById(R.id.main_fragment_my_post_image);
        mUseCaseImage = (ImageView)v.findViewById(R.id.main_fragment_my_usecase_image);
        mBugImage = (ImageView)v.findViewById(R.id.main_fragment_my_bug_image);
        mOvertimeImage = (ImageView)v.findViewById(R.id.main_fragment_overtime_image);
        mTravelImage = (ImageView)v.findViewById(R.id.main_fragment_travel_image);
    }

    private void onClickListener(ImageView imageView,final Activity activity) {

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), activity.getClass());
                startActivity(intent);
            }
        });
    }

}
