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
import android.widget.EditText;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DateTimePicker;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Schedule;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * Created by ZXL on 2016/9/5.
 */
public class PMScheduleEditFragment extends Fragment {
    private static final String TAG = "PMSchEditFragment";
    private static Schedule sSchedule = new Schedule();

    private TextView mTitle;
    private TextView mReportTime;
    private TextView mPmscheduleTime;
    private EditText mPercentEdit;
    private TextView mEdit;
    private TextView mBack;

    private String percent;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private Fragment mFragment;

    public static PMScheduleEditFragment newInstance(Object date) {
        sSchedule = (Schedule) date;
        PMScheduleEditFragment pmScheduleEditFragment = new PMScheduleEditFragment();
        return pmScheduleEditFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pm_schedule_edit, container, false);

        init(v);
        control();

        return v;
    }

    private void init(View v) {
        mTitle = (TextView) v.findViewById(R.id.pm_schedule_name);
        mReportTime = (TextView) v.findViewById(R.id.pm_schedule_submit_time);
        mPmscheduleTime = (TextView) v.findViewById(R.id.pm_schedule_pmsch_time);
        mPercentEdit = (EditText) v.findViewById(R.id.pm_schedule_schedule);
        mEdit = (TextView) v.findViewById(R.id.pm_schedule_edit);
        mBack = (TextView) v.findViewById(R.id.pm_schedule_edit_back);

    }

    private void control() {
        mTitle.setText(sSchedule.getTitle());
        mPmscheduleTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi2(sSchedule.getPmsch_time()));
        mReportTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi2(sSchedule.getReport_time()));
        mReportTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mReportTime);
            }
        });

        mPercentEdit.setText(String.valueOf(sSchedule.getPercent()));
        mPercentEdit.addTextChangedListener(new TextWatcher() {
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
                    if (null != mReportTime.getText())
                        keyObj.put(Link.report_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi3(mReportTime.getText().toString()));
                    else keyObj.put(Link.report_time, sSchedule.getReport_time());

                    keyObj.put(Link.percent, Integer.parseInt(percent));

                    keyObj.put(Link.pmsch_id, sSchedule.getPmsch_id());
                    key = DESCryptor.Encryptor(keyObj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mParams.put("key", key);
                Log.d(TAG,"key:" + key);
                mHttpc.post(Link.localhost + "pm_schedule&act=edit", mParams, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
                Fragment fragment = new PMScheduleListFragment();
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

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });
    }
}
