package com.example.zxl.cloudmanager.publicSearch.project;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.zxl.cloudmanager.R;


/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class ProjectSearchFragment extends Fragment {

    private EditText mProjectName;
    private Button mBeginTimeBtn;
    private Button mEndTimeBtn;
    private EditText mProjectManager;

    private Button mSearchBtn;

    public ProjectSearchFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getActionBar().setTitle("项目查询");
        View v = inflater.inflate(R.layout.fragment_project_search_public, container, false);

        init(v);

        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
        return v;
    }

    private void init(View v){
        mBeginTimeBtn = (Button)v.findViewById(R.id.project_begin_time_button);
        mEndTimeBtn = (Button)v.findViewById(R.id.project_end_time_button);
        mProjectName = (EditText) v.findViewById(R.id.project_name_edittext);
        mProjectManager = (EditText) v.findViewById(R.id.project_manager_edittext);

        mSearchBtn = (Button) v.findViewById(R.id.project_search_button);
    }

}
