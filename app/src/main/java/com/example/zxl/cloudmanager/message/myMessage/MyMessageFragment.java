package com.example.zxl.cloudmanager.message.myMessage;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.User;

/**
 * Created by ZXL on 2016/7/11.
 */
public class MyMessageFragment extends Fragment {
    private Spinner sexSpinner;
    private TextView mNameText;
    private TextView mPhoneNumber;
    private TextView mQQText;
    private TextView mWeChat;
    private TextView mAddress;

    private String name;
    private String phoneNumber;
    private String qq;
    private String weChat;
    private String address;

    private static final String[] list={"男", "女"};
    private ArrayAdapter<String> adapter;

    private static final String NAME_CHANGE = "名字修改";
    private static final String PHONE_CHANGE = "手机修改";
    private static final String QQ_CHANGE = "QQ修改";
    private static final String WECHAT_CHANGE = "微信修改";
    private static final String ADDRESS_CHANGE = "地址修改";

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;

    }


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View v = layoutInflater.inflate(R.layout.main_fragment_my_message, parent, false);
        getActivity().getActionBar().setTitle("我的信息");

        init(v);
        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(adapter);

        mNameText.setText(User.newInstance(mFragment.getActivity()).getName());
        mNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageEdit fragment = MessageEdit.newInstance(NAME_CHANGE, User.newInstance(mFragment.getActivity()).getName());
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
                MessageEdit fragment = MessageEdit.newInstance(PHONE_CHANGE, User.newInstance(mFragment.getActivity()).getPhone());
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
                MessageEdit fragment = MessageEdit.newInstance(QQ_CHANGE, User.newInstance(mFragment.getActivity()).getQq());
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
                MessageEdit fragment = MessageEdit.newInstance(WECHAT_CHANGE, User.newInstance(mFragment.getActivity()).getWechat());
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.blankActivity, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        mAddress.setText(User.newInstance(mFragment.getActivity()).getAddress());
        mAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageEdit fragment = MessageEdit.newInstance(ADDRESS_CHANGE, User.newInstance(mFragment.getActivity()).getAddress());
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.blankActivity, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return v;
    }

    private void init(View v) {
        sexSpinner = (Spinner)v.findViewById(R.id.main_fragment_my_message_sexSprinner);
        mNameText = (TextView)v.findViewById(R.id.main_fragment_my_message_nameTextView);
        mPhoneNumber = (TextView)v.findViewById(R.id.main_fragment_my_message_phoneTextView);
        mQQText = (TextView)v.findViewById(R.id.main_fragment_my_message_qqTextView);
        mWeChat = (TextView)v.findViewById(R.id.main_fragment_my_message_wechatTextView);
        mAddress = (TextView)v.findViewById(R.id.main_fragment_my_message_locationTextView);
    }
}
