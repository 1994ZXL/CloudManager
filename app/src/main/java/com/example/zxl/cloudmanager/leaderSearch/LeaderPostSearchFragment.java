package com.example.zxl.cloudmanager.leaderSearch;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;


public class LeaderPostSearchFragment extends Fragment {

    private TextView mEmployerName;
    private Button mBeginTimeBtn;
    private Button mEndTimeBtn;
    private EditText mPostContent;

    private Button mSearchBtn;
    public LeaderPostSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.leader_post_search, container, false);
        getActivity().getActionBar().setTitle("日报查询");
        init(v);
        return v;
    }

    private void init(View v){
        mBeginTimeBtn = (Button) v.findViewById(R.id.employer_travel_begin_time_button);
        mEndTimeBtn = (Button) v.findViewById(R.id.employer_travel_end_time_button);
        mPostContent = (EditText) v.findViewById(R.id.my_post_content_edittext);
        mEmployerName = (TextView) v.findViewById(R.id.leader_search_name_edittext);

        mSearchBtn = (Button) v.findViewById(R.id.my_post_search_button);
    }

}
