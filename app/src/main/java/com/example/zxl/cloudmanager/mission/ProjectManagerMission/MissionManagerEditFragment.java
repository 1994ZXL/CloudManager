package com.example.zxl.cloudmanager.mission.projectManagerMission;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Mission;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/7/21.
 */
public class MissionManagerEditFragment extends Fragment {
    private static final String TAG = "PMMissionEditFragment";

    private Spinner mTitle;
    private TextView mContent;
    private TextView mBeginTime;
    private TextView mEndTime;
    private Spinner mState;

    private Fragment mFragment;

    private static Mission sMission = new Mission();

    private static final String[] stateList={"待完成", "已完成"};
    private ArrayAdapter<String> stateAdapter;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.pm_mission_edit_or_add, parent, false);

        init(view);
        stateAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mState.setAdapter(stateAdapter);

        mHttpc.post(Link.localhost + "pm_task&act=options_project_name", mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }
        });

        contorl();

        return view;
    }

    public static MissionManagerEditFragment newInstance(Object data) {
        sMission = (Mission) data;
        MissionManagerEditFragment fragment = new MissionManagerEditFragment();
        return fragment;
    }

    private void init(View view) {
        mTitle = (Spinner) view.findViewById(R.id.pm_mission_name);
        mContent = (TextView) view.findViewById(R.id.pm_mission_content);
        mBeginTime = (TextView) view.findViewById(R.id.pm_mission_begin_time);
        mEndTime = (TextView) view.findViewById(R.id.pm_mission_end_time);

        mState = (Spinner) view.findViewById(R.id.pm_mission_state);


    }

    private void contorl() {
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
    }

}
