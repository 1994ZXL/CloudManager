package com.example.zxl.cloudmanager.mission.ProjectManagerMission;


import android.app.Fragment;
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
public class MissionSearchFragment extends Fragment {

    private EditText mProjectPlanName;
    private Button mPlanBeginTimeBtn;
    private Button mPlanEndTimeBtn;
    private EditText mProjectPlanManager;
    private Spinner mStateSpinner;

    private Button mSearchBtn;
    private static final String[] stateList={"全部","待完成", "已完成"};
    private ArrayAdapter<String> stateAdapter;

    private Fragment mFragment;
    public MissionSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getActionBar().setTitle("项目任务查询");
        View v = inflater.inflate(R.layout.fragment_mission_search, container, false);
        init(v);
        stateAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStateSpinner.setAdapter(stateAdapter);

        mPlanBeginTimeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
        mPlanEndTimeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Fragment fragment = new MissionManagerListFragment();
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
        return v;
    }

    private void init(View v){
        mPlanBeginTimeBtn = (Button)v.findViewById(R.id.pm_mission_begin_time_button);
        mPlanEndTimeBtn = (Button)v.findViewById(R.id.pm_mission_end_time_button);
        mProjectPlanManager = (EditText) v.findViewById(R.id.pm_employer_name_edittext);
        mProjectPlanName = (EditText) v.findViewById(R.id.pm_project_name_edittext);
        mStateSpinner = (Spinner) v.findViewById(R.id.pm_mission_state_sprinner);

        mSearchBtn = (Button) v.findViewById(R.id.pm_mission_search_button);
    }
}
