package com.example.zxl.cloudmanager.bug.myBug;

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
public class MyBugAddFragment extends Fragment {
    private static final String TAG = "MyBugAddFragment";

    private Spinner mProjectName;
    private TextView mSubmitter;
    private Spinner mBugLevel;
    private Spinner mBugStatus;
    private EditText mIntoWay;
    private EditText mUseCaseModel;
    private EditText mModular;
    private EditText mRemark;
    private EditText mContent;

    private TextView mAddTextView;
    private TextView mBack;

    private ArrayList<String> projectNameList = new ArrayList<String>();
    private ArrayList<String> projectIdList = new ArrayList<String>();
    private ArrayAdapter<String> projectName;
    private String projectname;
    private String projectid;

    private String[] bugLevelList = new String[]{"全部", "一级", "二级", "三级", "四级", "五级", "六级"};
    private ArrayAdapter<String> bugLevel;
    private int buglevel;

    private String[] bugStatusList = new String[]{"待确认"};
    private ArrayAdapter<String> bugStatus;
    private int bugstatus;

    private String intoWay;
    private String useCaseModel;
    private String modlar;
    private String remark;
    private String content;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.bug_add, container, false);

        init(v);
        control();

        return v;
    }

    private void init(View v) {
        mProjectName = (Spinner) v.findViewById(R.id.bug_add_projectName);
        mSubmitter = (TextView) v.findViewById(R.id.bug_add_submitter);
        mBugLevel = (Spinner) v.findViewById(R.id.bug_add_level);
        mBugStatus = (Spinner) v.findViewById(R.id.bug_add_status);
        mIntoWay = (EditText) v.findViewById(R.id.bug_add_entrance_mode);
        mUseCaseModel = (EditText) v.findViewById(R.id.bug_add_usecase_model);
        mModular = (EditText) v.findViewById(R.id.bug_add_modular);
        mRemark = (EditText) v.findViewById(R.id.bug_add_remark);
        mContent = (EditText) v.findViewById(R.id.bug_add_usecase_content);

        mAddTextView = (TextView) v.findViewById(R.id.bug_add_add);
        mBack = (TextView) v.findViewById(R.id.my_bug_add_back);
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
        mHttpc.post(Link.localhost + "my_bug&act=options_pm", mParams, new JsonHttpResponseHandler() {
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
                                projectName = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, projectNameList);
                                projectName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mProjectName.setAdapter(projectName);
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

        bugLevel = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, bugLevelList);
        bugLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBugLevel.setAdapter(bugLevel);
        mBugLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                buglevel = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bugStatus = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, bugStatusList);
        bugStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBugStatus.setAdapter(bugStatus);
        mBugStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bugstatus = i + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSubmitter.setText(User.newInstance().getMem_name());

        mIntoWay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                intoWay = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mUseCaseModel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                useCaseModel = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mModular.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                modlar = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mRemark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                remark = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                content = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mAddTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == projectid
                        || 0 == buglevel
                        || null == intoWay
                        || null == useCaseModel
                        || null == modlar
                        || null == remark
                        || null == content) {
                    Toast.makeText(getActivity(),
                            "填写信息不全哦",
                            Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        keyObj.put(Link.pm_id, projectid);
                        keyObj.put(Link.level, buglevel);
                        keyObj.put(Link.status, bugstatus);
                        keyObj.put(Link.into_way, intoWay);
                        keyObj.put(Link.case_mode, useCaseModel);
                        keyObj.put(Link.modular, modlar);
                        keyObj.put(Link.remark, remark);
                        keyObj.put(Link.content, content);

                        keyObj.put(Link.user_id, User.newInstance().getUser_id());
                        key = DESCryptor.Encryptor(keyObj.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mParams.put("key", key);
                    Log.d(TAG, "key: " + key);
                    mHttpc.post(Link.localhost + "my_bug&act=add", mParams, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                Toast.makeText(getActivity(),
                                        response.getString("msg"),
                                        Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Fragment fragment = new MyBugFragment();
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
                    });
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
