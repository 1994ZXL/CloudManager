package com.example.zxl.cloudmanager.publicSearch.PSAddressBook;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zxl.cloudmanager.Edit;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.User;

/**
 * Created by ZXL on 2016/7/11.
 */
public class PSListDetailFragment extends Fragment {
    private Spinner sexSpinner;
    private TextView mProjectName;
    private TextView mCustomerName;
    private TextView mCustomerJob;
    private TextView mPhoneNumber;
    private TextView mQQText;
    private TextView mWeChat;

    private static final String[] list={"男", "女"};
    private ArrayAdapter<String> adapter;

    private static final String NAME_CHANGE = "项目修改";
    private static final String CUSTOMER_NAME = "甲方负责人修改";
    private static final String PHONE_CHANGE = "手机修改";
    private static final String QQ_CHANGE = "QQ修改";
    private static final String WECHAT_CHANGE = "微信修改";
    private static final String CUSTOMER_JOB = "职务修改";

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.ps_project_list, parent, false);
        getActivity().getActionBar().setTitle("项目通讯录详情");

        init(v);
        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(adapter);

        /*TODO:项目通讯录模型*/
        mProjectName.setText(User.newInstance(mFragment.getActivity()).getName());
        mProjectName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit fragment = Edit.newInstance(NAME_CHANGE, User.newInstance(mFragment.getActivity()).getName());
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.blankActivity, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mPhoneNumber.setText(User.newInstance(mFragment.getActivity()).getPhone());
        mPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit fragment = Edit.newInstance(PHONE_CHANGE, User.newInstance(mFragment.getActivity()).getPhone());
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.blankActivity, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mQQText.setText(User.newInstance(mFragment.getActivity()).getQq());
        mQQText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit fragment = Edit.newInstance(QQ_CHANGE, User.newInstance(mFragment.getActivity()).getQq());
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.blankActivity, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        mWeChat.setText(User.newInstance(mFragment.getActivity()).getWechat());
        mWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Edit fragment = Edit.newInstance(WECHAT_CHANGE, User.newInstance(mFragment.getActivity()).getWechat());
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.blankActivity, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return v;
    }

    private void init(View v) {
        sexSpinner = (Spinner) v.findViewById(R.id.ps_detail_customer_sex);
        mProjectName = (TextView)v.findViewById(R.id.ps_deatail_project_name);
        mPhoneNumber = (TextView)v.findViewById(R.id.ps_detail_customer_phone);
        mQQText = (TextView)v.findViewById(R.id.ps_detail_cutomer_qq);
        mWeChat = (TextView)v.findViewById(R.id.ps_detail_customer_wechat);
        mCustomerName = (TextView)v.findViewById(R.id.ps_detail_customer_name);
        mCustomerJob = (TextView) v.findViewById(R.id.ps_detail_customer_job);
    }
}
