package com.example.zxl.cloudmanager.travel.checkManagerTravel;

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
 * Created by ZXL on 2016/9/6.
 */
public class ManagerTravelAddFragment extends Fragment{
    public static final String TAG = "MTravelAddFragment";

    private Spinner mMemName;
    private TextView mBeginTime;
    private TextView mEndTime;
    private EditText mAddress;
    private EditText mDetailAddr;
    private EditText mReason;

    private TextView mAddTextView;

    private ArrayList<String> memNameList = new ArrayList<String>();
    private ArrayList<String> memIdList = new ArrayList<String>();
    private ArrayAdapter<String> memName;
    private String memname;
    private String memid;

    private String address;
    private String detailaddress;
    private String reason;

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
        View v = inflater.inflate(R.layout.fragment_travel_add, container, false);

        init(v);
        control();

        return v;
    }

    private void init(View v) {
        mMemName = (Spinner) v.findViewById(R.id.fragment_travel_memName);
        mBeginTime = (TextView) v.findViewById(R.id.fragment_travel_beginTime);
        mEndTime = (TextView) v.findViewById(R.id.fragment_travel_endTime);
        mAddress = (EditText) v.findViewById(R.id.fragment_travel_address);
        mDetailAddr = (EditText) v.findViewById(R.id.fragment_travel_detail_addr);
        mReason = (EditText) v.findViewById(R.id.fragment_travel_reason);
        mAddTextView = (TextView) v.findViewById(R.id.fragment_travel_add_add);
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
        mHttpc.post(Link.localhost + "manage_trip&act=get_options", mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == 200) {
                    try {
                        if (response.getBoolean("result")) {
                            JSONArray array = response.getJSONArray("data1");
                            Log.d(TAG, "array: " + array);

                            for (int i = 0; i < array.length(); i++) {
                                if (array.getJSONObject(i).has("mem_name")) {
                                    memNameList.add(array.getJSONObject(i).getString("mem_name"));
                                }

                                if (array.getJSONObject(i).has("mem_id")) {
                                    memIdList.add(array.getJSONObject(i).getString("mem_id"));
                                }
                            }

                            memName = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, memNameList);
                            memName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mMemName.setAdapter(memName);
                            mMemName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    memid = memIdList.get(i);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                    }
                }
            }
        });

        mBeginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDate(mFragment, mBeginTime);
            }
        });

        mEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDate(mFragment, mEndTime);
            }
        });

        mAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                address = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDetailAddr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                detailaddress = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

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

        mAddTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == memid
                        || null == mBeginTime.getText()
                        || null == mEndTime.getText()
                        || null == address
                        || null == detailaddress
                        || null == reason) {
                    Toast.makeText(getActivity(),
                            "填写信息不全哦",
                            Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        keyObjAdd.put(Link.mem_id, memid);
                        keyObjAdd.put(Link.start_time, DateForGeLingWeiZhi.toGeLinWeiZhi(mBeginTime.getText().toString()));
                        keyObjAdd.put(Link.over_time_trip_add, DateForGeLingWeiZhi.toGeLinWeiZhi(mEndTime.getText().toString()));
                        keyObjAdd.put(Link.address, address);
                        keyObjAdd.put(Link.detail_addr, detailaddress);
                        keyObjAdd.put(Link.trip_reason, reason);

                        keyAdd = DESCryptor.Encryptor(keyObjAdd.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mParamsAdd.put("key", keyAdd);
                    Log.d(TAG, "key: " + keyAdd);
                    mHttpcAdd.post(Link.localhost + "manage_trip&act=add", mParamsAdd, new JsonHttpResponseHandler() {
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
