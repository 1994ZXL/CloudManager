package com.example.zxl.cloudmanager.schedule.PMSchedule;

import android.app.Fragment;
import android.app.FragmentTransaction;
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
 * Created by ZXL on 2016/9/12.
 */
public class PMScheduleAddFrament extends Fragment {
    private static final String TAG = "PMScheduleAddFrament";

    private Spinner mTitle;
    private TextView mCreateTime;
    private TextView mSubmitTime;
    private EditText mPercent;
    private TextView mBack;
    private TextView mAdd;

    private ArrayAdapter<String> titleAdapter;

    private ArrayList<String> pmtask_id_list = new ArrayList<String>();
    private String pmtask_id;
    private ArrayList<String> title_list = new ArrayList<String>();

    private String percent;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private static AsyncHttpClient mHttpcAdd = new AsyncHttpClient();
    private RequestParams mParamsAdd = new RequestParams();
    private JSONObject keyObjAdd = new JSONObject();
    private String keyAdd = "";

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pm_schedule_add, container, false);
        savedInstanceState = getArguments();

        init(v);
        control(savedInstanceState);

        return v;
    }

    private void init(View v) {
        mTitle = (Spinner) v.findViewById(R.id.pm_schedule_add_title);
        mCreateTime = (TextView) v.findViewById(R.id.pm_schedule_add_create_time);
        mSubmitTime = (TextView) v.findViewById(R.id.pm_schedule_add_submit_time);
        mPercent = (EditText) v.findViewById(R.id.pm_schedule_add_percent);
        mBack = (TextView) v.findViewById(R.id.pm_schedule_add_back);
        mAdd = (TextView) v.findViewById(R.id.pm_schedule_add_add);
    }

    private void control(final Bundle bundle) {
        try {
            keyObj.put(Link.user_id, User.newInstance().getUser_id());
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);

        mHttpc.post(Link.localhost + "pm_schedule&act=options_title", mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                try {
                    if (rjo.getBoolean("result")) {
                        JSONArray workArray = rjo.getJSONArray("data1");
                        Log.d(TAG, "workArray: " + workArray);
                        for (int i = 0; i < workArray.length(); i++) {
                            if (workArray.getJSONObject(i).has("pmtask_id"))
                                pmtask_id_list.add(workArray.getJSONObject(i).getString("pmtask_id"));
                            if (workArray.getJSONObject(i).has("title"))
                                title_list.add(workArray.getJSONObject(i).getString("title"));
                        }
                        if (null != mFragment.getActivity()){
                            titleAdapter = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, title_list);
                            titleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mTitle.setAdapter(titleAdapter);
                            mTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    pmtask_id = pmtask_id_list.get(i);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }

                    }
                } catch (JSONException e) {
                    Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                }
            }


        });

        mCreateTime.setText(android.text.format.DateFormat.format("yyyy-M-dd", System.currentTimeMillis()).toString());

        mSubmitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDate(mFragment, mSubmitTime);
            }
        });

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

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == percent) {
                    Toast.makeText(getActivity(),
                            "没有填写任务进度哦 ∑(っ °Д °;)っ",
                            Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        keyObjAdd.put(Link.pmtask_id, pmtask_id);
                        if (null != mSubmitTime.getText())
                            keyObjAdd.put(Link.report_time, DateForGeLingWeiZhi.toGeLinWeiZhi(mSubmitTime.getText().toString()));
                        keyObjAdd.put(Link.percent, Integer.valueOf(percent));

                        keyObjAdd.put(Link.user_id, User.newInstance().getUser_id());
                        keyAdd = DESCryptor.Encryptor(keyObjAdd.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mParamsAdd.put("key", keyAdd);
                    Log.d(TAG, "key: " + keyAdd);

                    mHttpcAdd.post(Link.localhost + "pm_schedule&act=add", mParamsAdd, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                            try {
                                Toast.makeText(getActivity(),
                                        rjo.getString("msg"),
                                        Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                            }
                        }
                    });

                    Fragment fragment = new PMScheduleListFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.blankActivity, fragment);
                    transaction.commit();
                }
            }
        });
    }
}
