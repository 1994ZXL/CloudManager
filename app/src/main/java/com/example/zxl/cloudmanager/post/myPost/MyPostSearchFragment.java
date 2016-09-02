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

    private Date beginTime;
    private String bgtime;
    private Date endTime;
    private String edtime;
    private Date postBeginTime;
    private String postbgtime;
    private Date postEndTime;
    private String postedtime;

    private String postContent;

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
        ImageButton mBtn = (ImageButton) getActivity().findViewById(R.id.my_post_activity_searchBtn);
        mBtn.setVisibility(View.INVISIBLE);
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
    }

    private void control() {
        mPostBeginTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 14);
                fragment.setTargetFragment(MyPostSearchFragment.this, 14);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyPostSearchFragment");
            }
        });
        mPostEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 15);
                fragment.setTargetFragment(MyPostSearchFragment.this, 15);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyPostSearchFragment");
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

                if (null != postbgtime){
                    bundle.putInt(Link.daily_time_from, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(postbgtime));
                } else {
                    bundle.putInt(Link.daily_time_from, -1);
                }

                if (null != postedtime) {
                    bundle.putInt(Link.daily_time_to, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(postedtime));
                } else {
                    bundle.putInt(Link.daily_time_to, -1);
                }

                bundle.putString(Link.content, postContent);

                Log.d(TAG, "选择条件："
                        + " post_start_time: " + postbgtime
                        + " post_over_time: " + postedtime
                        + " content: " + mPostContent);

                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.postActivity, fragment);
                transaction.commit();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        }  else if (requestCode == 14) {
            postBeginTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updatePostBeginDate();
        } else if (requestCode == 15) {
            postEndTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updatePostEndDate();
        }
    }

    private void updatePostBeginDate(){
        postbgtime = android.text.format.DateFormat.format("yyyy年MM月dd", postBeginTime).toString();
        mPostBeginTimeBtn.setText(postbgtime);
    }
    private void updatePostEndDate(){
        if (postEndTime.after(postBeginTime)) {
            postedtime = android.text.format.DateFormat.format("yyyy年MM月dd", postEndTime).toString();
            mPostEndTimeBtn.setText(postedtime);
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ImageButton mBtn = (ImageButton) getActivity().findViewById(R.id.my_post_activity_searchBtn);
        mBtn.setVisibility(View.INVISIBLE);
    }
}
