package com.example.zxl.cloudmanager.checkManager.travel;

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
import com.example.zxl.cloudmanager.myOvertime.OverTimeFragment;

public class ManagerTravelSearchFragment extends Fragment {

    private Button mBeginTimeBtn;
    private Button mEndTimeBtn;
    private Button mComeBeginBtn;
    private Button mComeEndBtn;
    private EditText mEmployerNameET;

    private Spinner mStateSpinner;
    private Fragment mFragment;
    private Fragment fragment;

    private ArrayAdapter<String> stateAdapter;
    private static final String[] stateList={"全部","正常","取消"};
    private Button mSearchBtn;

    public ManagerTravelSearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travelsearch, container, false);
        getActivity().getActionBar().setTitle("出差查询");

        init(view);
        stateAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStateSpinner.setAdapter(stateAdapter);

        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                fragment = new ManagerTravelListFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (!fragment.isAdded()) {
                    transaction.addToBackStack(null);
                    transaction.hide(mFragment);
                    transaction.add(R.id.cmTravelActivity, fragment);
                    transaction.commit();
                } else {
                    transaction.hide(mFragment);
                    transaction.show(fragment);
                    transaction.commit();
                }
            }
        });
        return view;
    }

    private void init(View v){
        mBeginTimeBtn = (Button) v.findViewById(R.id.employer_travel_begin_time_button);
        mEndTimeBtn = (Button) v.findViewById(R.id.employer_travel_end_time_button);
        mComeBeginBtn = (Button) v.findViewById(R.id.employer_back_begin_time_button);
        mComeEndBtn = (Button) v.findViewById(R.id.employer_back_end_time_button);
        mStateSpinner = (Spinner) v.findViewById(R.id.employer_travel_state_spinner);
        mEmployerNameET = (EditText) v.findViewById(R.id.cm_employer_name_edittext);

        mSearchBtn = (Button) v.findViewById(R.id.my_travel_search_button);
    }
}
