package com.example.zxl.cloudmanager.manageProject.projectManagerProjectManage;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DateTimePicker;
import com.example.zxl.cloudmanager.model.Link;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageProjectSearchFragment extends Fragment {

    private static final String TAG = "PMSearchFragment";
    private EditText mProjectName;
    private TextView mBeginTimeBtn;
    private TextView mEndTimeBtn;
    private Spinner mProjectStateSpinner;

    private Button mSearchBtn;
    private ArrayAdapter<String> adapter;
    private static final String[] list={"取消", "准备","开发","维护","结束"};

    private int state;
    private String project_name;

    private TextView mBack;

    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    public ManageProjectSearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_project_manager_search, container, false);

        init(v);

        mProjectName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                project_name = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mBeginTimeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DateTimePicker.selectDateTime(mFragment, mBeginTimeBtn);
            }
        });

        mEndTimeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DateTimePicker.selectDateTime(mFragment, mEndTimeBtn);
            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Fragment fragment = new PMManageProjectListFragment();
                Bundle bundle = new Bundle();

                bundle.putString(Link.project_name, project_name);

                if (null != mBeginTimeBtn.getText()) {
                    bundle.putInt(Link.ready_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi3(mBeginTimeBtn.getText().toString()));
                } else {
                    bundle.putInt(Link.ready_time, -1);
                }

                if (null != mEndTimeBtn.getText()) {
                    bundle.putInt(Link.finshed_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi3(mEndTimeBtn.getText().toString()));
                } else {
                    bundle.putInt(Link.finshed_time, -1);
                }

                bundle.putInt(Link.project_state, state);

                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.blankActivity, fragment);
                transaction.commit();
            }
        });

        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProjectStateSpinner.setAdapter(adapter);
        mProjectStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });
        return v;
    }

    private void init(View v){
        mBeginTimeBtn = (TextView)v.findViewById(R.id.pm_begin_time_button);
        mEndTimeBtn = (TextView)v.findViewById(R.id.pm_end_time_button);
        mProjectName = (EditText) v.findViewById(R.id.pm_name_edittext);
        mProjectStateSpinner = (Spinner) v.findViewById(R.id.pm_state_sprinner);
        mBack = (TextView) v.findViewById(R.id.manage_project_search_back);
        mSearchBtn = (Button) v.findViewById(R.id.project_manager_search_button);
    }

}
