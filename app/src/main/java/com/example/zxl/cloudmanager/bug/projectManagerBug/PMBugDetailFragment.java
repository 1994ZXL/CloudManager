package com.example.zxl.cloudmanager.bug.projectManagerBug;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.bug.myBug.MyBugFragment;
import com.example.zxl.cloudmanager.check.checkManager.ManagerCheckEditFragment;
import com.example.zxl.cloudmanager.model.Bug;
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
 * Created by ZXL on 2016/7/22.
 */
public class PMBugDetailFragment extends Fragment {
    private static final String TAG = "PMBugDetail";
    private static Bug sBug = new Bug();

    private Spinner mProjectName;
    private TextView mLevel;
    private Spinner mState;
    private TextView mPri;
    private TextView mSubmitter;
    private Spinner mModifier;
    private Spinner mUseCaseModel;
    private TextView mBugContent;
    private TextView mUseCaseNumber;
    private TextView mIntoWay;
    private TextView mModifyTime;
    private TextView mSubmitTime;
    private TextView mRemark;
    private TextView mBack;
    private TextView mEdit;
    private Fragment mFragment;

    private ArrayList<String> nameList1 = new ArrayList<String>();
    private ArrayList<String> idList1 = new ArrayList<String>();
    private ArrayList<String> projectNameList = new ArrayList<String>();
    private ArrayList<String> projectIdList = new ArrayList<String>();
    private ArrayAdapter<String> projectNameAdapter;
    private String projectid;

    private ArrayList<String> nameList2 = new ArrayList<String>();
    private ArrayList<String> idList2 = new ArrayList<String>();
    private ArrayList<String> modifierNameList = new ArrayList<String>();
    private ArrayList<String> modifierIdList = new ArrayList<String>();
    private ArrayAdapter<String> modifierAdapter;
    private String modifierid;

    private String[] bugStatusList = new String[4];
    private ArrayAdapter<String> bugStatusAdapter;
    private int bugstatus;

    private String[] useCaseModelList = new String[]{"全部"};
    private ArrayAdapter<String> useCaseAdapter;
    private int usecasemodel;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private static AsyncHttpClient mHttpc2 = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private static AsyncHttpClient mHttpcEdit = new AsyncHttpClient();
    private RequestParams mParamsEdit = new RequestParams();
    private JSONObject keyObjEdit = new JSONObject();
    private String keyEdit = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    public static PMBugDetailFragment newInstance(Object data) {
        sBug = (Bug) data;
        PMBugDetailFragment myBugDetailFragment = new PMBugDetailFragment();
        return myBugDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.pm_bug_details, parent, false);
        saveInstanceState = getArguments();
        init(view);
        contorl(saveInstanceState);
        return view;
    }

    private void init(View view) {
        mProjectName = (Spinner) view.findViewById(R.id.pm_bug_details_project_name);
        mLevel = (TextView) view.findViewById(R.id.pm_bug_details_level);
        mState = (Spinner) view.findViewById(R.id.pm_bug_details_state);
        mPri = (TextView) view.findViewById(R.id.pm_bug_details_pri);
        mSubmitter = (TextView) view.findViewById(R.id.pm_bug_details_found_man);
        mModifier = (Spinner) view.findViewById(R.id.pm_bug_details_edit_man);
        mUseCaseModel = (Spinner) view.findViewById(R.id.pm_bug_details_usecase_model);
        mBugContent = (TextView) view.findViewById(R.id.pm_bug_details_content);
        mUseCaseNumber = (TextView) view.findViewById(R.id.pm_bug_details_usecase_number);
        mIntoWay = (TextView) view.findViewById(R.id.pm_bug_details_entrance_mode);
        mModifyTime = (TextView) view.findViewById(R.id.pm_bug_details_edit_time);
        mSubmitTime = (TextView) view.findViewById(R.id.pm_bug_details_found_time);
        mRemark = (TextView) view.findViewById(R.id.pm_bug_details_remark);
        mBack = (TextView) view.findViewById(R.id.pm_bug_details_back);
        mEdit = (TextView) view.findViewById(R.id.pm_bug_details_edit);
        if (sBug.getStatus() == "待确认")
            bugStatusList = new String[]{"待确认", "已排除", "不解决", "待修改"};
        if (sBug.getStatus() == "已排除")
            bugStatusList = new String[]{"已排除", "待确认", "不解决", "待修改"};
        if (sBug.getStatus() == "不解决")
            bugStatusList = new String[]{"不解决", "待确认", "已排除", "待修改"};
        if (sBug.getStatus() == "待修改")
            bugStatusList = new String[]{"待修改", "待确认", "已排除", "不解决"};
    }

    private void contorl(final Bundle saveInstanceState) {
        try {
            keyObj.put(Link.user_id, User.newInstance().getUser_id());
            key = DESCryptor.Encryptor(keyObj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParams.put("key", key);
        Log.d(TAG, "key: " + key);
        mHttpc.post(Link.localhost + "pm_bug&act=options_pm", mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == 200) {
                    try {
                        if (response.getBoolean("result")) {
                            JSONArray array = response.getJSONArray("data1");
                            Log.d(TAG, "array: " + array);

                            for (int i = 0; i < array.length(); i++) {
                                if (array.getJSONObject(i).has("project_name")) {
                                    nameList1.add(array.getJSONObject(i).getString("project_name"));
                                }

                                if (array.getJSONObject(i).has("pm_id")) {
                                    idList1.add(array.getJSONObject(i).getString("pm_id"));
                                }
                            }

                            if (null != mFragment.getActivity()) {
                                projectNameList = new ArrayList<String>(nameList1);
                                projectIdList = new ArrayList<String>(idList1);

                                if (!"null".equals(sBug.getProject_name())) {
                                    Log.d(TAG,"存在ProjectName");
                                    int index = projectNameList.indexOf(sBug.getProject_name());
                                    projectNameList.remove(index);
                                    projectNameList.add(0, sBug.getProject_name());
                                    projectIdList.remove(index);
                                    projectIdList.add(0, idList1.get(index));
                                }

                                projectNameAdapter = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, projectNameList);
                                projectNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mProjectName.setAdapter(projectNameAdapter);
                                mProjectName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        projectid = projectIdList.get(i);
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
            }
        });
        mHttpc2.post(Link.localhost + "pm_bug&act=options_member", mParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (statusCode == 200) {
                    try {
                        if (response.getBoolean("result")) {
                            JSONArray array = response.getJSONArray("data1");
                            Log.d(TAG, "array: " + array);

                            for (int i = 0; i < array.length(); i++) {
                                if (array.getJSONObject(i).has("mem_name")) {
                                    nameList2.add(array.getJSONObject(i).getString("mem_name"));
                                }

                                if (array.getJSONObject(i).has("mem_id")) {
                                    idList2.add(array.getJSONObject(i).getString("mem_id"));
                                }
                            }

                            if (null != mFragment.getActivity()) {
                                modifierNameList = new ArrayList<String>(nameList2);
                                modifierIdList = new ArrayList<String>(idList2);

                                if (!"null".equals(sBug.getModifier())) {
                                    Log.d(TAG,"存在Modifier");
                                    int index = modifierNameList.indexOf(sBug.getModifier());
                                    modifierNameList.remove(index);
                                    modifierNameList.add(0, sBug.getProject_name());
                                    modifierIdList.remove(index);
                                    modifierIdList.add(0, idList2.get(index));
                                }

                                modifierAdapter = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, modifierNameList);
                                modifierAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mModifier.setAdapter(modifierAdapter);
                                mModifier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        modifierid = modifierIdList.get(i);
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
            }
        });
        bugStatusAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, bugStatusList);
        bugStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mState.setAdapter(bugStatusAdapter);
        mState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bugstatus = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        useCaseAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, useCaseModelList);
        useCaseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mUseCaseModel.setAdapter(useCaseAdapter);
        mUseCaseModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                usecasemodel = i + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mLevel.setText(sBug.getLevel());
        mPri.setText(sBug.getPri());
        mSubmitter.setText(sBug.getSubmitter());
        mIntoWay.setText(sBug.getInto_way());

        if (sBug.getSubmit_time() != 0)
            mSubmitTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(sBug.getSubmit_time()));
        else mSubmitTime.setText("--");

        if (sBug.getModify_time() != 0)
            mModifyTime.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(sBug.getModify_time()));
        else mModifyTime.setText("--");
        mModifyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDate(mFragment, mModifyTime);
            }
        });

        mRemark.setText(sBug.getRemark());
        mBugContent.setText(sBug.getContent());
        mUseCaseNumber.setText(sBug.getCase_mode());
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
                    keyObjEdit.put(Link.pm_id, projectid);
                    keyObjEdit.put(Link.status, bugstatus);
                    keyObjEdit.put(Link.case_mode, usecasemodel);
                    if (!mModifyTime.getText().toString().equals("--"))
                        keyObjEdit.put(Link.modify_time, DateForGeLingWeiZhi.toGeLinWeiZhi(mModifyTime.getText().toString()));
                    keyObjEdit.put(Link.modifier, modifierid);
                    keyObjEdit.put(Link.pmbug_id, sBug.getPmbug_id());
                    keyEdit = DESCryptor.Encryptor(keyObjEdit.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mParamsEdit.put("key", keyEdit);
                Log.d(TAG, "key: " + keyEdit);
                mHttpcEdit.post(Link.localhost + "pm_bug&act=edit", mParamsEdit, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        if (statusCode == 200) {
                            try {
                                if (response.getBoolean("result")) {
                                    JSONArray array = response.getJSONArray("data1");
                                    Log.d(TAG, "array: " + array);

                                    for (int i = 0; i < array.length(); i++) {
                                        if (array.getJSONObject(i).has("project_name")) {
                                            projectNameList.add(array.getJSONObject(i).getString("project_name"));
                                        }

                                        if (array.getJSONObject(i).has("pm_id")) {
                                            projectIdList.add(array.getJSONObject(i).getString("pm_id"));
                                        }
                                    }

                                    if (null != mFragment.getActivity()) {
                                        projectNameAdapter = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, projectNameList);
                                        projectNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        mProjectName.setAdapter(projectNameAdapter);
                                        mProjectName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                projectid = projectIdList.get(i);
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
                            Fragment fragment = new MyBugFragment();

                            if (null != saveInstanceState) {
                                Bundle bundle = new Bundle(saveInstanceState);
                                fragment.setArguments(bundle);
                            }

                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            if (!fragment.isAdded()) {
                                transaction.addToBackStack(null);
                                transaction.hide(mFragment);
                                transaction.replace(R.id.blankActivity, fragment);
                                transaction.commit();
                            } else {
                                transaction.hide(mFragment);
                                transaction.show(fragment);
                                transaction.commit();
                            }
                        }
                    }
                });
            }
        });
    }
}
