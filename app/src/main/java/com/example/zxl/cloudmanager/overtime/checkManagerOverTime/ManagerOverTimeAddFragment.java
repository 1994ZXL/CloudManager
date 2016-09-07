package com.example.zxl.cloudmanager.overtime.checkManagerOverTime;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
 * Created by ZXL on 2016/9/7.
 */
public class ManagerOverTimeAddFragment extends Fragment {
    private static final String TAG = "MOverTimeAdd";

    private Spinner mMemName;
    private Spinner mProjectName;
    private TextView mBeginTime;
    private TextView mEndTime;
    private EditText mReason;

    private TextView mBack;
    private TextView mAdd;

    private ArrayAdapter<String> nameAdapter;
    private ArrayAdapter<String> projectAdapter;

    private ArrayList<String> mem_name = new ArrayList<String>(); //名字
    private ArrayList<String> mem_id = new ArrayList<String>(); //名字id
    private String memid;

    private ArrayList<String> project_name = new ArrayList<String>(); //项目
    private ArrayList<String> pm_id = new ArrayList<String>(); //项目id
    private String projectid;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private static AsyncHttpClient mHttpcAdd = new AsyncHttpClient();
    private RequestParams mParamsAdd = new RequestParams();
    private JSONObject keyObjAdd = new JSONObject();
    private String keyAdd = "";

    private String reason;

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_manager_overtime_add, container, false);

        init(v);
        control();

        return v;
    }

    private void init(View v) {
        mMemName = (Spinner) v.findViewById(R.id.overtime_add_mem_name);
        mProjectName = (Spinner) v.findViewById(R.id.overtime_add_project_name);
        mBeginTime = (TextView) v.findViewById(R.id.overtime_add_begin_time);
        mEndTime = (TextView) v.findViewById(R.id.overtime_add_end_time);
        mReason = (EditText) v.findViewById(R.id.overtime_add_reason);

        mBack = (TextView) v.findViewById(R.id.overtime_add_back);
        mAdd = (TextView) v.findViewById(R.id.overtime_add_add);
    }

    private void control() {
        try {
            keyObj.put(Link.comp_id, User.newInstance().getComp_id());
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);

        mHttpc.post(Link.localhost + "manage_work&act=get_options", mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                try {
                    if (rjo.getBoolean("result")) {
                        JSONArray workArray = rjo.getJSONArray("data1");
                        JSONArray memArray = rjo.getJSONArray("data2");
                        Log.d(TAG, "workArray: " + workArray);
                        Log.d(TAG, "memArray: " + memArray);

                        for (int i = 0; i < workArray.length(); i++) {
                            if (workArray.getJSONObject(i).has("project_name"))
                                project_name.add(workArray.getJSONObject(i).getString("project_name"));
                            if (workArray.getJSONObject(i).has("pm_id"))
                                pm_id.add(workArray.getJSONObject(i).getString("pm_id"));
                        }

                        for (int j = 0; j < memArray.length(); j++) {
                            if (memArray.getJSONObject(j).has("mem_name"))
                                mem_name.add(memArray.getJSONObject(j).getString("mem_name"));
                            if (memArray.getJSONObject(j).has("mem_id"))
                                mem_id.add(memArray.getJSONObject(j).getString("mem_id"));
                        }

                        if (null != mFragment.getActivity()){
                            projectAdapter = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, project_name);
                            projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mProjectName.setAdapter(projectAdapter);
                            mProjectName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    projectid = pm_id.get(i);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                            nameAdapter= new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, mem_name);
                            nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mMemName.setAdapter(nameAdapter);
                            mMemName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    memid = mem_id.get(i);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }

                    } else {

                    }
                } catch (JSONException e) {
                    Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                }
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

        mReason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                reason = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == projectid
                        || null == memid
                        || null == mBeginTime.getText()
                        || null == mEndTime.getText()
                        || null == reason) {
                    Toast.makeText(getActivity(),
                            "填写信息不全哦",
                            Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        keyObjAdd.put(Link.mem_id, memid);
                        keyObjAdd.put(Link.work_pm, projectid);
                        keyObjAdd.put(Link.start_time, DateForGeLingWeiZhi.toGeLinWeiZhi3(mBeginTime.getText().toString()));
                        keyObjAdd.put(Link.end_time, DateForGeLingWeiZhi.toGeLinWeiZhi3(mEndTime.getText().toString()));
                        keyObjAdd.put(Link.work_reason, reason);

                        keyObjAdd.put(Link.user_id, User.newInstance().getUser_id());
                        keyAdd = DESCryptor.Encryptor(keyObjAdd.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mParamsAdd.put("key", keyAdd);
                    Log.d(TAG, "key: " + keyAdd);
                    mHttpcAdd.post(Link.localhost + "manage_work&act=add", mParamsAdd, new JsonHttpResponseHandler() {
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
                    });
                }
            }
        });
    }
}
