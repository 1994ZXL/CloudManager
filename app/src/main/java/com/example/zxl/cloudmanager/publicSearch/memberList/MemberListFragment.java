package com.example.zxl.cloudmanager.publicSearch.memberList;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.zxl.cloudmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberListFragment extends Fragment {

    private EditText mProjectNameET;

    public MemberListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_member_list_public, container, false);
        init(v);
        return v;
    }
    private void init(View v){
        mProjectNameET = (EditText) v.findViewById(R.id.member_project_name_edittext);
    }

}
