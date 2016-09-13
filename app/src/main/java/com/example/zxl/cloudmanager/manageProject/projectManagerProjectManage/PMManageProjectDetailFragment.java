package com.example.zxl.cloudmanager.manageProject.projectManagerProjectManage;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
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

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DateTimePicker;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Project;
import com.example.zxl.cloudmanager.model.User;
import com.example.zxl.cloudmanager.schedule.PMSchedule.PMScheduleListFragment;
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
 * Created by ZXL on 2016/7/21.
 */
public class PMManageProjectDetailFragment extends Fragment {
    private static final String TAG = "PMManageProjectDetail";
    private static Project mProject = new Project();

    private EditText mProject_name; //项目名称
    private Spinner mPm_master_name; //项目主管名
    private Spinner mGoon_technical; //技术负责人
    private Spinner mGoon_business; //客服
    private EditText mProject_summary; //项目内容
    private EditText mBelong_unit; //客户单位
    private EditText mCustom_name; //客户联系人
    private EditText mCustom_phone; //客户手机
    private TextView mReady_time; //准备开始时间
    private TextView mGoon_time; //运维开始时间
    private TextView mTodo_time; //开发开始时间
    private TextView mCancel_time; //取消时间
    private TextView mFinshed_time; //结束时间
    private TextView mGoon_actual_start_time; //运维实际开始时间
    private TextView mGoon_actual_end_time; //运维实际结束时间
    private Spinner mProject_state; //项目状态(0:取消，1:准备，2:开发，3:维护，4:结束)
    private TextView mBack;
    private TextView mSave;
    private TextView mTitle;

    private String project_name; //项目名称
    private String pm_master_name_id; //项目主管
    private String goon_technical_id; //技术负责人
    private String goon_business_id; //客服
    private String project_summary; //项目内容
    private String belong_unit; //客户单位
    private String custom_name; //客户联系人
    private String custom_phone; //客户手机
    private int project_state; //项目状态(0:取消，1:准备，2:开发，3:维护，4:结束)

    private ArrayAdapter<String> adapter;
    private static final String[] stateList={"取消", "准备","开发","维护","结束"};

    private static AsyncHttpClient mHttpcMember = new AsyncHttpClient();
    private RequestParams mParamsMember = new RequestParams();
    private JSONObject keyObjMember = new JSONObject();
    private String keyMember = "";

    private ArrayAdapter<String> goonTechnicalAdapter;
    private ArrayList<String> goon_technical_list = new ArrayList<String>(); //名字
    private ArrayList<String> goon_technical_id_list = new ArrayList<String>(); //名字id

    private ArrayAdapter<String> goonBusinessAdapter;

    private ArrayAdapter<String> pmMasterNameAdapter;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private Fragment mFragment;

    public static PMManageProjectDetailFragment newInstance(Object data) {
        mProject = (Project) data;
        PMManageProjectDetailFragment fragment = new PMManageProjectDetailFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.pm_project_edit_or_add, parent, false);

        saveInstanceState = getArguments();
        init(view);
        control(saveInstanceState);

        return view;
    }

    private void init(View v) {
        mProject_name = (EditText) v.findViewById(R.id.pm_project_name); //项目名称
        mPm_master_name = (Spinner) v.findViewById(R.id.pm_pm_master_name); //项目主管名
        mGoon_technical = (Spinner) v.findViewById(R.id.pm_goon_technical); //技术负责人
        mGoon_business = (Spinner) v.findViewById(R.id.pm_goon_business); //客服
        mProject_summary = (EditText) v.findViewById(R.id.pm_project_summary); //项目内容
        mBelong_unit = (EditText) v.findViewById(R.id.pm_belong_unit); //客户单位
        mCustom_name = (EditText) v.findViewById(R.id.pm_custom_name); //客户联系人
        mCustom_phone = (EditText) v.findViewById(R.id.pm_custom_phone); //客户手机
        mReady_time = (TextView) v.findViewById(R.id.pm_ready_time); //准备开始时间
        mGoon_time = (TextView) v.findViewById(R.id.pm_goon_time); //运维开始时间
        mTodo_time = (TextView) v.findViewById(R.id.pm_todo_time); //开发开始时间
        mCancel_time = (TextView) v.findViewById(R.id.pm_cancel_time); //取消时间
        mFinshed_time = (TextView) v.findViewById(R.id.pm_finished_time); //结束时间
        mGoon_actual_start_time = (TextView) v.findViewById(R.id.pm_goon_actual_start_time); //运维实际开始时间
        mGoon_actual_end_time = (TextView) v.findViewById(R.id.pm_goon_actual_end_time); //运维实际结束时间
        mProject_state = (Spinner) v.findViewById(R.id.pm_project_state); //项目状态(0:取消，1:准备，2:开发，3:维护，4:结束)
        mBack = (TextView) v.findViewById(R.id.pm_detail_back);
        mSave = (TextView) v.findViewById(R.id.pm_detail_save);
        mTitle = (TextView) v.findViewById(R.id.pm_detail_title);
        project_name = mProject.getProject_name(); //项目名称
        goon_technical_id = mProject.getGoon_technical(); //技术负责人
        goon_business_id = mProject.getGoon_business(); //客服
        project_summary = mProject.getProject_summary(); //项目内容
        belong_unit = mProject.getBelong_unit(); //客户单位
        custom_name = mProject.getCustom_name(); //客户联系人
        custom_phone = mProject.getCustom_phone(); //客户手机
        if (mProject.getProject_state().equals("取消")) //项目状态(0:取消，1:准备，2:开发，3:维护，4:结束)
            project_state = 0;
        if (mProject.getProject_state().equals("准备"))
            project_state = 1;
        if (mProject.getProject_state().equals("开发"))
            project_state = 2;
        if (mProject.getProject_state().equals("维护"))
            project_state = 3;
        if (mProject.getProject_state().equals("结束"))
            project_state = 4;
    }

    private void control(Bundle bundle) {
        mProject_name.setText(mProject.getProject_name());
        mProject_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                project_name = charSequence.toString();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mProject_summary.setText(mProject.getProject_summary());
        mProject_summary.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                project_summary = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mBelong_unit.setText(mProject.getBelong_unit());
        mBelong_unit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                belong_unit = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mCustom_name.setText(mProject.getCustom_name());
        mCustom_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                custom_name = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mCustom_phone.setText(String.valueOf(mProject.getCustom_phone()));
        mCustom_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                custom_phone = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        try {
            keyObjMember.put(Link.comp_id, User.newInstance().getComp_id());
            keyMember = DESCryptor.Encryptor(keyObjMember.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mParamsMember.put("key", keyMember);
        Log.d(TAG, "key: " + keyMember);

        mHttpcMember.post(Link.localhost + "manage_pm&act=options_member", mParamsMember, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                try {
                    if (rjo.getBoolean("result")) {
                        JSONArray workArray = rjo.getJSONArray("data1");
                        Log.d(TAG, "workArray: " + workArray);

                        for (int i = 0; i < workArray.length(); i++) {
                            if (workArray.getJSONObject(i).has("mem_name"))
                                goon_technical_list.add(workArray.getJSONObject(i).getString("mem_name"));
                            if (workArray.getJSONObject(i).has("mem_id"))
                                goon_technical_id_list.add(workArray.getJSONObject(i).getString("mem_id"));
                        }

                        if (null != mFragment.getActivity()){
                            goonTechnicalAdapter = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, goon_technical_list);
                            goonTechnicalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mGoon_technical.setAdapter(goonTechnicalAdapter);
                            mGoon_technical.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    goon_technical_id = goon_technical_id_list.get(i);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                            goonBusinessAdapter = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, goon_technical_list);
                            goonBusinessAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mGoon_business.setAdapter(goonBusinessAdapter);
                            mGoon_business.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    goon_business_id = goon_technical_id_list.get(i);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                            pmMasterNameAdapter = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, goon_technical_list);
                            pmMasterNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mPm_master_name.setAdapter(pmMasterNameAdapter);
                            mPm_master_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    pm_master_name_id = goon_technical_id_list.get(i);
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

        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, stateList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProject_state.setAdapter(adapter);
        mProject_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                project_state = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (mProject.getReady_time() != 0)
            mReady_time.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(mProject.getReady_time()));
        else mReady_time.setText("--");
        mReady_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDate(mFragment, mReady_time);
            }
        });

        if (mProject.getGoon_time() != 0)
            mGoon_time.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(mProject.getGoon_time()));
        else mGoon_time.setText("--");
        mGoon_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDate(mFragment, mGoon_time);
            }
        });

        if (mProject.getGoon_time() != 0)
            mTodo_time.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(mProject.getTodo_time()));
        else mTodo_time.setText("--");
        mTodo_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDate(mFragment, mTodo_time);
            }
        });

        if (mProject.getCancel_time() != 0)
            mCancel_time.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(mProject.getCancel_time()));
        else mCancel_time.setText("--");
        mCancel_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDate(mFragment, mCancel_time);
            }
        });

        if (mProject.getFinshed_time() != 0)
            mFinshed_time.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(mProject.getFinshed_time()));
        else mFinshed_time.setText("--");
        mFinshed_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDate(mFragment, mFinshed_time);
            }
        });

        if (mProject.getGoon_actual_start_time() != 0)
            mGoon_actual_start_time.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(mProject.getGoon_actual_start_time()));
        else mGoon_actual_start_time.setText("--");
        mGoon_actual_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDate(mFragment, mGoon_actual_start_time);
            }
        });

        if (mProject.getGoon_actual_end_time() != 0)
            mGoon_actual_end_time.setText(DateForGeLingWeiZhi.fromGeLinWeiZhi(mProject.getGoon_actual_end_time()));
        else mGoon_actual_end_time.setText("--");
        mGoon_actual_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDate(mFragment, mGoon_actual_end_time);
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        if (bundle == null) {
            mSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        keyObj.put(Link.project_name, project_name);
                        keyObj.put(Link.pm_master_name, pm_master_name_id);
                        keyObj.put(Link.goon_technical, goon_technical_id);
                        keyObj.put(Link.goon_business, goon_business_id);
                        keyObj.put(Link.project_summary, project_summary);
                        keyObj.put(Link.belong_unit, belong_unit);
                        keyObj.put(Link.custom_name, custom_name);
                        keyObj.put(Link.custom_phone, custom_phone);
                        keyObj.put(Link.ready_time, DateForGeLingWeiZhi.toGeLinWeiZhi(mReady_time.getText().toString()));
                        keyObj.put(Link.goon_time, DateForGeLingWeiZhi.toGeLinWeiZhi(mGoon_time.getText().toString()));
                        keyObj.put(Link.todo_time, DateForGeLingWeiZhi.toGeLinWeiZhi(mTodo_time.getText().toString()));
                        keyObj.put(Link.cancel_time, DateForGeLingWeiZhi.toGeLinWeiZhi(mCancel_time.getText().toString()));
                        keyObj.put(Link.finshed_time, DateForGeLingWeiZhi.toGeLinWeiZhi(mFinshed_time.getText().toString()));
                        keyObj.put(Link.goon_actual_start_time, DateForGeLingWeiZhi.toGeLinWeiZhi(mGoon_actual_start_time.getText().toString()));
                        keyObj.put(Link.goon_actual_end_time, DateForGeLingWeiZhi.toGeLinWeiZhi(mGoon_actual_end_time.getText().toString()));
                        keyObj.put(Link.project_state, project_state);

                        keyObj.put(Link.mem_id, User.newInstance().getUser_id());
                        keyObj.put(Link.pm_id, mProject.getPm_id());
                        key = DESCryptor.Encryptor(keyObj.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mParams.put("key", key);
                    Log.d(TAG,"key:" + key);
                    mHttpc.post(Link.localhost + "manage_pm&act=edit", mParams, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
                    Log.d(TAG, "Url: "+ Link.localhost + "manage_pm&act=edit&key="+key);
                    Fragment fragment = new PMManageProjectListFragment();
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
        } else {
            mTitle.setText("项目新增");
            mSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        keyObj.put(Link.project_name, project_name);
                        keyObj.put(Link.pm_master_name, pm_master_name_id);
                        keyObj.put(Link.goon_technical, goon_technical_id);
                        keyObj.put(Link.goon_business, goon_business_id);
                        keyObj.put(Link.project_summary, project_summary);
                        keyObj.put(Link.belong_unit, belong_unit);
                        keyObj.put(Link.custom_name, custom_name);
                        keyObj.put(Link.custom_phone, custom_phone);
                        keyObj.put(Link.ready_time, DateForGeLingWeiZhi.toGeLinWeiZhi(mReady_time.getText().toString()));
                        keyObj.put(Link.goon_time, DateForGeLingWeiZhi.toGeLinWeiZhi(mGoon_time.getText().toString()));
                        keyObj.put(Link.todo_time, DateForGeLingWeiZhi.toGeLinWeiZhi(mTodo_time.getText().toString()));
                        keyObj.put(Link.cancel_time, DateForGeLingWeiZhi.toGeLinWeiZhi(mCancel_time.getText().toString()));
                        keyObj.put(Link.finshed_time, DateForGeLingWeiZhi.toGeLinWeiZhi(mFinshed_time.getText().toString()));
                        keyObj.put(Link.goon_actual_start_time, DateForGeLingWeiZhi.toGeLinWeiZhi(mGoon_actual_start_time.getText().toString()));
                        keyObj.put(Link.goon_actual_end_time, DateForGeLingWeiZhi.toGeLinWeiZhi(mGoon_actual_end_time.getText().toString()));
                        keyObj.put(Link.project_state, project_state);

                        keyObj.put(Link.comp_id, User.newInstance().getComp_id());
                        key = DESCryptor.Encryptor(keyObj.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mParams.put("key", key);
                    Log.d(TAG,"key:" + key);
                    mHttpc.post(Link.localhost + "manage_pm&act=add", mParams, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
                    Log.d(TAG, "Url: "+ Link.localhost + "manage_pm&act=add&key=" + key);
                    Fragment fragment = new PMManageProjectListFragment();
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
        }
    }
}
