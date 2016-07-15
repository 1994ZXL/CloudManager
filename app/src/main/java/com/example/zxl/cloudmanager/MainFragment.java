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
import com.example.zxl.cloudmanager.publicSearch.bug.ProjectBugSearchActivity;
import com.example.zxl.cloudmanager.publicSearch.list.ProjectListSearchActivity;
import com.example.zxl.cloudmanager.publicSearch.memberList.MemberListActivity;
import com.example.zxl.cloudmanager.publicSearch.plan.MissionSearchActivity;
import com.example.zxl.cloudmanager.publicSearch.project.PublicSearchActivity;
import com.example.zxl.cloudmanager.publicSearch.usecase.UsecaseSearchActivity;

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


    //公共查询
    private ImageView myProjectImage;
    private ImageView myPlanImage;
    private ImageView myListImage;
    private ImageView myMemberListImage;
    private ImageView myUseCaseImage;
    private ImageView myBugImage;

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


        //公共查询
        onClickListener(myProjectImage,new PublicSearchActivity());
        onClickListener(myPlanImage,new MissionSearchActivity());
        onClickListener(myListImage,new ProjectListSearchActivity());
        onClickListener(myMemberListImage,new MemberListActivity());
        onClickListener(myUseCaseImage,new UsecaseSearchActivity());
        onClickListener(myBugImage,new ProjectBugSearchActivity());
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


        //公共查询
        myProjectImage = (ImageView) v.findViewById(R.id.main_fragment_project_query_image);
        myPlanImage = (ImageView) v.findViewById(R.id.main_fragment_project_plan_query_image);
        myListImage = (ImageView) v.findViewById(R.id.main_fragment_project_list_image);
        myMemberListImage = (ImageView) v.findViewById(R.id.main_fragment_project_memberlist_image);
        myUseCaseImage = (ImageView) v.findViewById(R.id.main_fragment_usecase_query_image);
        myBugImage = (ImageView) v.findViewById(R.id.main_fragment_bug_query_image);
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
