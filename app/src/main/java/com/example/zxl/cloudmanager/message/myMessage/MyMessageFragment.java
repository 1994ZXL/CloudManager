package com.example.zxl.cloudmanager.message.myMessage;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.AutoText;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.Edit;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/7/11.
 */
public class MyMessageFragment extends Fragment {
    private static final String TAG = "MyMessageFragment";

    private TextView mUserName;
    private TextView mName;
    private TextView mSex;
    private EditText mPhoneNumber;
    private EditText mQQ;
    private EditText mWeChat;
    private EditText mEmail;
    private EditText mAddress;
    private EditText mDetailAddress;
    private TextView mIDCard;
    private TextView mServiceState;
    private TextView mCompany;
    private TextView mPunchAddress;
    private TextView mPuncherMaster;
    private TextView mJoinTime;
    private TextView mBack;
    private TextView mEdit;

    private String phoneNumber;
    private String qq;
    private String weChat;
    private String email;
    private String address;
    private String detailAddress;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";
    private RequestParams mParams2 = new RequestParams();
    private JSONObject keyObj2 = new JSONObject();
    private String key2 = "";

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;

    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.main_fragment_my_message, parent, false);

        init(v);
        control();

        return v;
    }

    private void init(View v) {
        mUserName = (TextView)v.findViewById(R.id.main_fragment_my_message_user_name);
        mName = (TextView)v.findViewById(R.id.main_fragment_my_message_name);
        mSex = (TextView)v.findViewById(R.id.main_fragment_my_message_sex);
        mPhoneNumber = (EditText)v.findViewById(R.id.main_fragment_my_message_phone);
        mQQ = (EditText)v.findViewById(R.id.main_fragment_my_message_qq);
        mWeChat = (EditText)v.findViewById(R.id.main_fragment_my_message_wechat);
        mEmail = (EditText) v.findViewById(R.id.main_fragment_my_message_email);
        mAddress = (EditText)v.findViewById(R.id.main_fragment_my_message_location);
        mDetailAddress = (EditText) v.findViewById(R.id.main_fragment_my_message_detail_location);
        mIDCard = (TextView) v.findViewById(R.id.main_fragment_my_message_idcard);
        mServiceState = (TextView) v.findViewById(R.id.main_fragment_my_message_service_state);
        mCompany = (TextView)v.findViewById(R.id.main_fragment_my_message_comp_name);
        mPunchAddress = (TextView)v.findViewById(R.id.main_fragment_my_message_punch_address);
        mPuncherMaster = (TextView)v.findViewById(R.id.main_fragment_my_message_puncher_master);
        mJoinTime = (TextView)v.findViewById(R.id.main_fragment_my_message_join_time);
        mBack = (TextView) v.findViewById(R.id.my_message_back);
        mEdit = (TextView) v.findViewById(R.id.my_message_edit);
    }

    private void control() {
        try {
            keyObj.put(Link.user_id, User.newInstance().getUser_id());
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);
        mHttpc.post(Link.localhost + "member&act=get_list", mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getBoolean("result")) {
                        JSONArray array = response.getJSONArray("data1");
                        Log.d(TAG, "array: " + array);
                        for (int i = 0; i < array.length(); i++) {
                            User.newInstance().setUser(array.getJSONObject(i));
                            Log.d(TAG, "Name: " + User.newInstance().getMem_name());
                        }
                        mUserName.setText(User.newInstance().getUser_name());
                        mName.setText(User.newInstance().getMem_name());
                        mSex.setText(User.newInstance().getMem_sex());
                        mPhoneNumber.setText(User.newInstance().getPhone());
                        mQQ.setText(User.newInstance().getQq());
                        mWeChat.setText(User.newInstance().getWchat());
                        mEmail.setText(User.newInstance().getEmail());
                        mAddress.setText(User.newInstance().getMem_region());
                        mDetailAddress.setText(User.newInstance().getMem_addr());
                        mIDCard.setText(User.newInstance().getCard());
                        mServiceState.setText(User.newInstance().getMem_state());
                        mCompany.setText(User.newInstance().getComp_name());
                        mPunchAddress.setText(User.newInstance().getPuncher_name());
                        mPuncherMaster.setText(User.newInstance().getPuncher_master());
                        mJoinTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(User.newInstance().getJoin_time()));
                    } else {
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                }
            }
        });


        mPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                phoneNumber = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        mQQ.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                qq = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        mWeChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                weChat = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                email = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

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


        mDetailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                detailAddress = charSequence.toString();
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

        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    keyObj2.put(Link.user_id, User.newInstance().getUser_id());
                    if (phoneNumber != null) {
                        keyObj2.put(Link.phone, phoneNumber);
                    }
                    if (qq != null) {
                        keyObj2.put(Link.qq, qq);
                    }
                    if (weChat != null) {
                        keyObj2.put(Link.wchat, weChat);
                    }
                    if (email != null) {
                        keyObj2.put(Link.email, email);
                    }
                    if (address != null) {
                        keyObj2.put(Link.mem_region, address);
                    }
                    if (detailAddress != null) {
                        keyObj2.put(Link.mem_addr, detailAddress);
                    }
                    key2 = DESCryptor.Encryptor(keyObj2.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mParams2.put("key", key2);
                Log.d(TAG,"key2: " + key2);
                mHttpc.post(Link.localhost + "member&act=edit_profile", mParams2, new JsonHttpResponseHandler() {
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
    }
}
