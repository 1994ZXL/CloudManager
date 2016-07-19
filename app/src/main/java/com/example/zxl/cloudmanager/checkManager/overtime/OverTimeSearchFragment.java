package com.example.zxl.cloudmanager.checkManager.overtime;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.zxl.cloudmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OverTimeSearchFragment extends Fragment {

    private static final String MY = "MY_OVERTIME_LIST";
    private Button mOvertimeBeginBtn;
    private Button mOvertimeEndBtn;
    private Spinner mEmployerNameSpinner;
    private Spinner mEmployerProjectSpinner;
    private Fragment mFragment;

    private ArrayAdapter<String> nameAdapter;
    private ArrayAdapter<String> projectAdapter;

    private static final String[] nameList={"全部"};
    private static final String[] projectLst={"全部"};
    private Button mSearchBtn;

    private String string = null;
    private Fragment fragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_over_time, container, false);
        getActivity().getActionBar().setTitle("加班查询");

        init(v);
        loadNameList();
        loadProjectList();
        mOvertimeEndBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        mOvertimeBeginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                fragment = new ManagerOvertimeListFragment();
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

        projectAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, projectLst);
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEmployerProjectSpinner.setAdapter(projectAdapter);

        nameAdapter= new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, nameList);
        nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEmployerNameSpinner.setAdapter(nameAdapter);
        return v;
    }

    //TODO：将员工姓名和其参与的项目加载到下拉列表当中
    public void loadNameList(){

    }

    public void loadProjectList(){

    }

    private void init(View v){
        mOvertimeBeginBtn = (Button) v.findViewById(R.id.employer_overtime_begin_time_button);
        mOvertimeEndBtn = (Button) v.findViewById(R.id.employer_overtime_end_time_button);
        mEmployerNameSpinner = (Spinner) v.findViewById(R.id.employer_name_spinner);
        mEmployerProjectSpinner = (Spinner) v.findViewById(R.id.employer_project_spinner);

        mSearchBtn = (Button) v.findViewById(R.id.my_overtime_search_button);
    }

}
