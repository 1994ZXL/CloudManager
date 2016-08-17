package com.example.zxl.cloudmanager.mission.projectManagerMission;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.Link;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class MissionSearchFragment extends Fragment {
    private static final String TAG = "MissionSearchFragment";

    private Spinner mProjectName;
    private Spinner mMemName;
    private Button mBeginTimeButton;
    private Button mEndTimeButton;
    private Spinner mStateSpinner;

    private Button mSearchBtn;
    private static final String[] stateList={"待完成", "已完成"};
    private ArrayAdapter<String> stateAdapter;
    private int state;

    private ArrayList<String> MemNameList = new ArrayList<String>();
    private ArrayList<String> MemIdList = new ArrayList<String>();
    private ArrayAdapter<String> MemNameAdapter;
    private String memId;

    private ArrayList<String> projecNametList = new ArrayList<String>();
    private ArrayList<String> projectIdList = new ArrayList<String>();
    private ArrayAdapter<String> projectNameAdapter;
    private String projectId;

    private Date beginTime;
    private String bgtime;
    private Date endTime;
    private String edtime;

    private static AsyncHttpClient mHttpcProject = new AsyncHttpClient();
    private static AsyncHttpClient mHttpcMem = new AsyncHttpClient();
    private RequestParams mParamsProject = new RequestParams();
    private RequestParams mParamsMem = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private Fragment mFragment;
    public MissionSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getActionBar().setTitle("项目任务查询");
        View v = inflater.inflate(R.layout.fragment_mission_search, container, false);
        init(v);

        mHttpcProject.post(Link.localhost + "pm_task&act=options_project_name", mParamsProject, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("data1");
                    for (int i = 0; i < array.length(); i++) {
                        if (array.getJSONObject(i).has("pm_id"))
                            projectIdList.add(array.getJSONObject(i).getString("pm_id"));
                        if (array.getJSONObject(i).has("project_name"))
                            projecNametList.add(array.getJSONObject(i).getString("project_name"));
                    }
                    projectNameAdapter = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, projecNametList);
                    projectNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mProjectName.setAdapter(projectNameAdapter);
                    mProjectName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            projectId = projectIdList.get(i);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } catch (JSONException e) {
                    Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                }
            }
        });

        mHttpcMem.post(Link.localhost + "pm_task&act=options_member", mParamsMem, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("data1");
                    for (int i = 0; i < array.length(); i++) {
                        if (array.getJSONObject(i).has("mem_id"))
                            MemIdList.add(array.getJSONObject(i).getString("mem_id"));
                        if (array.getJSONObject(i).has("mem_name"))
                            MemNameList.add(array.getJSONObject(i).getString("mem_name"));
                    }
                    MemNameAdapter = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, MemNameList);
                    MemNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mMemName.setAdapter(MemNameAdapter);
                    mMemName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            memId = MemIdList.get(i);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } catch (JSONException e) {
                    Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                }
            }
        });

        stateAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStateSpinner.setAdapter(stateAdapter);
        mStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //项目任务状态 0:待完成 1:已完成
                if (stateList[i] == "待完成")
                    state = 0;
                if (stateList[i] == "已完成")
                    state = 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mBeginTimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 12);
                fragment.setTargetFragment(MissionSearchFragment.this, 12);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MissionSearchFragment");
            }
        });
        mEndTimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 13);
                fragment.setTargetFragment(MissionSearchFragment.this, 13);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MissionSearchFragment");
            }
        });
        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Fragment fragment = new MissionManagerListFragment();
                Bundle bundle = new Bundle();

                bundle.putString(Link.pmtask_id, projectId);
                bundle.putString(Link.mem_id, memId);
                if (null != bgtime) {
                    bundle.putInt(Link.start_time, DateForGeLingWeiZhi.toGeLinWeiZhi(bgtime));
                } else {
                    bundle.putInt(Link.start_time, -1);
                }
                if (null != edtime) {
                    bundle.putInt(Link.over_time, DateForGeLingWeiZhi.toGeLinWeiZhi(edtime));
                } else {
                    bundle.putInt(Link.over_time, -1);
                }

                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (!fragment.isAdded()) {
                    transaction.addToBackStack(null);
                    transaction.hide(mFragment);
                    transaction.add(R.id.blankActivity, fragment);
                    transaction.commit();
                } else {
                    transaction.hide(mFragment);
                    transaction.show(fragment);
                    transaction.commit();
                }
            }
        });
        return v;
    }

    private void init(View v){
        mBeginTimeButton = (Button)v.findViewById(R.id.pm_mission_begin_time_button);
        mEndTimeButton = (Button)v.findViewById(R.id.pm_mission_end_time_button);
        mProjectName = (Spinner) v.findViewById(R.id.pm_mission_projectName);
        mStateSpinner = (Spinner) v.findViewById(R.id.pm_mission_state);
        mMemName = (Spinner) v.findViewById(R.id.pm_mission_memName);
        mSearchBtn = (Button) v.findViewById(R.id.pm_mission_search_button);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "进入回调 " + " resultCode:" + requestCode);
        if (resultCode != Activity.RESULT_OK){
            Log.d(TAG, "未进入判断");
            return;
        } else if (requestCode == 12) {
            beginTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateBeginDate();
        } else if (requestCode == 13) {
            endTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateEndDate();
        }
    }
    private void updateBeginDate(){
        bgtime = android.text.format.DateFormat.format("yyyy年MM月dd", beginTime).toString();
        Log.d(TAG, "bgtime: " + bgtime);
        mBeginTimeButton.setText(bgtime);
        Log.d(TAG, "beginTimeButton: " + mBeginTimeButton.getText());
    }
    private void updateEndDate(){
        if (endTime.after(beginTime)) {
            edtime = android.text.format.DateFormat.format("yyyy年MM月dd", endTime).toString();
            Log.d(TAG, "edtime: " + edtime);
            mEndTimeButton.setText(edtime);
            Log.d(TAG, "endTimeButton: " + mEndTimeButton.getText());
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
