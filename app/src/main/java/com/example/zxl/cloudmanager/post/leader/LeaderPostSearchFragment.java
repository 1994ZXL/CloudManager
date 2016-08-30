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


public class LeaderPostSearchFragment extends Fragment {

    private EditText mEmployerName;
    private Button mPostBeginTimeBtn;
    private Button mPostEndTimeBtn;
    private EditText mPostContent;

    private Button mSearchBtn;

    private Fragment mFragment;

    private Date beginTime;
    private String bgtime;
    private Date endTime;
    private String edtime;
    private Date postBeginTime;
    private String postbgtime;
    private Date postEndTime;
    private String postedtime;
    private String name;
    private String content;

    private ArrayList<Post> mPosts = new ArrayList<Post>();
    private int index;
    private ArrayList<Integer> sum = new ArrayList<Integer>();
    private static final String SEARCH_KEY = "search_key";
    private static final String WHERE = "where";
    private static final String TAG = "LPSearchFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    public LeaderPostSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.leader_post_search, container, false);
        getActivity().getActionBar().setTitle("日报查询");

        init(v);
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
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 14);
                fragment.setTargetFragment(LeaderPostSearchFragment.this, 14);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "LeaderPostSearchFragment");
            }
        });
        mPostEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 15);
                fragment.setTargetFragment(LeaderPostSearchFragment.this, 15);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "LeaderPostSearchFragment");
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
                Fragment fragment = new LeaderPostListFragment();
                Bundle bundle = new Bundle();

                bundle.putString(Link.mem_name, name);

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

                bundle.putString(Link.content, content);

                Log.d(TAG, "选择条件："
                        + " mem_name: " + name
                        + " post_start_time: " + postbgtime
                        + " post_over_time: " + postedtime
                        + " content: " + mPostContent);

                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.blankActivity, fragment);
                transaction.commit();
            }
        });

        return v;
    }

    private void init(View v){
        mPostBeginTimeBtn = (Button) v.findViewById(R.id.leader_post_post_begin_time_button);
        mPostEndTimeBtn = (Button) v.findViewById(R.id.leader_post_post_end_time_button);
        mPostContent = (EditText) v.findViewById(R.id.leader_post_content_edittext);
        mEmployerName = (EditText) v.findViewById(R.id.leader_search_name_edittext);

        mSearchBtn = (Button) v.findViewById(R.id.leader_post_search_button);
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
        postbgtime = android.text.format.DateFormat.format("yyyy.M.dd", postBeginTime).toString();
        mPostBeginTimeBtn.setText(postbgtime);
    }
    private void updatePostEndDate(){
        if (postEndTime.after(postBeginTime)) {
            postedtime = android.text.format.DateFormat.format("yyyy.M.dd", postEndTime).toString();
            mPostEndTimeBtn.setText(postedtime);
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }



    public static Date ConverToDate(String strDate) throws Exception
    {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        return df.parse(strDate);
    }
}
