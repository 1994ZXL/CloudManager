package com.example.zxl.cloudmanager.post.leader;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.DateTimePicker;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Post;
import com.example.zxl.cloudmanager.post.myPost.MyPostFragment;
import com.example.zxl.cloudmanager.post.projectManagerPost.PMPostActivtiy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class LeaderPostSearchFragment extends Fragment {

    private EditText mEmployerName;
    private TextView mPostBeginTimeBtn;
    private TextView mPostEndTimeBtn;
    private EditText mPostContent;

    private LinearLayout mContentLinearLayout;

    private Button mSearchBtn;
    private TextView mBack;
    private TextView mClean;

    private Fragment mFragment;

    private String name;
    private String content;

    private static final String TAG = "LPSearchFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.leader_post_search, container, false);

        init(v);

        if (mFragment.getActivity().getClass() == PMPostActivtiy.class)
            mContentLinearLayout.setVisibility(View.GONE);


        //查询条件
        mEmployerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                name = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mPostBeginTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDate(mFragment, mPostBeginTimeBtn);
            }
        });
        mPostEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDate(mFragment, mPostEndTimeBtn);
            }
        });
        mPostContent.addTextChangedListener(new TextWatcher() {
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

        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Fragment fragment = new MyPostFragment();
                Bundle bundle = new Bundle();

                bundle.putString(Link.mem_name, name);

                if (null != mPostBeginTimeBtn.getText()){
                    bundle.putInt(Link.daily_time_from, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(mPostBeginTimeBtn.getText().toString()));
                } else {
                    bundle.putInt(Link.daily_time_from, -1);
                }

                if (null != mPostEndTimeBtn.getText()) {
                    bundle.putInt(Link.daily_time_to, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(mPostEndTimeBtn.getText().toString()));
                } else {
                    bundle.putInt(Link.daily_time_to, -1);
                }

                bundle.putString(Link.content, content);

                Log.d(TAG, "选择条件："
                        + " mem_name: " + name
                        + " post_start_time: " + mPostBeginTimeBtn.getText()
                        + " post_over_time: " + mPostEndTimeBtn.getText()
                        + " content: " + mPostContent);

                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.blankActivity, fragment);
                transaction.commit();
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mClean.setVisibility(View.INVISIBLE);

        return v;
    }

    private void init(View v){
        mPostBeginTimeBtn = (TextView) v.findViewById(R.id.leader_post_post_begin_time_button);
        mPostEndTimeBtn = (TextView) v.findViewById(R.id.leader_post_post_end_time_button);
        mPostContent = (EditText) v.findViewById(R.id.leader_post_content_edittext);
        mEmployerName = (EditText) v.findViewById(R.id.leader_search_name_edittext);

        mSearchBtn = (Button) v.findViewById(R.id.leader_post_search_button);
        mBack = (TextView) v.findViewById(R.id.leader_post_search_back);
        mClean = (TextView) v.findViewById(R.id.leader_post_search_clean);

        mContentLinearLayout = (LinearLayout) v.findViewById(R.id.leader_post_contentLinearLayout);
    }

}
