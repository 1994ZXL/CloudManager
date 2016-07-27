package com.example.zxl.cloudmanager.leaderSearch;


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
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.MyPostFragment;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.Post;
import com.example.zxl.cloudmanager.model.PostLab;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class LeaderPostSearchFragment extends Fragment {

    private EditText mEmployerName;
    private Button mBeginTimeBtn;
    private Button mEndTimeBtn;
    private EditText mPostContent;

    private Button mSearchBtn;

    private Fragment mFragment;

    private Date beginTime;
    private String bgtime;
    private Date endTime;
    private String edtime;
    private String name;

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
        control();

        return v;
    }

    private void init(View v){
        mBeginTimeBtn = (Button) v.findViewById(R.id.leader_post_begin_time_button);
        mEndTimeBtn = (Button) v.findViewById(R.id.leader_post_end_time_button);
        mPostContent = (EditText) v.findViewById(R.id.leader_post_content_edittext);
        mEmployerName = (EditText) v.findViewById(R.id.leader_search_name_edittext);

        mSearchBtn = (Button) v.findViewById(R.id.leader_post_search_button);
    }

    private void control() {
        mBeginTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 12);
                fragment.setTargetFragment(LeaderPostSearchFragment.this, 12);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "LeaderPostSearchFragment");
            }
        });

        mEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 13);
                fragment.setTargetFragment(LeaderPostSearchFragment.this, 13);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "LeaderPostSearchFragment");
            }
        });

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

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, name);
                try {
                    search();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "抛异常");
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        } else if (requestCode == 12) {
            beginTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateBeginDate();
        } else if (requestCode == 13) {
            endTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateEndDate();
        }
    }

    private void updateBeginDate(){
        bgtime = android.text.format.DateFormat.format("yyyy.M.dd", beginTime).toString();
        mBeginTimeBtn.setText(bgtime);
    }
    private void updateEndDate(){
        if (endTime.after(beginTime)) {
            edtime = android.text.format.DateFormat.format("yyyy.M.dd", endTime).toString();
            mEndTimeBtn.setText(edtime);
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void search() throws Exception {
        mPosts = PostLab.newInstance(mFragment.getActivity()).getPosts();
        for (index = 0; index < mPosts.size(); index++ ) {
            if (ConverToDate(mPosts.get(index).getPostTime()).after(beginTime)
                    && ConverToDate(mPosts.get(index).getPostTime()).before(endTime)
                    && name.equals(mPosts.get(index).getName())) {
                sum.add(index);
            }
        }
        Log.d(TAG, "下标：" + sum);
        Fragment fragment = new MyPostFragment();
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList(SEARCH_KEY, sum);
        bundle.putString(WHERE, "LeaderPostSearchFragment");
        fragment.setArguments(bundle);
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

    public static Date ConverToDate(String strDate) throws Exception
    {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        return df.parse(strDate);
    }
}
