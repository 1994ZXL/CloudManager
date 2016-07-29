package com.example.zxl.cloudmanager.publicSearch.list;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;

/**
 * Created by ZXL on 2016/7/11.
 */
public class PSListDetailFragment extends Fragment {
    private TextView sexSpinner;
    private TextView mProjectName;
    private TextView mCustomerName;
    private TextView mCustomerJob;
    private TextView mPhoneNumber;
    private TextView mQQText;
    private TextView mWeChat;

    private static final String[] list={"男", "女"};
    private ArrayAdapter<String> adapter;

    /*private static final String NAME_CHANGE = "名字修改";
    private static final String PHONE_CHANGE = "手机修改";
    private static final String QQ_CHANGE = "QQ修改";
    private static final String WECHAT_CHANGE = "微信修改";
    private static final String ADDRESS_CHANGE = "地址修改";*/

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
        getActivity().getActionBar().setTitle("我的信息");

        init(v);
        return v;
    }

    private void init(View v) {
        sexSpinner = (TextView)v.findViewById(R.id.ps_detail_customer_sex);
        mProjectName = (TextView)v.findViewById(R.id.ps_deatail_project_name);
        mPhoneNumber = (TextView)v.findViewById(R.id.ps_detail_customer_phone);
        mQQText = (TextView)v.findViewById(R.id.ps_detail_cutomer_qq);
        mWeChat = (TextView)v.findViewById(R.id.ps_detail_customer_wechat);
        mCustomerName = (TextView)v.findViewById(R.id.ps_detail_customer_name);
        mCustomerJob = (TextView) v.findViewById(R.id.ps_detail_customer_job);
    }
}
