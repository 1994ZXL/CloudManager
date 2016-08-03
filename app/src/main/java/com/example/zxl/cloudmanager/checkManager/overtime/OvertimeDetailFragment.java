package com.example.zxl.cloudmanager.checkManager.overtime;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Leave;
import com.example.zxl.cloudmanager.model.OverTime;

/**
 * A simple {@link Fragment} subclass.
 */
public class OvertimeDetailFragment extends Fragment {

    private Spinner mEmployerSpinner;
    private Button mBeginTimeBtn;
    private Button mEndTimeBtn;
    private Spinner mProjectNameSpinner;
    private TextView mOvertimeReasonET;

    private Button mSubmitBtn;

    private static OverTime mOverTime = new OverTime();

    private ArrayAdapter<String> employerAdapter;
    private static final String[] employerList={"全部"};

    private ArrayAdapter<String> projectAdapter;
    private static final String[] projectList={"全部"};
    public OvertimeDetailFragment() {
        // Required empty public constructor
    }
    public static OvertimeDetailFragment newInstance(Object data) {
        mOverTime = (OverTime) data;
        OvertimeDetailFragment fragment = new OvertimeDetailFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_overtime_detail, container, false);
        init(v);
        employerAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, employerList);
        employerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEmployerSpinner.setAdapter(employerAdapter);

        projectAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, projectList);
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProjectNameSpinner.setAdapter(projectAdapter);

        mSubmitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
        return v;
    }

    private void init(View v){
        mBeginTimeBtn = (Button) v.findViewById(R.id.manager_overtime_begin_time_button);
        mEndTimeBtn = (Button) v.findViewById(R.id.manager_overtime_end_time_button);
        mEmployerSpinner = (Spinner) v.findViewById(R.id.manager_employer_name_spinner);
        mProjectNameSpinner = (Spinner) v.findViewById(R.id.manager_overtime_project_spinner);
        mOvertimeReasonET = (TextView) v.findViewById(R.id.manager_overtime_reason_edittext);

        mSubmitBtn = (Button) v.findViewById(R.id.manager_overtime_submit_button);
    }
}
