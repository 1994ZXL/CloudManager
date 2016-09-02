package com.example.zxl.cloudmanager.projectManager.memberManager;


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
public class MemberSearchFragment extends Fragment {

    private EditText mProjectNameET;

    private Button mSearchBtn;

    public MemberSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_member_search, container, false);
        init(v);
        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
        return v;
    }

    private void init(View v){
        mProjectNameET = (EditText) v.findViewById(R.id.pm_project_name_edittext);
        mSearchBtn = (Button) v.findViewById(R.id.pm_member_search_button);
    }
}
