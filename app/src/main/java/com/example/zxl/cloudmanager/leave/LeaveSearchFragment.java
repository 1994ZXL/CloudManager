package com.example.zxl.cloudmanager.leave;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.zxl.cloudmanager.R;

public class LeaveSearchFragment extends Fragment {

    private Button mLeaveBeginBtn;
    private Button mLeaveEndBtn;
    private Spinner mLeaveKindSpinner;
    private Spinner mLeaveStateSpinner;

    private Button mSearchBtn;

    private ArrayAdapter<String> stateAdapter;
    private static final String[] stateList={"全部","未批准", "待批准","已批准"};

    private ArrayAdapter<String> kindAdapter;
    private static final String[] kindList={"全部","病假", "事假","婚假","丧假","产假","年休假"};

    public LeaveSearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_leavesearch, container, false);

        init(v);
        stateAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLeaveStateSpinner.setAdapter(stateAdapter);

        kindAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, kindList);
        kindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLeaveKindSpinner.setAdapter(kindAdapter);

        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
        return v;
    }

    private void init(View v){
        mLeaveBeginBtn = (Button) v.findViewById(R.id.my_leave_begin_time_button);
        mLeaveEndBtn = (Button) v.findViewById(R.id.my_leave_end_time_button);
        mLeaveKindSpinner = (Spinner) v.findViewById(R.id.my_leave_kind_sprinner);
        mLeaveStateSpinner = (Spinner) v.findViewById(R.id.my_leave_state_sprinner);

        mSearchBtn = (Button) v.findViewById(R.id.my_leave_search_button);
    }


}
