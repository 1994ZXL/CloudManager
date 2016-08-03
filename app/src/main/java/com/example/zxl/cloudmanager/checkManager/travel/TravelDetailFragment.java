package com.example.zxl.cloudmanager.checkManager.travel;


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

/**
 * A simple {@link Fragment} subclass.
 */
public class TravelDetailFragment extends Fragment {

    private Spinner mEmployerNameSpinner;
    private Button mBeginTimeBtn;
    private Button mEndTimeBtn;
    private TextView mTravelContentET;

    private Button mSubmitBtn;

    private ArrayAdapter<String> employerAdapter;
    private static final String[] employerList={"全部"};

    public TravelDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_travel_detail, container, false);
        getActivity().getActionBar().setTitle("出差处理");
        init(v);
        employerAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, employerList);
        employerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEmployerNameSpinner.setAdapter(employerAdapter);

        mSubmitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
        return v;
    }

    private void init(View v){
        mBeginTimeBtn = (Button) v.findViewById(R.id.manager_travel_begin_time_button);
        mEndTimeBtn = (Button) v.findViewById(R.id.manager_travel_end_time_button);
        mEmployerNameSpinner = (Spinner) v.findViewById(R.id.manager_travel_employer_name_spinner);
        mTravelContentET = (TextView) v.findViewById(R.id.manager_travel_content_edittext);

        mSubmitBtn = (Button) v.findViewById(R.id.manager_travel_submit_button);
    }
}
