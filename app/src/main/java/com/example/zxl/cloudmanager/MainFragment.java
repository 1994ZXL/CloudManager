package com.example.zxl.cloudmanager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zxl.cloudmanager.bug.myBug.MyBugActivity;
import com.example.zxl.cloudmanager.check.checkManager.ManagerCheckAcitvity;
import com.example.zxl.cloudmanager.check.myCheck.MyCheckActivity;
import com.example.zxl.cloudmanager.leave.checkManagerLeave.ManagerLeaveActivity;
import com.example.zxl.cloudmanager.usecase.myUseCase.MyUseCaseActivity;
import com.example.zxl.cloudmanager.overtime.checkManagerOverTime.ManagerOvertimeActivity;
import com.example.zxl.cloudmanager.travel.checkManagerTravel.ManagerTravelActivity;
import com.example.zxl.cloudmanager.check.leader.LeaderCheckSearchActivity;
import com.example.zxl.cloudmanager.leave.leader.LeaderLeaveSearchActivity;
import com.example.zxl.cloudmanager.overtime.leader.LeaderOvertimeSearchActivity;
import com.example.zxl.cloudmanager.post.leader.LeaderPostSearchActivity;
import com.example.zxl.cloudmanager.travel.leader.LeaderTravelSearchActivity;
import com.example.zxl.cloudmanager.mission.myMission.MyMissionActivity;
import com.example.zxl.cloudmanager.model.Check;
import com.example.zxl.cloudmanager.model.CheckLab;
import com.example.zxl.cloudmanager.message.myMessage.MyMessageAcivity;
import com.example.zxl.cloudmanager.leave.myLeave.MyLeaveActivity;
import com.example.zxl.cloudmanager.post.myPost.MyPostActivity;
import com.example.zxl.cloudmanager.operation.MyOperationActivity;
import com.example.zxl.cloudmanager.overtime.myOvertime.MyOverTimeActivity;
import com.example.zxl.cloudmanager.bug.projectManagerBug.ProjectBugDealActivity;
import com.example.zxl.cloudmanager.projectManager.PMAdressBook.ProjectManagerActivity;
import com.example.zxl.cloudmanager.projectManager.memberManager.ProjectMemberManagerActivity;
import com.example.zxl.cloudmanager.mission.ProjectManagerMission.ProjectMissionManagerActivity;
import com.example.zxl.cloudmanager.projectManager.projectList.ProjectConnectionListActivity;
import com.example.zxl.cloudmanager.usecase.projectManagerUseCase.ProjectManagerUsecaseActivity;
import com.example.zxl.cloudmanager.bug.publicSearchBug.ProjectBugSearchActivity;
import com.example.zxl.cloudmanager.publicSearch.PSAddressBook.ProjectListSearchActivity;
import com.example.zxl.cloudmanager.publicSearch.memberList.MemberListActivity;
import com.example.zxl.cloudmanager.mission.publicSearchMission.MissionSearchActivity;
import com.example.zxl.cloudmanager.publicSearch.projectSearch.PublicSearchActivity;
import com.example.zxl.cloudmanager.usecase.publicSearchUseCase.UsecaseSearchActivity;
import com.example.zxl.cloudmanager.travel.myTravel.MyTravelActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    /*考勤按钮*/
    private Button mSignBtn;
    private Button mOffSignBtn;
    private String mSignTime;
    private ArrayList<Check> mChecks = new ArrayList<Check>();
    private String mOffSignTime;

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

    //项目主管
    private ImageView projectManagmentImage;
    private ImageView pmBugDealImage;
    private ImageView pmUsecaseImage;
    private ImageView pmMemberManagerImage;
    private ImageView pmListImage;
    private ImageView pmMissionImage;

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
    }

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
        onClickListener(leaderTravelImage, new LeaderTravelSearchActivity());

        //项目主管
        onClickListener(projectManagmentImage, new ProjectManagerActivity());
        onClickListener(pmBugDealImage, new ProjectBugDealActivity());
        onClickListener(pmListImage, new ProjectConnectionListActivity());
        onClickListener(pmMemberManagerImage, new ProjectMemberManagerActivity());
        onClickListener(pmMissionImage, new ProjectMissionManagerActivity());
        onClickListener(pmUsecaseImage, new ProjectManagerUsecaseActivity());

        mSignBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sign();
            }
        });
        mOffSignBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                offSign();
            }
        });
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

        /*考勤按钮*/
        mSignBtn = (Button) v.findViewById(R.id.work_sign_button);
        mOffSignBtn = (Button) v.findViewById(R.id.work_off_sign_button);

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

        //项目主管
        projectManagmentImage = (ImageView) v.findViewById(R.id.main_fragment_project_manage);
        pmListImage = (ImageView) v.findViewById(R.id.main_fragment_project_memo);
        pmMemberManagerImage = (ImageView) v.findViewById(R.id.main_fragment_project_member_manage);
        pmBugDealImage = (ImageView) v.findViewById(R.id.main_fragment_bug_manage_image);
        pmMissionImage = (ImageView) v.findViewById(R.id.main_fragment_project_mission_manage);
        pmUsecaseImage = (ImageView) v.findViewById(R.id.main_fragment_usecase_manage_image);
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

    private void sign() {
        mSignTime = android.text.format.DateFormat.format("yyyy.M.dd   HH:mm:ss", getTime()).toString();
        Check check = new Check();
        check.setDutyTime(mSignTime);
        CheckLab.newInstance(mFragment.getActivity()).add(check);
        mSignBtn.setText(mSignTime);
        mSignBtn.setClickable(false);
    }

    private void offSign() {
        mOffSignTime = android.text.format.DateFormat.format("yyyy.M.dd   HH:mm:ss", getTime()).toString();
        mChecks = CheckLab.newInstance(mFragment.getActivity()).get();
        mChecks.get(mChecks.size() - 1).setOffDutyTime(mOffSignTime);
        mOffSignBtn.setText(mOffSignTime);
        mOffSignBtn.setClickable(false);
    }

    private Date getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return curDate;
    }

}
