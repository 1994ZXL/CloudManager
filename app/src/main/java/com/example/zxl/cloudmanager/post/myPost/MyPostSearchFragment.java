package com.example.zxl.cloudmanager.post.myPost;


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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.DateTimePicker;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Post;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MyPostSearchFragment extends Fragment {
    private static final String TAG = "MyPostSearchFragment";

    private TextView mPostBeginTimeBtn;
    private TextView mPostEndTimeBtn;
    private EditText mPostContent;

    private Button mSearchBtn;
    private TextView mClean;
    private TextView mBack;

    private String postContent;

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_my_post_search, container, false);



        init(v);
        control();

        return v;
    }

    private void init(View v){
        mPostBeginTimeBtn = (TextView) v.findViewById(R.id.my_post_search_post_begintTime);
        mPostEndTimeBtn = (TextView) v.findViewById(R.id.my_post_search_post_endTime);
        mPostContent = (EditText) v.findViewById(R.id.my_post_content_edittext);

        mSearchBtn = (Button) v.findViewById(R.id.my_post_search_button);
        mClean = (TextView) v.findViewById(R.id.fragment_my_post_search_clean);
        mBack = (TextView) v.findViewById(R.id.fragment_my_post_search_back);
    }

    private void control() {
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
                postContent = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MyPostFragment();
                Bundle bundle = new Bundle();

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

                bundle.putString(Link.content, postContent);

                Log.d(TAG, "选择条件："
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

        mClean.setVisibility(View.INVISIBLE);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
