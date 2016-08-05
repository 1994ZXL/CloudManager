package com.example.zxl.cloudmanager.checkManager.overtime;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.OverTime;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class OverTimeSearchFragment extends Fragment {

    private static final String TAG = "OverTimeSearchFragment";
    private Button mOvertimeBeginBtn;
    private Button mOvertimeEndBtn;
    private Spinner mEmployerNameSpinner;
    private Spinner mEmployerProjectSpinner;
    private Spinner mOvertimeStatusSpinner;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();

    private Fragment mFragment;

    private ArrayAdapter<String> nameAdapter;
    private ArrayAdapter<String> projectAdapter;
    private ArrayAdapter<String> overTimeAdapter;

    private ArrayList<String> nameList = new ArrayList<String>(); //名字
    private ArrayList<String> name_Id = new ArrayList<String>(); //名字id
    private ArrayList<String> projectList = new ArrayList<String>(); //项目
    private ArrayList<String> project_Id = new ArrayList<String>(); //项目id
    private static final String[] overtimeStatus = {"等待" ,"确认" ,"取消"}; //状态:1:等待,2:确认,3:取消，默认为等待

    private Button mSearchBtn;

    private Date beginTime;
    private String bgtime;

    private Date endTime;
    private String edtime;

    private String employerName;
    private String employerId;

    private String projectName;
    private String projectId;

    private String state;
    private int status;

    private String string = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_my_over_time, container, false);
        getActivity().getActionBar().setTitle("加班查询");

        init(v);

        mHttpc.post(Link.localhost + "manage_work&act=get_options", mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                try {
                    if (rjo.getBoolean("result")) {
                        JSONArray workArray = rjo.getJSONArray("data1");
                        JSONArray memArray = rjo.getJSONArray("data2");
                        Log.d(TAG, "workArray: " + workArray);
                        Log.d(TAG, "memArray: " + memArray);

                        for (int i = 0; i < workArray.length(); i++) {
                            Iterator<String> sIterator = workArray.getJSONObject(i).keys();
                            while (sIterator.hasNext()) {
                                project_Id.add(sIterator.next());
                                projectList.add(workArray.getJSONObject(i).getString(sIterator.next()));
                            }
                        }

                        for (int j = 0; j < memArray.length(); j++) {
                            Iterator<String> sIterator = workArray.getJSONObject(j).keys();
                            while (sIterator.hasNext()) {
                                name_Id.add(sIterator.next());
                                nameList.add(workArray.getJSONObject(j).getString(sIterator.next()));
                            }
                        }

                        Log.d(TAG, "workArray: " + workArray);
                        Log.d(TAG, "memArray: " + memArray);

                    } else {

                    }
                } catch (JSONException e) {
                    Log.e(TAG, "ee2: " + e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {

            }
        });

        mOvertimeEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 12);
                fragment.setTargetFragment(OverTimeSearchFragment.this, 12);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "OverTimeSearchFragment");
            }
        });

        mOvertimeBeginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 13);
                fragment.setTargetFragment(OverTimeSearchFragment.this, 13);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "OverTimeSearchFragment");
            }
        });

        projectAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, projectList);
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEmployerProjectSpinner.setAdapter(projectAdapter);
        mEmployerProjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                projectName = projectList.get(i);
                projectId = project_Id.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        nameAdapter= new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, nameList);
        nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEmployerNameSpinner.setAdapter(nameAdapter);
        mEmployerNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                employerName = nameList.get(i);
                employerId = name_Id.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        overTimeAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, overtimeStatus);
        overTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOvertimeStatusSpinner.setAdapter(overTimeAdapter);
        mOvertimeStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state = overtimeStatus[i];
                //状态:1:等待,2:确认,3:取消，默认为等待
                if (state == "等待")
                    status = 1;
                if (state == "确认")
                    status = 2;
                if (state == "取消")
                    status = 3;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ManagerOvertimeListFragment();
                Bundle bundle = new Bundle();

                if (null != bgtime) {
                    bundle.putInt(Link.start_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(bgtime));
                } else {
                    bundle.putInt(Link.start_time, -1);
                }
                if (null != edtime) {
                    bundle.putInt(Link.end_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(edtime));
                } else {
                    bundle.putInt(Link.end_time, -1);
                }
                bundle.putInt(Link.status, status);
                bundle.putString(Link.mem_id, employerId);
                bundle.putString(Link.work_pm, projectId);

                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.blankActivity, fragment);
                transaction.commit();
            }
        });

        return v;
    }

    private void init(View v){
        mOvertimeBeginBtn = (Button) v.findViewById(R.id.employer_overtime_begin_time_button);
        mOvertimeEndBtn = (Button) v.findViewById(R.id.employer_overtime_end_time_button);
        mEmployerNameSpinner = (Spinner) v.findViewById(R.id.employer_name_spinner);
        mEmployerProjectSpinner = (Spinner) v.findViewById(R.id.employer_project_spinner);
        mOvertimeStatusSpinner = (Spinner) v.findViewById(R.id.overtime_status_spinner);

        mSearchBtn = (Button) v.findViewById(R.id.my_overtime_search_button);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "进入回调 " + " resultCode:" + requestCode);
        if (resultCode != Activity.RESULT_OK){
            Log.d(TAG, "未进入判断");
            return;
        } else if (requestCode == 12) {
            beginTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateBeginDate();
        } else if (requestCode == 13) {
            endTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateEndDate();
        }
    }

    private void updateBeginDate(){
        bgtime = android.text.format.DateFormat.format("yyyy年MM月dd", beginTime).toString();
        Log.d(TAG, "bgtime: " + bgtime);
        mOvertimeBeginBtn.setText(bgtime);
        Log.d(TAG, "beginTimeButton: " + mOvertimeBeginBtn.getText());
    }
    private void updateEndDate(){
        if (endTime.after(beginTime)) {
            edtime = android.text.format.DateFormat.format("yyyy年MM月dd", endTime).toString();
            Log.d(TAG, "edtime: " + edtime);
            mOvertimeEndBtn.setText(edtime);
            Log.d(TAG, "endTimeButton: " + mOvertimeEndBtn.getText());
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }

}
