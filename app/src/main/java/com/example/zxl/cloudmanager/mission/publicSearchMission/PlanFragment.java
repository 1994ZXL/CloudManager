package com.example.zxl.cloudmanager.mission.publicSearchMission;


import android.app.Fragment;
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
public class PlanFragment extends Fragment {

    private EditText mProjectPlanName;
    private Button mPlanBeginTimeBtn;
    private Button mPlanEndTimeBtn;
    private EditText mProjectPlanManager;
    private Spinner mPrioritySpinner;
    private Spinner mStateSpinner;

    private ArrayAdapter<String> priorityAdapter;
    private static final String[] priorityList={"高", "中","低"};

    private static final String[] stateList={"待完成", "待审核","已完成","已审核"};
    private ArrayAdapter<String> stateAdapter;

    public PlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getActionBar().setTitle("任务查询");
        View v = inflater.inflate(R.layout.fragment_plan_public, container, false);
        init(v);

        priorityAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, priorityList);
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPrioritySpinner.setAdapter(priorityAdapter);

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

        return v;
    }

    private void init(View v){
        mPlanBeginTimeBtn = (Button)v.findViewById(R.id.project_mission_begin_time_button);
        mPlanEndTimeBtn = (Button)v.findViewById(R.id.project_mission_end_time_button);
        mProjectPlanManager = (EditText) v.findViewById(R.id.project_plan_name_edittext);
        mProjectPlanName = (EditText) v.findViewById(R.id.project_plan_manager_name_edittext);
        mPrioritySpinner = (Spinner) v.findViewById(R.id.project_plan_priority_sprinner);
        mStateSpinner = (Spinner) v.findViewById(R.id.project_mission_state_sprinner);
    }
}
