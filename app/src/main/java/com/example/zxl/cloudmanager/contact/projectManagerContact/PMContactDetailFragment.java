package com.example.zxl.cloudmanager.contact.projectManagerContact;

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

import com.example.zxl.cloudmanager.Edit;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Contact;
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
public class PMContactDetailFragment extends Fragment {
    private static final String TAG = "PMContactDetail";
    private static Contact sContact = new Contact();

    private EditText mContactName;
    private EditText mContactPosition;
    private EditText mContactCompany;
    private TextView mContactProjectName;
    private Spinner mContactProjectNameSpinner;
    private EditText mQQ;
    private EditText mWchat;
    private EditText mPhone;
    private EditText mRemark;
    private TextView mBack;
    private TextView mSearch;
    private TextView mSave;
    private TextView mTitle;

    private String contact_name;
    private String contact_position;
    private String contact_company;
    private String qq;
    private String wchat;
    private String phone;
    private String remark;

    private ArrayAdapter<String> mProjectNameAdapter;
    private ArrayList<String> project_name_list = new ArrayList<String>(); //名字
    private ArrayList<String> project_name_id_list = new ArrayList<String>(); //名字id
    private String project_name_id;

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private static AsyncHttpClient mHttpcAdd = new AsyncHttpClient();
    private RequestParams mParamsAdd = new RequestParams();
    private JSONObject keyObjAdd = new JSONObject();
    private String keyAdd = "";

    private Fragment mFragment;

    public static PMContactDetailFragment newInstance(Object data) {
        sContact = (Contact) data;
        PMContactDetailFragment fragment = new PMContactDetailFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.ps_project_list, parent, false);

        saveInstanceState = getArguments();
        init(v);
        control(saveInstanceState);

        return v;
    }

    private void init(View v) {
        mContactName = (EditText) v.findViewById(R.id.pm_contact_detail_contact_name);
        mContactPosition = (EditText) v.findViewById(R.id.pm_contact_detail_contact_posistion);
        mContactCompany = (EditText) v.findViewById(R.id.pm_contact_detail_contact_company);
        mContactProjectName = (TextView) v.findViewById(R.id.pm_contact_detail_project_name);
        mContactProjectNameSpinner = (Spinner) v.findViewById(R.id.pm_contact_detail_add_project_name);
        mQQ = (EditText) v.findViewById(R.id.pm_contact_detail_qq);
        mWchat = (EditText) v.findViewById(R.id.pm_contact_detail_wechat);
        mPhone = (EditText) v.findViewById(R.id.pm_contact_detail_phone);
        mRemark = (EditText) v.findViewById(R.id.pm_contact_detail_remark);
        mBack = (TextView) v.findViewById(R.id.pm_contact_detail_back);
        mSearch = (TextView) v.findViewById(R.id.pm_contact_detail_search);
        mSave = (TextView) v.findViewById(R.id.pm_contact_details_save);
        mTitle = (TextView) v.findViewById(R.id.pm_contact_detail_title);
    }

    private void control(Bundle bundle) {
        if (bundle == null) {
            contact_name = sContact.getContact_name();
            contact_position = sContact.getContact_position();
            contact_company = sContact.getContact_company();
            qq = sContact.getQq();
            wchat = sContact.getWchat();
            phone = sContact.getPhone();
            remark = sContact.getRemark();
        }

        mContactName.setText(sContact.getContact_name());
        mContactName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contact_name = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mContactPosition.setText(sContact.getContact_position());
        mContactPosition.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contact_position = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mContactCompany.setText(sContact.getContact_company());
        mContactCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contact_company = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mContactProjectName.setText(sContact.getProject_name());

        mQQ.setText(sContact.getQq());
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

        mWchat.setText(sContact.getWchat());
        mWchat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                wchat = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mPhone.setText(sContact.getPhone());
        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                phone = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mRemark.setText(sContact.getRemark());
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

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ContactSearchFragment();
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
            mContactProjectNameSpinner.setVisibility(View.GONE);
            mSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        keyObj.put(Link.contact_name, contact_name);
                        keyObj.put(Link.contact_position, contact_position);
                        keyObj.put(Link.contact_company, contact_company);
                        keyObj.put(Link.phone, phone);
                        keyObj.put(Link.wchat, wchat);
                        keyObj.put(Link.qq, qq);
                        keyObj.put(Link.remark, remark);

                        keyObj.put(Link.pmcon_id, sContact.getPmcon_id());
                        key = DESCryptor.Encryptor(keyObj.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mParams.put("key", key);
                    Log.d(TAG,"key:" + key);
                    mHttpc.post(Link.localhost + "pm_contact&act=edit", mParams, new AsyncHttpResponseHandler() {
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
            mContactProjectName.setVisibility(View.GONE);
            mSave.setText("新增");
            mTitle.setText("项目通讯录新增");

            try {
                keyObjAdd.put(Link.comp_id, User.newInstance().getComp_id());
                keyAdd = DESCryptor.Encryptor(keyObjAdd.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            mParamsAdd.put("key", keyAdd);
            Log.d(TAG, "key: " + keyAdd);

            mHttpcAdd.post(Link.localhost + "pm_contact&act=options_pmcontact", mParamsAdd, new JsonHttpResponseHandler() {
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
                                mContactProjectNameSpinner.setAdapter(mProjectNameAdapter);
                                mContactProjectNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

            mSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        keyObj.put(Link.contact_name, contact_name);
                        keyObj.put(Link.contact_position, contact_position);
                        keyObj.put(Link.contact_company, contact_company);
                        keyObj.put(Link.phone, phone);
                        keyObj.put(Link.wchat, wchat);
                        keyObj.put(Link.qq, qq);
                        keyObj.put(Link.remark, remark);
                        keyObj.put(Link.pm_id, project_name_id);

                        keyObj.put(Link.pmcon_id, sContact.getPmcon_id());
                        key = DESCryptor.Encryptor(keyObj.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mParams.put("key", key);
                    Log.d(TAG,"key:" + key);
                    mHttpc.post(Link.localhost + "pm_contact&act=add", mParams, new AsyncHttpResponseHandler() {
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
        }
    }
}
