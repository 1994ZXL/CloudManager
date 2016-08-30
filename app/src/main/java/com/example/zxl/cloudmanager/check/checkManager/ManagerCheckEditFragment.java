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

    private Date s_att_time;
    private StringBuilder satttime;
    private Date s_time;
    private StringBuilder stime;
    private Date e_att_time;
    private StringBuilder eatttime;
    private Date e_time;
    private StringBuilder etime;

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

        satttime = new StringBuilder(DateForGeLingWeiZhi.fromGeLinWeiZhi2(sCheck.getS_att_time()));
        stime = new StringBuilder(DateForGeLingWeiZhi.fromGeLinWeiZhi2(sCheck.getS_time()));
        eatttime = new StringBuilder(DateForGeLingWeiZhi.fromGeLinWeiZhi2(sCheck.getE_att_time()));
        etime = new StringBuilder(DateForGeLingWeiZhi.fromGeLinWeiZhi2(sCheck.getE_time()));

        mS_att_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 12);
//                fragment.setTargetFragment(ManagerCheckEditFragment.this, 12);
//                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
//                fragment.show(getFragmentManager(), "ManagerCheckEditFragment");
                Calendar calendar = Calendar.getInstance();
                Dialog dateDialog = new DatePickerDialog(mFragment.getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        satttime.setLength(0);
                        satttime.append(i + "年" + (i1 + 1) + "月" + i2 + " ");
                        Calendar time = Calendar.getInstance();
                        Dialog timeDialog = new TimePickerDialog(mFragment.getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                satttime.append(i + ":" + i1);
                                mS_att_time.setText(satttime);
                                Log.d(TAG, "mS_att_time" + satttime);
                            }
                        }, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true);
                        timeDialog.setTitle("请选择时间");
                        timeDialog.show();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dateDialog.setTitle("请选择日期");
                dateDialog.show();
            }
        });

        mS_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 13);
//                fragment.setTargetFragment(ManagerCheckEditFragment.this, 13);
//                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
//                fragment.show(getFragmentManager(), "ManagerCheckEditFragment");
                Calendar calendar = Calendar.getInstance();
                Dialog dateDialog = new DatePickerDialog(mFragment.getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        stime.setLength(0);
                        stime.append(i + "年" + (i1 + 1) + "月" + i2 + " ");
                        Calendar time = Calendar.getInstance();
                        Dialog timeDialog = new TimePickerDialog(mFragment.getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                stime.append(i + ":" + i1);
                                mS_time.setText(stime);
                                Log.d(TAG, "mS_time" + stime);
                            }
                        }, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true);
                        timeDialog.setTitle("请选择时间");
                        timeDialog.show();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dateDialog.setTitle("请选择日期");
                dateDialog.show();
            }
        });

        mE_att_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 14);
//                fragment.setTargetFragment(ManagerCheckEditFragment.this, 13);
//                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
//                fragment.show(getFragmentManager(), "ManagerCheckEditFragment");
                Calendar calendar = Calendar.getInstance();
                Dialog dateDialog = new DatePickerDialog(mFragment.getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        eatttime.setLength(0);
                        eatttime.append(i + "年" + (i1 + 1) + "月" + i2 + " ");
                        Calendar time = Calendar.getInstance();
                        Dialog timeDialog = new TimePickerDialog(mFragment.getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                eatttime.append(i + ":" + i1);
                                mE_att_time.setText(eatttime);
                                Log.d(TAG, "mE_att_time" + eatttime);
                            }
                        }, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true);
                        timeDialog.setTitle("请选择时间");
                        timeDialog.show();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dateDialog.setTitle("请选择日期");
                dateDialog.show();
            }
        });

        mE_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 15);
//                fragment.setTargetFragment(ManagerCheckEditFragment.this, 14);
//                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
//                fragment.show(getFragmentManager(), "ManagerCheckEditFragment");
                Calendar calendar = Calendar.getInstance();
                Dialog dateDialog = new DatePickerDialog(mFragment.getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        etime.setLength(0);
                        etime.append(i + "年" + (i1 + 1) + "月" + i2 + " ");
                        Calendar time = Calendar.getInstance();
                        Dialog timeDialog = new TimePickerDialog(mFragment.getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                etime.append(i + ":" + i1);
                                mE_time.setText(etime);
                                Log.d(TAG, "mE_time" + etime);
                            }
                        }, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true);
                        timeDialog.setTitle("请选择时间");
                        timeDialog.show();
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dateDialog.setTitle("请选择日期");
                dateDialog.show();
            }
        });

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (null != satttime)
                        keyObj.put(Link.S_att_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi3(satttime.toString()));
                    if (null != stime)
                        keyObj.put(Link.S_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi3(stime.toString()));
                    if (null != eatttime)
                        keyObj.put(Link.E_att_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi3(eatttime.toString()));
                    if (null != etime)
                        keyObj.put(Link.E_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi3(etime.toString()));
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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.d(TAG, "进入回调 " + " resultCode:" + requestCode);
//        if (resultCode != Activity.RESULT_OK){
//            Log.d(TAG, "未进入判断");
//            return;
//        } else if (requestCode == 12) {
//            s_att_time = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
//            updateSattDate();
//        } else if (requestCode == 13) {
//            s_time = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
//            updateSDate();
//        } else if (requestCode == 14) {
//            e_att_time = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
//            updateEattDate();
//        } else if (requestCode == 15) {
//            e_time = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
//            updateEDate();
//        }
//    }
//
//    private void updateSattDate(){
//        satttime = android.text.format.DateFormat.format("yyyy年MM月dd", s_att_time).toString();
//        Log.d(TAG, "satttime: " + satttime);
//        mS_att_time.setText(satttime);
//    }
//    private void updateEattDate(){
//        if (e_att_time.after(s_att_time)) {
//            eatttime = android.text.format.DateFormat.format("yyyy年MM月dd", e_att_time).toString();
//            Log.d(TAG, "eatttime: " + eatttime);
//            mE_att_time.setText(eatttime);
//            Log.d(TAG, "mE_att_time: " + mE_att_time.getText());
//        } else {
//            Toast.makeText(getActivity(),
//                    R.string.time_erro,
//                    Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void updateSDate(){
//        stime = android.text.format.DateFormat.format("yyyy年MM月dd", s_time).toString();
//        Log.d(TAG, "stime: " + stime);
//        mS_time.setText(stime);
//    }
//    private void updateEDate(){
//        if (e_time.after(s_time)) {
//            etime = android.text.format.DateFormat.format("yyyy年MM月dd", e_time).toString();
//            Log.d(TAG, "etime: " + etime);
//            mE_time.setText(etime);
//            Log.d(TAG, "mE_time: " + mE_time.getText());
//        } else {
//            Toast.makeText(getActivity(),
//                    R.string.time_erro,
//                    Toast.LENGTH_SHORT).show();
//        }
//    }
}
