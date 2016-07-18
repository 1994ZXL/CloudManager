package com.example.zxl.cloudmanager.myMission;


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
public class MyMissionSearchFragment extends Fragment {
    private EditText mMisiionNameET;
    private Button mMissionBeginBtn;
    private Button mMissionEndBtn;
    private Spinner mMissionStateSpinner;

    private Button mSearchBtn;

    private ArrayAdapter<String> adapter;
    private static final String[] list={"全部","待完成", "待审核","已完成","未完成"};
    public MyMissionSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_mission, container, false);
        getActivity().getActionBar().setTitle("任务查询");
        init(v);
        mMissionBeginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        mMissionEndBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMissionStateSpinner.setAdapter(adapter);
        return v;
    }

    private void init(View v){
        mMisiionNameET = (EditText) v.findViewById(R.id.my_mission_name_edittext);
        mMissionBeginBtn = (Button) v.findViewById(R.id.my_mission_begin_time_button);
        mMissionEndBtn = (Button) v.findViewById(R.id.my_mission_end_time_button);
        mMissionStateSpinner = (Spinner) v.findViewById(R.id.my_mission_state_sprinner);

        mSearchBtn = (Button) v.findViewById(R.id.my_mission_search_button);
    }

}
