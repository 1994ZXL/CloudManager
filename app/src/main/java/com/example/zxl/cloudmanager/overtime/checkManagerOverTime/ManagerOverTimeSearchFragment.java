package com.example.zxl.cloudmanager.overtime.checkManagerOverTime;


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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.DateTimePicker;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.User;
import com.example.zxl.cloudmanager.overtime.myOvertime.MyOverTimeActivity;
import com.example.zxl.cloudmanager.overtime.myOvertime.MyOverTimeFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerOverTimeSearchFragment extends Fragment {

    private static final String TAG = "MOTSearchFragment";
    private TextView mOvertimeBeginBtn;
    private TextView mOvertimeEndBtn;
    private Spinner mEmployerNameSpinner;
    private Spinner mEmployerProjectSpinner;
    private Spinner mOvertimeStatusSpinner;
    private LinearLayout mNameLinearLayout;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private TextView mBack;
    private TextView mAdd;

    private Fragment mFragment;
    private Fragment mAimFragment;

    private ArrayAdapter<String> nameAdapter;
    private ArrayAdapter<String> projectAdapter;
    private ArrayAdapter<String> overTimeAdapter;

    private ArrayList<String> mem_name = new ArrayList<String>(); //名字
    private ArrayList<String> mem_id = new ArrayList<String>(); //名字id
    private ArrayList<String> project_name = new ArrayList<String>(); //项目
    private ArrayList<String> pm_id = new ArrayList<String>(); //项目id
    private static final String[] overtimeStatus = {"全部" ,"确认" ,"取消"}; //状态 2:确认,3:取消，默认为确认

    private Button mSearchBtn;

    private String employerName;
    private String employerId;

    private String projectName;
    private String projectId;

    private String state;
    private int status;

    private String string = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public void onPause() {
        super.onPause();
        mem_name.clear();
        mem_id.clear();
        project_name.clear();
        pm_id.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_my_over_time, container, false);


        init(v);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        if (mFragment.getActivity().getClass() == MyOverTimeActivity.class) {
            mNameLinearLayout.setVisibility(View.GONE);
            mAimFragment = new MyOverTimeFragment();
        } else {
            mAimFragment = new ManagerOvertimeListFragment();
        }

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
                            mEmployerProjectSpinner.setAdapter(projectAdapter);
                            mEmployerProjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    projectName = project_name.get(i);
                                    projectId = pm_id.get(i);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                            nameAdapter= new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, mem_name);
                            nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mEmployerNameSpinner.setAdapter(nameAdapter);
                            mEmployerNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    employerName = mem_name.get(i);
                                    employerId = mem_id.get(i);
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

        mOvertimeEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePicker.selectDateTime(mFragment, mOvertimeEndBtn);
            }
        });

        mOvertimeBeginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePicker.selectDateTime(mFragment, mOvertimeBeginBtn);
            }
        });


        overTimeAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, overtimeStatus);
        overTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOvertimeStatusSpinner.setAdapter(overTimeAdapter);
        mOvertimeStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state = overtimeStatus[i];
                //状态 2:确认,3:取消，默认为确认
                if (state == "全部")
                    status = 0;
                else status = i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();

                if (null != mOvertimeEndBtn.getText()) {
                    bundle.putInt(Link.start_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi3(mOvertimeEndBtn.getText().toString()));
                } else {
                    bundle.putInt(Link.start_time, -1);
                }
                if (null != mOvertimeBeginBtn.getText()) {
                    bundle.putInt(Link.end_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi3(mOvertimeBeginBtn.getText().toString()));
                } else {
                    bundle.putInt(Link.end_time, -1);
                }
                bundle.putInt(Link.status, status);
                bundle.putString(Link.mem_name, employerName);
                bundle.putString(Link.work_pm, projectId);

                mAimFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.blankActivity, mAimFragment);
                transaction.commit();
            }
        });

        mAdd.setText(R.string.add);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ManagerOverTimeAddFragment();
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
        mNameLinearLayout = (LinearLayout) v.findViewById(R.id.fragment_my_overtime_nameLinearLayout);
        mOvertimeBeginBtn = (TextView) v.findViewById(R.id.employer_overtime_begin_time_button);
        mOvertimeEndBtn = (TextView) v.findViewById(R.id.employer_overtime_end_time_button);
        mEmployerProjectSpinner = (Spinner) v.findViewById(R.id.employer_project_spinner);
        mOvertimeStatusSpinner = (Spinner) v.findViewById(R.id.overtime_status_spinner);
        mEmployerNameSpinner = (Spinner) v.findViewById(R.id.employer_name_spinner);

        mSearchBtn = (Button) v.findViewById(R.id.my_overtime_search_button);
        mBack = (TextView) v.findViewById(R.id.overtime_search_back);
        mAdd = (TextView) v.findViewById(R.id.overtime_search_add);
    }

}
