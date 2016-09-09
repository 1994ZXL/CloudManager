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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.zxl.cloudmanager.Edit;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.DateTimePicker;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Mission;
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
 * Created by ZXL on 2016/7/21.
 */
public class MissionManagerEditFragment extends Fragment {
    private static final String TAG = "PMMissionEditFragment";

    private EditText mTitle;
    private EditText mContent;
    private TextView mBeginTimeButton;
    private TextView mEndTimeButton;
    private EditText mEvaluate;
    private Spinner mState;
    private EditText mPercent;

    private TextView mEdit;
    private TextView mBack;

    private Fragment mFragment;

    private static Mission sMission = new Mission();

    private String[] stateList;
    private ArrayAdapter<String> stateAdapter;

    private String title;
    private String content;
    private String evaluate;
    private String percent;
    private int state;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.pm_mission_edit_or_add, parent, false);

        init(view);
        contorl();

        return view;
    }

    public static MissionManagerEditFragment newInstance(Object data) {
        sMission = (Mission) data;
        MissionManagerEditFragment fragment = new MissionManagerEditFragment();
        return fragment;
    }

    private void init(View view) {
        mTitle = (EditText) view.findViewById(R.id.pm_mission_name);
        mContent = (EditText) view.findViewById(R.id.pm_mission_content);
        mBeginTimeButton = (TextView) view.findViewById(R.id.pm_mission_begin_time);
        mEndTimeButton = (TextView) view.findViewById(R.id.pm_mission_end_time);
        mEvaluate = (EditText) view.findViewById(R.id.pm_mission_evaluate);
        mState = (Spinner) view.findViewById(R.id.pm_mission_state);
        mPercent = (EditText) view.findViewById(R.id.pm_mission_progress);
        mEdit = (TextView) view.findViewById(R.id.pm_mission_edit_or_add_edit);
        mBack = (TextView) view.findViewById(R.id.pm_mission_edit_back);
    }

    private void contorl() {
        mTitle.setText(sMission.getTitle());
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                title = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mContent.setText(sMission.getContent());
        mContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                content = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (sMission.getStart_time() == 0) {
            mBeginTimeButton.setText("——");
        } else {
            mBeginTimeButton.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi2(sMission.getStart_time()));
        }
        mBeginTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mBeginTimeButton);
            }
        });

        if (sMission.getEnd_time() == 0) {
            mEndTimeButton.setText("——");
        } else {
            mEndTimeButton.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi2(sMission.getEnd_time()));
        }
        mEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mEndTimeButton);
            }
        });

        mEvaluate.setText(sMission.getEvaluate());
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

        if (sMission.getStatus().equals("待完成")) {
            stateList = new String[]{"待完成", "已完成"};
        } else {
            stateList = new String[]{"已完成", "待完成"};
        }
        stateAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mState.setAdapter(stateAdapter);
        mState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        if (sMission.getPercent() == null)
            mPercent.setText("0");
        else mPercent.setText(sMission.getPercent());
        mPercent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                percent = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (null != title)
                        keyObj.put(Link.title, title);
                    else keyObj.put(Link.title, sMission.getTitle());

                    if (null != content)
                        keyObj.put(Link.content, content);
                    else keyObj.put(Link.content, sMission.getContent());

                    if (null != mBeginTimeButton.getText())
                        keyObj.put(Link.start_time, DateForGeLingWeiZhi.toGeLinWeiZhi3(mBeginTimeButton.getText().toString()));
                    else keyObj.put(Link.start_time, DateForGeLingWeiZhi.fromGeLinWeiZhi2(sMission.getStart_time()));

                    if (null != mEndTimeButton.getText())
                        keyObj.put(Link.over_time, DateForGeLingWeiZhi.toGeLinWeiZhi3(mEndTimeButton.getText().toString()));
                    else keyObj.put(Link.over_time, DateForGeLingWeiZhi.fromGeLinWeiZhi2(sMission.getEnd_time()));

                    if (null != evaluate)
                        keyObj.put(Link.evaluate, evaluate);
                    else keyObj.put(Link.evaluate, sMission.getEvaluate());

                    if (null != percent)
                        keyObj.put(Link.percent, percent);
                    else keyObj.put(Link.percent, sMission.getPercent());

                    keyObj.put(Link.status, state);

                    keyObj.put(Link.pmtask_id, sMission.getPmtask_id());
                    keyObj.put(Link.mem_id, sMission.getMem_id());
                    keyObj.put(Link.pmsch_id, sMission.getPmsch_id());

                    key = DESCryptor.Encryptor(keyObj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mParams.put("key", key);
                Log.d(TAG,"key:" + key);

                mHttpc.post(Link.localhost + "pm_task&act=edit", mParams, new JsonHttpResponseHandler() {
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
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });
    }
}
