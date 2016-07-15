package com.example.zxl.cloudmanager.publicSearch.bug;

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


public class BugSearchFragment extends Fragment {

    private EditText mProjectNameET;
    private Button mBugBeginBtn;
    private Button mBugEndBtn;
    private EditText mFinderET;
    private EditText mReviserET;
    private Spinner mBugStateSpinner;
    private Spinner mBugLevelSpinner;

    private Button mSearchBtn;
    private ArrayAdapter<String> stateAdapter;
    private ArrayAdapter<String> levelAdapter;

    private static final String stateList[] =
            {"全部","待确认","待修改","待测试","已通过","已排除","不解决"};
    private static final String levelList[] =
            {"全部","一级","二级","三级","四级","五级","六级"};


    public BugSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getActionBar().setTitle("bug查询");
        View v = inflater.inflate(R.layout.fragment_bug_search, container, false);
        init(v);

        stateAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBugStateSpinner.setAdapter(stateAdapter);


        levelAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, levelList);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBugLevelSpinner.setAdapter(levelAdapter);
        return v;
    }

    private void init(View v){
        mBugBeginBtn = (Button)v.findViewById(R.id.bug_begin_time_button);
        mBugEndBtn = (Button)v.findViewById(R.id.bug_end_time_button);
        mProjectNameET = (EditText) v.findViewById(R.id.bug_project_name_edittext);
        mFinderET = (EditText) v.findViewById(R.id.bug_finder_edittext);
        mReviserET = (EditText) v.findViewById(R.id.bug_reviser_edittext);
        mBugStateSpinner = (Spinner) v.findViewById(R.id.bug_state_sprinner);
        mBugLevelSpinner = (Spinner) v.findViewById(R.id.bug_level_sprinner);

        mSearchBtn = (Button) v.findViewById(R.id.bug_search_button);

    }
}
