package com.example.zxl.cloudmanager.mission.projectManagerMission;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.DateTimePicker;
import com.example.zxl.cloudmanager.model.Link;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class MissionSearchFragment extends Fragment {
    private static final String TAG = "MissionSearchFragment";

    private EditText mProjectName;
    private EditText mMemName;
    private Button mBeginTimeBeginButton;
    private Button mBeginTimeEndButton;
    private Button mEndTimeBeginButton;
    private Button mEndTimeEndButton;
    private Spinner mStateSpinner;

    private Button mSearchBtn;
    private static final String[] stateList={"待完成", "已完成"};
    private ArrayAdapter<String> stateAdapter;
    private int state;

    private String projecName;
    private String name;

    private static AsyncHttpClient mHttpcProject = new AsyncHttpClient();
    private RequestParams mParamsProject = new RequestParams();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mission_search, container, false);
        init(v);

        mMemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                name = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mProjectName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                projecName = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

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


        mBeginTimeBeginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DateTimePicker.selectDateTime(mFragment, mBeginTimeBeginButton);
            }
        });
        mBeginTimeEndButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DateTimePicker.selectDateTime(mFragment, mBeginTimeEndButton);
            }
        });
        mEndTimeBeginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mEndTimeBeginButton);
            }
        });
        mEndTimeEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mEndTimeEndButton);
            }
        });
        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Fragment fragment = new MissionManagerListFragment();
                Bundle bundle = new Bundle();

                if (null != projecName)
                    bundle.putString(Link.title, projecName);

                if (null != name)
                    bundle.putString(Link.mem_name, name);

                if (null != mBeginTimeBeginButton.getText()) {
                    bundle.putInt(Link.start_time_from, DateForGeLingWeiZhi.toGeLinWeiZhi3(mBeginTimeBeginButton.getText().toString()));
                } else {
                    bundle.putInt(Link.start_time_from, -1);
                }

                if (null != mBeginTimeEndButton.getText()) {
                    bundle.putInt(Link.start_time_to, DateForGeLingWeiZhi.toGeLinWeiZhi3(mBeginTimeEndButton.getText().toString()));
                } else {
                    bundle.putInt(Link.start_time_to, -1);
                }

                if (null != mEndTimeBeginButton.getText()) {
                    bundle.putInt(Link.end_time_from, DateForGeLingWeiZhi.toGeLinWeiZhi3(mEndTimeBeginButton.getText().toString()));
                } else {
                    bundle.putInt(Link.end_time_from, -1);
                }

                if (null != mEndTimeEndButton.getText()) {
                    bundle.putInt(Link.end_time_to, DateForGeLingWeiZhi.toGeLinWeiZhi3(mEndTimeEndButton.getText().toString()));
                } else {
                    bundle.putInt(Link.end_time_to, -1);
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
        mBeginTimeBeginButton = (Button)v.findViewById(R.id.pm_mission_begin_time_button);
        mBeginTimeEndButton = (Button)v.findViewById(R.id.pm_mission_end_time_button);
        mEndTimeBeginButton = (Button) v.findViewById(R.id.pm_mission_end_time_begin_button);
        mEndTimeEndButton = (Button) v.findViewById(R.id.pm_mission_end_time_end_button);
        mProjectName = (EditText) v.findViewById(R.id.pm_mission_projectName);
        mStateSpinner = (Spinner) v.findViewById(R.id.pm_mission_state);
        mMemName = (EditText) v.findViewById(R.id.pm_mission_memName);
        mSearchBtn = (Button) v.findViewById(R.id.pm_mission_search_button);
    }

}
