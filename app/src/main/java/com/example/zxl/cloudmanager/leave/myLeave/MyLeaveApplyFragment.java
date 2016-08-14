package com.example.zxl.cloudmanager.leave.myLeave;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.Leave;
import com.example.zxl.cloudmanager.model.LeaveMyLab;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/7/12.
 */
public class MyLeaveApplyFragment extends Fragment {
    private Leave leave = new Leave();

    private ArrayAdapter<String> adapter;
    private static final String[] list={"事假", "病假", "休假", "婚假", "其他"};
    private int type;

    private Spinner mTypeSpinner;
    private Button mBeginTime;
    private Button mEndTime;
    private EditText mReson;

    private Date beginTime;
    private String bgtime;
    private Date endTime;
    private String edtime;
    private String reson;

    private Button mCommitBtn;

    private static final String TAG = "MyLeaveApplyFragment";
    private static final String ARRAY = "array";

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        mFragment = this;
    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.main_fragment_my_leave_apply, parent, false);

        initVariable(view);

        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeSpinner.setAdapter(adapter);
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //请假类型:1事假,2病假,3休假,4婚假,5其他
                if (list[i].equals("事假")){
                    type = 1;
                } else if (list[i].equals("病假")){
                    type = 2;
                } else if (list[i].equals("休假")){
                    type = 3;
                } else if (list[i].equals("婚假")){
                    type = 4;
                } else if (list[i].equals("其他")){
                    type = 5;
                }

                adapterView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mBeginTime.setEnabled(true);//设置按钮可点
        mBeginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 12);
                fragment.setTargetFragment(MyLeaveApplyFragment.this, 12);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyLeaveApplyFragment");
            }
        });


        mEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 13);
                fragment.setTargetFragment(MyLeaveApplyFragment.this, 13);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyLeaveApplyFragment");
            }
        });

        mReson.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                reson = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mCommitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit();
            }
        });

        return view;
    }

    public void initVariable(View view) {
        mTypeSpinner = (Spinner)view.findViewById(R.id.main_fragment_my_leave_typeSprinner);
        mBeginTime = (Button)view.findViewById(R.id.main_fragment_my_leave_begin_time);
        mEndTime = (Button)view.findViewById(R.id.main_fragment_my_leave_end_time);
        mCommitBtn = (Button)view.findViewById(R.id.main_fragment_my_leave_apply_commitButton);
        mReson = (EditText)view.findViewById(R.id.main_fragment_my_leave_resonEditText);
    }

    public void commit() {

        try {
            keyObj.put(Link.mem_id, User.newInstance().getUser_id());
            keyObj.put(Link.leave_type, type);
            keyObj.put(Link.start_time, DateForGeLingWeiZhi.toGeLinWeiZhi(bgtime));
            keyObj.put(Link.end_time, DateForGeLingWeiZhi.toGeLinWeiZhi(edtime));
            keyObj.put(Link.leave_reson, reson);
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG,"key:" + key);
        mHttpc.post(Link.localhost + "my_leave&act=add", mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == 200) {
                    try {
                        if (response.getBoolean("result")) {
                            Intent intent = new Intent(mFragment.getActivity(), MyLeaveActivity.class);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                    }
                } else if (statusCode == 400) {
                    try {
                        Toast.makeText(getActivity(),
                                response.getString("msg"),
                                Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                    }
                }

            }

        });

        Intent intent = new Intent(mFragment.getActivity(), MyLeaveActivity.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "进入回调，未进入判断 " + " resultCode:" + requestCode);
        if (resultCode != Activity.RESULT_OK){
            Log.d(TAG, "进入回调");
            return;
        }else if (requestCode == 12) {
            beginTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            bgtime = android.text.format.DateFormat.format("yyyy年M月dd日", beginTime).toString();
            leave.setStart_time(DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(bgtime));
            updateBeginDate();
        }else if (requestCode == 13) {
            endTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            edtime = android.text.format.DateFormat.format("yyyy年M月dd日", endTime).toString();
            leave.setEnd_time(DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(edtime));
            updateEndDate();
        }
    }

    private void updateBeginDate(){
        mBeginTime.setText(bgtime);
    }
    private void updateEndDate(){
        if (endTime.after(beginTime)) {
            mEndTime.setText(edtime);
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
