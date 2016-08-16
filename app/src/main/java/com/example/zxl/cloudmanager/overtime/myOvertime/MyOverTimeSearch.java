package com.example.zxl.cloudmanager.overtime.myOvertime;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.Link;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOverTimeSearch extends Fragment {

    private static final String MY = "MY_OVERTIME_LIST";
    private Button mOvertimeBeginBtn;
    private Button mOvertimeEndBtn;
    private Spinner mEmployerProjectSpinner;
    private Spinner mOvertimeStatus;
    private Fragment mFragment;

    private ArrayAdapter<String> projectAdapter;
    private ArrayAdapter<String> statusAdapter;

    private static final String[] projectLst={"全部"};
    private static final String[] statusList = {"等待", "确认", "取消", };//状态:1:等待,2:确认,3:取消，默认为等待
    private Button mSearchBtn;

    private Date beginTime;
    private String bgtime;
    private Date endTime;
    private String edtime;
    private String project;
    private int status;

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
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 12);
                fragment.setTargetFragment(MyOverTimeSearch.this, 12);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyOverTimeSearch");
            }
        });

        mOvertimeBeginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 13);
                fragment.setTargetFragment(MyOverTimeSearch.this, 13);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyOverTimeSearch");
            }
        });

        projectAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, projectLst);
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEmployerProjectSpinner.setAdapter(projectAdapter);
        mEmployerProjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                project = projectLst[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        statusAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, statusList);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOvertimeStatus.setAdapter(statusAdapter);
        mOvertimeStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //状态:1:等待,2:确认,3:取消，默认为等待
                if (statusList[i] == "等待") {
                    status = 1;
                }
                if (statusList[i] == "确认") {
                    status = 2;
                }
                if (statusList[i] == "取消") {
                    status = 3;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Fragment fragment = new MyOverTimeFragment();
                Bundle bundle = new Bundle();

                if (null != bgtime) {
                    bundle.putInt(Link.start_time, DateForGeLingWeiZhi.toGeLinWeiZhi(bgtime));
                } else {
                    bundle.putInt(Link.start_time, -1);
                }
                if (null != edtime) {
                    bundle.putInt(Link.end_time, DateForGeLingWeiZhi.toGeLinWeiZhi(edtime));
                } else {
                    bundle.putInt(Link.end_time, -1);
                }
                bundle.putString(Link.work_pm, project);
                bundle.putInt(Link.status, status);

                fragment.setArguments(bundle);
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

    //TODO：将员工姓名和其参与的项目加载到下拉列表当中
    public void loadNameList(){

    }

    public void loadProjectList(){

    }

    private void init(View v){
        mOvertimeBeginBtn = (Button) v.findViewById(R.id.employer_overtime_begin_time_button);
        mOvertimeEndBtn = (Button) v.findViewById(R.id.employer_overtime_end_time_button);
        mEmployerProjectSpinner = (Spinner) v.findViewById(R.id.employer_project_spinner);
        mOvertimeStatus = (Spinner) v.findViewById(R.id.overtime_status_spinner);
        mSearchBtn = (Button) v.findViewById(R.id.my_overtime_search_button);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK){
            return;
        } else if (requestCode == 12) {
            beginTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateBeginDate();
        } else if (requestCode == 13) {
            endTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateEndDate();
        }
    }

    private void updateBeginDate(){
        bgtime = android.text.format.DateFormat.format("yyyy.M.dd", beginTime).toString();
        mOvertimeBeginBtn.setText(bgtime);
    }
    private void updateEndDate(){
        if (endTime.after(beginTime)) {
            edtime = android.text.format.DateFormat.format("yyyy.M.dd", endTime).toString();
            mOvertimeEndBtn.setText(edtime);
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
