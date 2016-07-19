package com.example.zxl.cloudmanager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zxl.cloudmanager.checkManager.overtime.ManagerOvertimeActivity;
import com.example.zxl.cloudmanager.checkManager.travel.ManagerTravelActivity;
import com.example.zxl.cloudmanager.leaderSearch.LeaderCheckSearchActivity;
import com.example.zxl.cloudmanager.leaderSearch.LeaderLeaveSearchActivity;
import com.example.zxl.cloudmanager.leaderSearch.LeaderOvertimeSearchActivity;
import com.example.zxl.cloudmanager.leaderSearch.LeaderPostSearchActivity;
import com.example.zxl.cloudmanager.operation.MyOperationActivity;
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
    private ImageView mManageCheck;
    private ImageView mManageLeave;


    //公共查询
    private ImageView myProjectImage;
    private ImageView myPlanImage;
    private ImageView myListImage;
    private ImageView myMemberListImage;
    private ImageView myUseCaseImage;
    private ImageView myBugImage;
    private ImageView myOperationImage;

    //领导查询
    private ImageView leaderPostImage;
    private ImageView leaderCheckImage;
    private ImageView leaderLeaveImage;
    private ImageView leaderTravelImage;
    private ImageView leaderOvertimeImage;

    //考勤主管
    private ImageView managerOvertimeImage;
    private ImageView managerTravelImage;

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
        onClickListener(myOperationImage, new MyOperationActivity());

        //考勤主管
        onClickListener(managerOvertimeImage, new ManagerOvertimeActivity());
        onClickListener(managerTravelImage, new ManagerTravelActivity());

        //公共查询
        onClickListener(myProjectImage,new PublicSearchActivity());
        onClickListener(myPlanImage,new MissionSearchActivity());
        onClickListener(myListImage,new ProjectListSearchActivity());
        onClickListener(myMemberListImage,new MemberListActivity());
        onClickListener(myUseCaseImage,new UsecaseSearchActivity());
        onClickListener(myBugImage,new ProjectBugSearchActivity());
        onClickListener(mManageCheck, new ManagerCheckAcitvity());
        onClickListener(mManageLeave, new ManagerLeaveActivity());

        //领导查询
        onClickListener(leaderCheckImage, new LeaderCheckSearchActivity());
        onClickListener(leaderLeaveImage, new LeaderLeaveSearchActivity());
        onClickListener(leaderOvertimeImage, new LeaderOvertimeSearchActivity());
        onClickListener(leaderPostImage, new LeaderPostSearchActivity());
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
        myOperationImage = (ImageView) v.findViewById(R.id.main_fragment_my_maintain_image) ;


        //公共查询
        myProjectImage = (ImageView) v.findViewById(R.id.main_fragment_project_query_image);
        myPlanImage = (ImageView) v.findViewById(R.id.main_fragment_project_plan_query_image);
        myListImage = (ImageView) v.findViewById(R.id.main_fragment_project_list_image);
        myMemberListImage = (ImageView) v.findViewById(R.id.main_fragment_project_memberlist_image);
        myUseCaseImage = (ImageView) v.findViewById(R.id.main_fragment_usecase_query_image);
        myBugImage = (ImageView) v.findViewById(R.id.main_fragment_bug_query_image);
        mManageCheck = (ImageView)v.findViewById(R.id.main_fragment_check_deal_image);
        mManageLeave = (ImageView)v.findViewById(R.id.main_fragment_leave_deal_image);

        //领导查询
        leaderPostImage = (ImageView) v.findViewById(R.id.main_fragment_post_query_image);
        leaderCheckImage = (ImageView) v.findViewById(R.id.main_fragment_check_query_image);
        leaderLeaveImage =(ImageView) v.findViewById(R.id.main_fragment_leave_record_query_image);
        leaderOvertimeImage =(ImageView) v.findViewById(R.id.imageView);
        leaderTravelImage =(ImageView) v.findViewById(R.id.imageView2);

        //考勤主管
        managerOvertimeImage = (ImageView) v.findViewById(R.id.main_fragment_overtime_deal_image);
        managerTravelImage = (ImageView) v.findViewById(R.id.main_fragment_travel_deal_image);
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
