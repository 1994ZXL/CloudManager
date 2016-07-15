package com.example.zxl.cloudmanager.publicSearch.usecase;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.zxl.cloudmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsecaseFragment extends Fragment {

    private EditText mProjectNameET;
    private EditText mUsecaseIdET;
    private EditText mProgramNumberET;
    private EditText mTesterET;
    private EditText mDeveloperET;
    private Button mUsecaseBeginBtn;
    private Button mUsecaseEndBtn;

    private Button mSearchBtn;

    public UsecaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getActionBar().setTitle("用例查询");
        View v = inflater.inflate(R.layout.fragment_usecase_public, container, false);
        init(v);
        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
        return v;
    }

    private void init(View v){
        mUsecaseBeginBtn = (Button)v.findViewById(R.id.usecase_begin_time_button);
        mUsecaseEndBtn = (Button)v.findViewById(R.id.usecase_end_time_button);
        mProjectNameET = (EditText) v.findViewById(R.id.usecase_project_edittext);
        mUsecaseIdET = (EditText) v.findViewById(R.id.usecase_id_edittext);
        mProgramNumberET = (EditText) v.findViewById(R.id.usecase_program_number);
        mTesterET = (EditText) v.findViewById(R.id.usecase_tester);
        mDeveloperET = (EditText) v.findViewById(R.id.usecase_developer);

        mSearchBtn = (Button) v.findViewById(R.id.usecase_search_button);

    }
}
