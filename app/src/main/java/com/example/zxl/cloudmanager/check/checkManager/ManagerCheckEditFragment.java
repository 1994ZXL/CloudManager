package com.example.zxl.cloudmanager.check.checkManager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.zxl.cloudmanager.Edit;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Check;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.DateTimePicker;
import com.example.zxl.cloudmanager.model.Link;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ZXL on 2016/7/15.
 */
public class ManagerCheckEditFragment extends Fragment{
    private static final String TAG = "MCEdtiFragment";

    private static Check sCheck;

    private Button mS_att_time;
    private Button mS_time;
    private Button mE_att_time;
    private Button mE_time;
    private Button mSaveBtn;

    private StringBuilder str;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mFragment = this;
    }

    public static ManagerCheckEditFragment newInstance(Check check) {
        sCheck = check;
        ManagerCheckEditFragment fragment = new ManagerCheckEditFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.main_fragment_manager_check_edit, parent, false);

        mS_time = (Button) view.findViewById(R.id.main_fragment_manager_check_edit_stipulate_dutytime);
        mS_att_time = (Button) view.findViewById(R.id.main_fragment_manager_check_edit_duty_sign_time);
        mE_time = (Button) view.findViewById(R.id.main_fragment_manager_check_edit_stipulate_offdutytime);
        mE_att_time = (Button) view.findViewById(R.id.main_fragment_manager_check_edit_offduty_sign_time);
        mSaveBtn = (Button) view.findViewById(R.id.main_fragment_manager_check_edit_save_edit_button);

        if (sCheck.getS_time() != 0){
            mS_time.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(sCheck.getS_time()+28800));
        } else {
            mS_time.setText("——");
        }

        if (sCheck.getE_time() != 0) {
            mE_time.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(sCheck.getE_time()+28800));
        } else {
            mE_time.setText("——");
        }

        if (sCheck.getS_att_time() == 0) {
            mS_att_time.setText("——");
        }else {
            mS_att_time.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(sCheck.getS_att_time()+28800));
        }

        if (sCheck.getE_att_time() != 0) {
            mE_att_time.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi2(sCheck.getE_att_time()+28800));
        } else {
            mE_att_time.setText("——");
        }


        mS_att_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mS_att_time);
            }
        });

        mS_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mS_time);
            }
        });

        mE_att_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mE_att_time);
            }
        });

        mE_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mE_time);
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (null != mS_att_time.getText())
                        keyObj.put(Link.S_att_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi3(mS_att_time.getText().toString()));
                    else keyObj.put(Link.S_att_time, sCheck.getS_att_time());

                    if (null != mS_time.getText())
                        keyObj.put(Link.S_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi3(mS_time.getText().toString()));
                    else keyObj.put(Link.S_time, sCheck.getS_att_time());

                    if (null != mE_att_time.getText())
                        keyObj.put(Link.E_att_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi3(mE_att_time.toString()));
                    else keyObj.put(Link.E_att_time, sCheck.getS_att_time());

                    if (null != mE_time.getText())
                        keyObj.put(Link.E_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi3(mE_time.toString()));
                    else keyObj.put(Link.E_time, sCheck.getS_att_time());

                    keyObj.put(Link.att_id, sCheck.getAtt_id());
                    key = DESCryptor.Encryptor(keyObj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mParams.put("key", key);
                Log.d(TAG,"key:" + key);
                mHttpc.post(Link.localhost + "manage_punch&act=edit", mParams, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
                Fragment fragment = new ManagerCheckListFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (!fragment.isAdded()) {
                    transaction.hide(mFragment);
                    transaction.replace(R.id.blankActivity, fragment);
                    transaction.commit();
                } else {
                    transaction.hide(mFragment);
                    transaction.show(fragment);
                    transaction.commit();
                }
            }
        });

        return view;
    }
}
