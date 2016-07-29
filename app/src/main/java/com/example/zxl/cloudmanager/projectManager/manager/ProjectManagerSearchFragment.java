package com.example.zxl.cloudmanager.projectManager.manager;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.zxl.cloudmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectManagerSearchFragment extends Fragment {

    private EditText mProjectName;
    private Button mBeginTimeBtn;
    private Button mEndTimeBtn;
    private EditText mProjectManager;
    private Spinner mProjectStateSpinner;

    private Button mSearchBtn;
    private ArrayAdapter<String> adapter;
    private static final String[] list={"全部","启动", "进行中","维护期","已结束"};

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    public ProjectManagerSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_project_manager_search, container, false);
        getActivity().getActionBar().setTitle("项目管理");
        init(v);
        mBeginTimeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
        
            }
        });

        mEndTimeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Fragment fragment = new ProjectManagerListFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (!fragment.isAdded()) {
                    transaction.addToBackStack(null);
                    transaction.hide(mFragment);
                    transaction.add(R.id.blankActivity, fragment);
                    transaction.commit();
                } else {
                    transaction.hide(mFragment);
                    transaction.show(fragment);
                    transaction.commit();
                }
            }
        });

        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProjectStateSpinner.setAdapter(adapter);
        return v;
    }

    private void init(View v){
        mBeginTimeBtn = (Button)v.findViewById(R.id.pm_begin_time_button);
        mEndTimeBtn = (Button)v.findViewById(R.id.pm_end_time_button);
        mProjectName = (EditText) v.findViewById(R.id.pm_name_edittext);
        mProjectManager = (EditText) v.findViewById(R.id.pm_manager_edittext);
        mProjectStateSpinner = (Spinner) v.findViewById(R.id.pm_state_sprinner);

        mSearchBtn = (Button) v.findViewById(R.id.project_manager_search_button);
    }

}
