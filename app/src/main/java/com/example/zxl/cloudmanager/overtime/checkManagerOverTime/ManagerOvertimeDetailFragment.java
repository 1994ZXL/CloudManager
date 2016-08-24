package com.example.zxl.cloudmanager.overtime.checkManagerOverTime;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.OverTime;
import com.example.zxl.cloudmanager.post.myPost.MyPostSearchFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerOvertimeDetailFragment extends Fragment {
    private static final String TAG = "MODetailFragment";

    private TextView mEmployer;
    private TextView mBeginTime;
    private TextView mEndTime;
    private TextView mProjectName;
    private TextView mOvertimeReasonET;
    private Spinner mStatus;

    private static OverTime mOverTime = new OverTime();

    private ArrayAdapter<String> statusAdapter;
    private String[] statusList;
    private int status;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    public static ManagerOvertimeDetailFragment newInstance(Object data) {
        mOverTime = (OverTime) data;
        ManagerOvertimeDetailFragment fragment = new ManagerOvertimeDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_overtime_detail, container, false);

        init(v);
        control();

        return v;
    }

    private void init(View v){
        mBeginTime = (TextView) v.findViewById(R.id.manager_overtime_detail_beginTime);
        mEndTime = (TextView) v.findViewById(R.id.manager_overtime_detail_endTime);
        mEmployer = (TextView) v.findViewById(R.id.manager_employer_name_spinner);
        mProjectName = (TextView) v.findViewById(R.id.manager_overtime_project_spinner);
        mOvertimeReasonET = (TextView) v.findViewById(R.id.manager_overtime_reason_edittext);
        mStatus = (Spinner) v.findViewById(R.id.manager_overtime_status_spinner);

        //状态 1:确认,2:取消，默认为确认
        if (mOverTime.getStatus() == "确认")
            statusList = new String[] {"确认" ,"取消"};
        if (mOverTime.getStatus() == "取消")
            statusList = new String[] {"取消" ,"确认"};

    }

    private void control() {
        mBeginTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(mOverTime.getStart_time()));
        mEndTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(mOverTime.getEnd_time()));
        mEmployer.setText(mOverTime.getMem_name());
        mProjectName.setText(mOverTime.getWork_pm());
        mOvertimeReasonET.setText(mOverTime.getWork_resaon());

        statusAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, statusList);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStatus.setAdapter(statusAdapter);
        mStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (statusList[i] == "确认")
                    status = 2;
                if (statusList[i] == "取消")
                    status = 3;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.message_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_edit_message:
                try {
                    keyObj.put(Link.work_id, mOverTime.getWork_id());
                    keyObj.put(Link.status, status);
                    key = DESCryptor.Encryptor(keyObj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mParams.put("key", key);
                Log.d(TAG,"key:" + key);
                mHttpc.post(Link.localhost + Link.manage_work + Link.edit, mParams, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    }
                });

                FragmentManager fm = getFragmentManager();
                fm.popBackStack();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
