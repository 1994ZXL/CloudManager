package com.example.zxl.cloudmanager.mission.projectManagerMission;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DateTimePicker;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/9/1.
 */
public class MissionManagerAddFragment extends Fragment {
    private static final String TAG ="PMTaskAddFragment";

    private Spinner mProjectName;
    private EditText mMissionName;
    private EditText mMissionContent;
    private Spinner mMemberName;
    private TextView mBeginTime;
    private TextView mEndTime;
    private Spinner mStatus;
    private EditText mEvaluate;

    private TextView mSaveTextView;
    private TextView mBack;

    private String missionName;
    private String missionContent;
    private int status;
    private String evaluate;

    private String[] statusList = new String[]{"待完成", "已完成"};
    private ArrayAdapter<String> statusAdapter;

    private ArrayList<String> projecNametList = new ArrayList<String>();
    private ArrayList<String> projectIdList = new ArrayList<String>();
    private ArrayAdapter<String> projectNameAdapter;
    private String projectId;

    private ArrayList<String> memberNametList = new ArrayList<String>();
    private ArrayList<String> memberIdList = new ArrayList<String>();
    private ArrayAdapter<String> memberNameAdapter;
    private String memberId;

    private static AsyncHttpClient mHttpcAdd = new AsyncHttpClient();
    private RequestParams mParamsAdd  = new RequestParams();
    private JSONObject keyObjAdd  = new JSONObject();
    private String keyAdd  = "";

    private static AsyncHttpClient mHttpcProject = new AsyncHttpClient();
    private RequestParams mParamsProject = new RequestParams();
    private JSONObject keyObjProject = new JSONObject();
    private String keyProject = "";

    private static AsyncHttpClient mHttpcMember = new AsyncHttpClient();
    private RequestParams mParamsMember= new RequestParams();
    private JSONObject keyObjMember = new JSONObject();
    private String keyMember = "";

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        final View v = layoutInflater.inflate(R.layout.mission_add, parent, false);
        saveInstanceState = getArguments();

        init(v);
        control(saveInstanceState);

        return v;
    }

    public void init(View v) {
        mProjectName = (Spinner) v.findViewById(R.id.pm_task_add_projectName);
        mMissionName = (EditText) v.findViewById(R.id.pm_task_add_missionName);
        mMissionContent = (EditText) v.findViewById(R.id.pm_task_add_missionContent);
        mMemberName = (Spinner) v.findViewById(R.id.pm_task_add_memberName);
        mBeginTime = (TextView) v.findViewById(R.id.pm_task_add_beginTime);
        mEndTime = (TextView) v.findViewById(R.id.pm_task_add_endTime);
        mStatus = (Spinner) v.findViewById(R.id.pm_task_add_missionState);
        mEvaluate = (EditText) v.findViewById(R.id.pm_task_add_evaluate);
        mSaveTextView = (TextView) v.findViewById(R.id.pm_task_add_save);
        mBack = (TextView) v.findViewById(R.id.pm_task_add_back);
    }

    public void control(final Bundle bundle) {
        try {
            keyObjProject.put(Link.user_id, User.newInstance().getUser_id());
            keyProject = DESCryptor.Encryptor(keyObjProject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParamsProject.put("key", keyProject);
        Log.d(TAG,"key:" + keyProject);

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


        try {
            keyObjMember.put(Link.user_id, User.newInstance().getUser_id());
            keyMember = DESCryptor.Encryptor(keyObjMember.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParamsMember.put("key", keyMember);
        Log.d(TAG,"key:" + keyMember);

        mHttpcMember.post(Link.localhost + "pm_task&act=options_member", mParamsMember, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("data1");
                    for (int i = 0; i < array.length(); i++) {
                        if (array.getJSONObject(i).has("mem_id"))
                            memberIdList.add(array.getJSONObject(i).getString("mem_id"));
                        if (array.getJSONObject(i).has("mem_name"))
                            memberNametList.add(array.getJSONObject(i).getString("mem_name"));
                    }
                    memberNameAdapter = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, memberNametList);
                    memberNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mMemberName.setAdapter(memberNameAdapter);
                    mMemberName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            memberId = memberIdList.get(i);
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

        mMissionName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                missionName = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mMissionContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                missionContent = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mBeginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mBeginTime);
            }
        });

        mEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mEndTime);
            }
        });

        statusAdapter = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, statusList);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStatus.setAdapter(statusAdapter);
        mStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                status = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mEvaluate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                evaluate = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mSaveTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == projectId || null == missionName || null == mBeginTime.getText()
                        || null == memberId || null == mEndTime.getText()) {
                    Toast.makeText(getActivity(),
                            "条件不全",
                            Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        keyObjAdd.put(Link.pm_id, projectId);

                        if (null != missionContent)
                            keyObjAdd.put(Link.content, missionContent);

                        keyObjAdd.put(Link.mem_id, memberId);

                        keyObjAdd.put(Link.title, missionName);


                        keyObjAdd.put(Link.start_time, DateForGeLingWeiZhi.toGeLinWeiZhi3(mBeginTime.getText().toString()));


                        keyObjAdd.put(Link.over_time, DateForGeLingWeiZhi.toGeLinWeiZhi3(mEndTime.getText().toString()));

                        if (null != evaluate)
                            keyObjAdd.put(Link.evaluate, evaluate);

                        keyObjAdd.put(Link.status, status);

                        keyAdd = DESCryptor.Encryptor(keyObjAdd.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mParamsAdd.put("key", keyAdd);
                    Log.d(TAG,"key:" + keyAdd);

                    mHttpcAdd.post(Link.localhost + "pm_task&act=add", mParamsAdd, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                Toast.makeText(getActivity(),
                                        response.getString("msg"),
                                        Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Toast.makeText(getActivity(),
                                    R.string.edit_error,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                Fragment fragment = new MissionManagerListFragment();
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
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });
    }


}
