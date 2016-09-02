package com.example.zxl.cloudmanager.projectManager.PMAdressBook;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.check.checkManager.ManagerCheckListFragment;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.Link;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectManagerSearchFragment extends Fragment {

    private static final String TAG = "PMSearchFragment";
    private EditText mProjectName;
    private Button mBeginTimeBtn;
    private Button mEndTimeBtn;
    private EditText mProjectManager;
    private Spinner mProjectStateSpinner;

    private Button mSearchBtn;
    private ArrayAdapter<String> adapter;
    private static final String[] list={"取消", "准备","开发","维护","结束"};

    private String project_name;
    private String header;
    private Date mReady_time;
    private String ready_time;
    private Date mFinished_time;
    private String finished_time;
    private Fragment mFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    public ProjectManagerSearchFragment() {
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
        mProjectManager.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                header = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mBeginTimeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 15);
                fragment.setTargetFragment(ProjectManagerSearchFragment.this, 15);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "ProjectManagerSearchFragment");
            }
        });

        mEndTimeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 16);
                fragment.setTargetFragment(ProjectManagerSearchFragment.this, 16);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "ProjectManagerSearchFragment");
            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Fragment fragment = new ProjectManagerListFragment();
                Bundle bundle = new Bundle();

                bundle.putString(Link.project_name, project_name);

                if (null != ready_time) {
                    bundle.putInt(Link.ready_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(ready_time));
                } else {
                    bundle.putInt(Link.ready_time, -1);
                }

                if (null != finished_time) {
                    bundle.putInt(Link.finished_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(finished_time));
                } else {
                    bundle.putInt(Link.finished_time, -1);
                }

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
        return v;
    }

    private void init(View v){
        mBeginTimeBtn = (Button)v.findViewById(R.id.pm_begin_time_button);
        mEndTimeBtn = (Button)v.findViewById(R.id.pm_end_time_button);
        mProjectName = (EditText) v.findViewById(R.id.pm_name_edittext);
        mProjectManager = (EditText) v.findViewById(R.id.pm_manager_edittext);
        mProjectStateSpinner = (Spinner) v.findViewById(R.id.pm_state_sprinner);

        mSearchBtn = (Button) v.findViewById(R.id.project_manager_search_button);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "进入回调 " + " resultCode:" + requestCode);
        if (resultCode != Activity.RESULT_OK){
            Log.d(TAG, "未进入判断");
            return;
        } else if (requestCode == 15) {
            mReady_time = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateBeginDate();
        } else if (requestCode == 16) {
            mFinished_time = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateEndDate();
        }
    }
    private void updateBeginDate(){
        ready_time = android.text.format.DateFormat.format("yyyy年MM月dd", mReady_time).toString();
        Log.d(TAG, "bgtime: " + ready_time);
        mBeginTimeBtn.setText(ready_time);
        Log.d(TAG, "beginTimeButton: " + mBeginTimeBtn.getText());
    }
    private void updateEndDate(){
        if (mFinished_time.after(mReady_time)) {
            finished_time = android.text.format.DateFormat.format("yyyy年MM月dd", mFinished_time).toString();
            Log.d(TAG, "edtime: " + finished_time);
            mEndTimeBtn.setText(finished_time);
            Log.d(TAG, "endTimeButton: " + mEndTimeBtn.getText());
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
