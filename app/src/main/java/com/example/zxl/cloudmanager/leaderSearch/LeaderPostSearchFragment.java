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
import com.example.zxl.cloudmanager.checkManager.travel.ManagerTravelListFragment;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.Link;
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
                bundle.putInt(Link.creat_time_t, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(bgtime));
                bundle.putInt(Link.creat_time_f, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(edtime));
                bundle.putString(Link.content, content);
                Log.d(TAG, "选择条件："
                        + " mem_name: " + name
                        + " start_time: " + bgtime
                        + " over_time: " + edtime
                        + " content: " + mPostContent);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.blankActivity, fragment);
                transaction.commit();
            }
        });

        /*control();*/

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
                search();
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

    private void search() {
        Fragment fragment = new LeaderPostListFragment();
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
