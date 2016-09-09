package com.example.zxl.cloudmanager.leave.checkManagerLeave;


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
import com.example.zxl.cloudmanager.model.Leave;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.User;
import com.example.zxl.cloudmanager.post.myPost.MyPostFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/7/11.
 */
public class ManagerLeaveDealFragment extends Fragment {
    private TextView name;
    private TextView leaveBeginTime,leaveEndTime,leaveKind,leaveReason,leaveApplyTime;
    private EditText leaveSuggestion;
    private TextView leaveDealTime;
    private Spinner leaveState;

    private TextView mBack;
    private TextView mEdit;

    private static final String EXTRA_OBJECT = "leave";
    private String[] leaveStateList; //状态:1:待批准,2:已批准,3:拒绝
    private ArrayAdapter<String> spinnerAdapter;
    private int status;

    private String suggestion;

    private static Leave mLeave = new Leave();

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private Fragment mFragment;
    private static final String TAG = "MLeaveDeallFragment";

    @Override
    public void onCreate(Bundle saveInstanceState) {
        Log.e(EXTRA_OBJECT,"CM_leave : " + "执行");
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
    }

    public static ManagerLeaveDealFragment newInstance(Object data) {
        mLeave = (Leave) data;
        ManagerLeaveDealFragment fragment = new ManagerLeaveDealFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.cm_leave_deal, parent, false);
        Log.e(EXTRA_OBJECT,"CM_leave : " + "初始化View");

        init(v);
        control();

        return v;
    }

    private void init(View view) {
        Log.e(EXTRA_OBJECT,"CM_leave : " + "初始化控件");
        name = (TextView)view.findViewById(R.id.cm_employer_leave_name);
        leaveKind = (TextView)view.findViewById(R.id.cm_leave_deal_type);
        leaveBeginTime = (TextView)view.findViewById(R.id.cm_leave_deal_begin_time);
        leaveEndTime = (TextView)view.findViewById(R.id.cm_leave_deal_end_time);
        leaveReason = (TextView)view.findViewById(R.id.cm_leave_deal_ask_reason);
        leaveApplyTime = (TextView)view.findViewById(R.id.cm_leave_deal_apply_time);
        leaveSuggestion = (EditText) view.findViewById(R.id.cm_leave_deal_suggestion);
        leaveState = (Spinner) view.findViewById(R.id.cm_leave_deal_state);
        leaveDealTime = (TextView)view.findViewById(R.id.cm_leave_deal_time);

        mBack = (TextView) view.findViewById(R.id.cm_leave_deal_back);
        mEdit = (TextView) view.findViewById(R.id.cm_leave_deal_edit);
    }

    private void control() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    keyObj.put(Link.handle_opinion, suggestion);
                    keyObj.put(Link.status, status);
                    keyObj.put(Link.leave_id, mLeave.getLeave_id());
                    keyObj.put(Link.mem_id, User.newInstance().getUser_id());
                    key = DESCryptor.Encryptor(keyObj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mParams.put("key", key);
                Log.d(TAG,"key:" + key);
                mHttpc.post(Link.localhost + Link.manage_leave + Link.edit, mParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Toast.makeText(getActivity(),
                                    response.getString("msg"),
                                    Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                        }
                    }

                });
            }
        });

        name.setText(mLeave.getMem_name());
        leaveKind.setText(mLeave.getLeave_type());
        leaveBeginTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(mLeave.getStart_time()));
        Log.d(TAG, "beginTime: " + leaveBeginTime.getText());
        leaveEndTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(mLeave.getEnd_time()));
        leaveSuggestion.setText(mLeave.getHandle_opinion());
        leaveSuggestion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                suggestion = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        leaveReason.setText(mLeave.getLeave_reason());
        if ("null" != mLeave.getHandle_time()) {
            Log.d(TAG, "Handle_time1: " + mLeave.getHandle_time());
            leaveDealTime.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(Integer.parseInt(mLeave.getHandle_time()) + 28800));
        } else {
            Log.d(TAG, "Handle_time2: " + mLeave.getHandle_time());
            leaveDealTime.setText("——");
        }

        //状态:1:待批准,2:已批准,3:拒绝
        if (mLeave.getStatus() == "待批准")
            leaveStateList = new String[]{"待批准", "已批准", "拒绝"};
        else if (mLeave.getStatus() == "已批准")
            leaveStateList = new String[]{"已批准", "待批准", "拒绝"};
        else if (mLeave.getStatus() == "拒绝")
            leaveStateList = new String[]{"拒绝", "待批准", "已批准"};
        spinnerAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, leaveStateList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leaveState.setAdapter(spinnerAdapter);
        leaveState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (leaveStateList[i] == "待批准")
                    status = 1;
                else if (leaveStateList[i] == "已批准")
                    status = 2;
                else if (leaveStateList[i] == "拒绝")
                    status = 3;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
