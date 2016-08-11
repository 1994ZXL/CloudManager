package com.example.zxl.cloudmanager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.zxl.cloudmanager.bug.myBug.MyBugActivity;
import com.example.zxl.cloudmanager.check.checkManager.ManagerCheckAcitvity;
import com.example.zxl.cloudmanager.check.myCheck.MyCheckActivity;
import com.example.zxl.cloudmanager.leave.checkManagerLeave.ManagerLeaveActivity;
import com.example.zxl.cloudmanager.mission.projectManagerMission.ProjectMissionManagerActivity;
import com.example.zxl.cloudmanager.model.CheckLab;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Post;
import com.example.zxl.cloudmanager.model.PostLab;
import com.example.zxl.cloudmanager.usecase.myUseCase.MyUseCaseActivity;
import com.example.zxl.cloudmanager.overtime.checkManagerOverTime.ManagerOvertimeActivity;
import com.example.zxl.cloudmanager.travel.checkManagerTravel.ManagerTravelActivity;
import com.example.zxl.cloudmanager.check.leader.LeaderCheckSearchActivity;
import com.example.zxl.cloudmanager.leave.leader.LeaderLeaveSearchActivity;
import com.example.zxl.cloudmanager.overtime.leader.LeaderOvertimeSearchActivity;
import com.example.zxl.cloudmanager.post.leader.LeaderPostSearchActivity;
import com.example.zxl.cloudmanager.travel.leader.LeaderTravelSearchActivity;
import com.example.zxl.cloudmanager.mission.myMission.MyMissionActivity;
import com.example.zxl.cloudmanager.message.myMessage.MyMessageAcivity;
import com.example.zxl.cloudmanager.leave.myLeave.MyLeaveActivity;
import com.example.zxl.cloudmanager.post.myPost.MyPostActivity;
import com.example.zxl.cloudmanager.operation.MyOperationActivity;
import com.example.zxl.cloudmanager.overtime.myOvertime.MyOverTimeActivity;
import com.example.zxl.cloudmanager.bug.projectManagerBug.ProjectBugDealActivity;
import com.example.zxl.cloudmanager.projectManager.PMAdressBook.ProjectManagerActivity;
import com.example.zxl.cloudmanager.projectManager.memberManager.ProjectMemberManagerActivity;
import com.example.zxl.cloudmanager.projectManager.projectList.ProjectConnectionListActivity;
import com.example.zxl.cloudmanager.usecase.projectManagerUseCase.ProjectManagerUsecaseActivity;
import com.example.zxl.cloudmanager.bug.publicSearchBug.ProjectBugSearchActivity;
import com.example.zxl.cloudmanager.publicSearch.PSAddressBook.ProjectListSearchActivity;
import com.example.zxl.cloudmanager.publicSearch.memberList.MemberListActivity;
import com.example.zxl.cloudmanager.mission.publicSearchMission.MissionSearchActivity;
import com.example.zxl.cloudmanager.publicSearch.projectSearch.PublicSearchActivity;
import com.example.zxl.cloudmanager.usecase.publicSearchUseCase.UsecaseSearchActivity;
import com.example.zxl.cloudmanager.travel.myTravel.MyTravelActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";

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
    private LinearLayout leaderLinearLayout;
    private ImageView leaderPostImage;
    private ImageView leaderCheckImage;
    private ImageView leaderLeaveImage;
    private ImageView leaderTravelImage;
    private ImageView leaderOvertimeImage;

    //考勤主管
    private LinearLayout CMLinearLayout;
    private ImageView managerOvertimeImage;
    private ImageView managerTravelImage;

    //项目主管
    private LinearLayout PMLinearLayout;
    private ImageView projectManagmentImage;
    private ImageView pmBugDealImage;
    private ImageView pmUsecaseImage;
    private ImageView pmMemberManagerImage;
    private ImageView pmListImage;
    private ImageView pmMissionImage;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private String mAtt_id;

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


        try {
            keyObj.put(Link.att_date_start, 1470758400);
            keyObj.put(Link.att_date_end, 1470844800);
            keyObj.put(Link.mem_id, "93aa131446db0f983904e812a2f94e6d");
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);
        mHttpc.post(Link.localhost + "my_punch&act=get_list", mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getBoolean("result")) {
                        JSONArray array = response.getJSONArray("data1");
                        Log.d(TAG, "array: " + array);
                        for (int i = 0; i < array.length(); i++) {
                            mAtt_id = array.getJSONObject(i).getString(Link.att_id);
                            if (0 == array.getJSONObject(i).getInt(Link.s_att_time)) {
                                mSignTime = "——";
                            } else {
                                mSignTime = DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(array.getJSONObject(i).getInt(Link.s_att_time));
                            }
                            if (0 == array.getJSONObject(i).getInt(Link.e_att_time)) {
                                mOffSignTime = "——";
                            } else {
                                mOffSignTime = DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(array.getJSONObject(i).getInt(Link.e_att_time));
                            }
                        }
                        mSignBtn.setText(mSignTime);
                        mOffSignBtn.setText(mOffSignTime);
                    } else {

                    }
                } catch (JSONException e) {
                    Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                }
            }
        });

        if (mSignTime == "——" || mSignTime == null) {
            mSignBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    check("start_work");
                    sign();
                }
            });
        } else {
            mSignBtn.setClickable(false);
        }

        if (mOffSignTime == "——" || mOffSignTime == null) {
            mOffSignBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    check("end_work");
                    offSign();
                }
            });
        } else {
            mOffSignBtn.setClickable(false);
        }


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
        leaderLinearLayout = (LinearLayout) v.findViewById(R.id.leader_linearlayout);
        leaderPostImage = (ImageView) v.findViewById(R.id.main_fragment_post_query_image);
        leaderCheckImage = (ImageView) v.findViewById(R.id.main_fragment_check_query_image);
        leaderLeaveImage =(ImageView) v.findViewById(R.id.main_fragment_leave_record_query_image);
        leaderOvertimeImage =(ImageView) v.findViewById(R.id.imageView);
        leaderTravelImage =(ImageView) v.findViewById(R.id.imageView2);

        //考勤主管
        CMLinearLayout = (LinearLayout) v.findViewById(R.id.check_manager_linearlayout);
        managerOvertimeImage = (ImageView) v.findViewById(R.id.main_fragment_overtime_deal_image);
        managerTravelImage = (ImageView) v.findViewById(R.id.main_fragment_travel_deal_image);

        //项目主管
        PMLinearLayout = (LinearLayout) v.findViewById(R.id.project_manager_linearlayout);
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

    private void check(String function) {
        try {
            keyObj.put(Link.att_id, mAtt_id);
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG,"key:" + key);
        mHttpc.post(Link.localhost + "my_punch&act=" + function, mParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d(TAG, "签到成功");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "签到失败");
            }
        });
    }

    private void sign() {
        mSignTime = android.text.format.DateFormat.format("yyyy.M.dd   HH:mm:ss", System.currentTimeMillis()).toString();
        mSignBtn.setText(mSignTime);
        mSignBtn.setClickable(false);
    }

    private void offSign() {
        mOffSignTime = android.text.format.DateFormat.format("yyyy.M.dd   HH:mm:ss", System.currentTimeMillis()).toString();
        mOffSignBtn.setText(mOffSignTime);
        mOffSignBtn.setClickable(false);
    }

    private String getTime() {
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String date = android.text.format.DateFormat.format("yyyy年MM月dd", curDate).toString();
        return date;
    }

}
