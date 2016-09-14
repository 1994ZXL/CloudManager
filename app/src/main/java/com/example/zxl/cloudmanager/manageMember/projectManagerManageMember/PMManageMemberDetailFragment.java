package com.example.zxl.cloudmanager.manageMember.projectManagerManageMember;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.contact.projectManagerContact.PMContactListFragment;
import com.example.zxl.cloudmanager.model.DESCryptor;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.PMMember;
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
 * Created by ZXL on 2016/9/13.
 */
public class PMManageMemberDetailFragment extends Fragment {
    private static final String TAG = "PMManageMemberDetail";
    private static PMMember sPmMember = new PMMember();

    private EditText mMemName;
    private Spinner mMemNameSpinner;
    private EditText mProjectName;
    private Spinner mProjectNameSpinner;
    private EditText mRole;
    private EditText mMemberRes;
    private TextView mBack;
    private TextView mSearch;
    private TextView mTitle;
    private Button mSave;
    private Fragment mFragment;

    private String mem_name;
    private String project_name;
    private String role;
    private String member_res;

    private ArrayAdapter<String> mProjectNameAdapter;
    private ArrayList<String> project_name_list = new ArrayList<String>(); //名字
    private ArrayList<String> project_name_id_list = new ArrayList<String>(); //名字id
    private String project_name_id;

    private ArrayAdapter<String> mMemNameAdapter;
    private ArrayList<String> mem_name_list = new ArrayList<String>(); //名字
    private ArrayList<String> mem_name_id_list = new ArrayList<String>(); //名字id
    private String mem_id;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private static AsyncHttpClient mHttpcAdd = new AsyncHttpClient();
    private RequestParams mParamsAdd = new RequestParams();
    private JSONObject keyObjAdd = new JSONObject();
    private String keyAdd = "";

    private static AsyncHttpClient mHttpcAdd2 = new AsyncHttpClient();
    private RequestParams mParamsAdd2 = new RequestParams();
    private JSONObject keyObjAdd2 = new JSONObject();
    private String keyAdd2 = "";

    public static PMManageMemberDetailFragment newInstance(Object data) {
        sPmMember = (PMMember) data;
        PMManageMemberDetailFragment fragment = new PMManageMemberDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pm_manage_member_detail, container, false);
        savedInstanceState = getArguments();

        init(v);
        control(savedInstanceState);

        return v;
    }

    private void init(View v) {
        mMemName = (EditText) v.findViewById(R.id.pm_manage_member_detail_mem_name);
        mMemNameSpinner = (Spinner) v.findViewById(R.id.pm_manage_member_detail_mem_name_spinner);
        mProjectName = (EditText) v.findViewById(R.id.pm_manage_member_detail_project_name);
        mProjectNameSpinner = (Spinner) v.findViewById(R.id.pm_manage_member_detail_project_name_spinner);
        mRole = (EditText) v.findViewById(R.id.pm_manage_member_detail_role);
        mMemberRes = (EditText) v.findViewById(R.id.pm_manage_member_detail_member_res);
        mBack = (TextView) v.findViewById(R.id.pm_manage_member_detail_back);
        mSearch = (TextView) v.findViewById(R.id.pm_manage_member_detail_search);
        mTitle = (TextView) v.findViewById(R.id.pm_manage_member_detail_title);
        mSave = (Button) v.findViewById(R.id.pm_manage_member_detail_save);
        mem_name = sPmMember.getMem_name();
        project_name = sPmMember.getProject_name();
        role = sPmMember.getRole();
        member_res = sPmMember.getMember_res();
    }

    private void control(Bundle bundle) {
        mMemName.setText(sPmMember.getMem_name());
        mMemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mem_name = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mProjectName.setText(sPmMember.getProject_name());
        mProjectName.addTextChangedListener(new TextWatcher() {
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

        mRole.setText(sPmMember.getRole());
        mRole.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                role = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mMemberRes.setText(sPmMember.getMember_res());
        mMemberRes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                member_res = charSequence.toString();
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

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ManageMemberSearchFragment();
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

        //详情 else 为添加
        if (bundle == null) {
            mMemNameSpinner.setVisibility(View.GONE);
            mProjectNameSpinner.setVisibility(View.GONE);
            mSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        keyObj.put(Link.role, role);
                        keyObj.put(Link.member_res, member_res);

                        keyObj.put(Link.pmmem_id, sPmMember.getPmmem_id());
                        key = DESCryptor.Encryptor(keyObj.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mParams.put("key", key);
                    Log.d(TAG,"key:" + key);
                    mHttpc.post(Link.localhost + "pm_manage_member&act=edit", mParams, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
                    Log.d(TAG, "Url: "+ Link.localhost + "pm_contact&act=edit&key="+key);
                    Fragment fragment = new PMContactListFragment();
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
            mMemName.setVisibility(View.GONE);
            mProjectName.setVisibility(View.GONE);
            mSave.setText("新增");
            mTitle.setText("项目成员录新增");

            try {
                keyObjAdd.put(Link.comp_id, User.newInstance().getComp_id());
                keyAdd = DESCryptor.Encryptor(keyObjAdd.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            mParamsAdd.put("key", keyAdd);
            Log.d(TAG, "key: " + keyAdd);

            mHttpcAdd.post(Link.localhost + "pm_manage_member&act=options_pmmember", mParamsAdd, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                    try {
                        if (rjo.getBoolean("result")) {
                            JSONArray workArray = rjo.getJSONArray("data1");
                            Log.d(TAG, "workArray: " + workArray);

                            for (int i = 0; i < workArray.length(); i++) {
                                if (workArray.getJSONObject(i).has("project_name"))
                                    project_name_list.add(workArray.getJSONObject(i).getString("project_name"));
                                if (workArray.getJSONObject(i).has("pm_id"))
                                    project_name_id_list.add(workArray.getJSONObject(i).getString("pm_id"));
                            }

                            if (null != mFragment.getActivity()){
                                mProjectNameAdapter = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, project_name_list);
                                mProjectNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mProjectNameSpinner.setAdapter(mProjectNameAdapter);
                                mProjectNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        project_name_id = project_name_id_list.get(i);
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

            try {
                keyObjAdd2.put(Link.comp_id, User.newInstance().getComp_id());
                keyAdd2 = DESCryptor.Encryptor(keyObjAdd2.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            mParamsAdd2.put("key", keyAdd2);
            Log.d(TAG, "key: " + keyAdd2);

            mHttpcAdd2.post(Link.localhost + "pm_manage_member&act=options_member", mParamsAdd2, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject rjo) {
                    try {
                        if (rjo.getBoolean("result")) {
                            JSONArray workArray = rjo.getJSONArray("data1");
                            Log.d(TAG, "workArray: " + workArray);

                            for (int i = 0; i < workArray.length(); i++) {
                                if (workArray.getJSONObject(i).has("mem_name"))
                                    mem_name_list.add(workArray.getJSONObject(i).getString("mem_name"));
                                if (workArray.getJSONObject(i).has("mem_id"))
                                    mem_name_id_list.add(workArray.getJSONObject(i).getString("mem_id"));
                            }

                            if (null != mFragment.getActivity()){
                                mMemNameAdapter = new ArrayAdapter<String>(mFragment.getActivity(),android.R.layout.simple_spinner_item, mem_name_list);
                                mMemNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mMemNameSpinner.setAdapter(mMemNameAdapter);
                                mMemNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        mem_id = mem_name_id_list.get(i);
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

            mSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        keyObj.put(Link.pm_id, project_name_id);
                        keyObj.put(Link.role, role);
                        keyObj.put(Link.mem_id, mem_id);
                        keyObj.put(Link.member_res, member_res);

                        key = DESCryptor.Encryptor(keyObj.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mParams.put("key", key);
                    Log.d(TAG,"key:" + key);
                    mHttpc.post(Link.localhost + "pm_manage_member&act=add", mParams, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
                    Fragment fragment = new PMManageMemberListFragment();
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
