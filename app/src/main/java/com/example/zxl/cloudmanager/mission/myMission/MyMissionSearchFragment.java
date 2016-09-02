package com.example.zxl.cloudmanager.mission.myMission;


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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.DateTimePicker;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Mission;
import com.example.zxl.cloudmanager.model.MissionLab;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMissionSearchFragment extends Fragment {
    private EditText mMisiionNameET;
    private Button mMissionBeginBeginBtn;
    private Button mMissionBeginEndBtn;
    private Button mMissionEndBeginBtn;
    private Button mMissionEndEndBtn;
    private Spinner mMissionStateSpinner;

    private Button mSearchBtn;

    private ArrayAdapter<String> adapter;
    private static final String[] list={"全部","待完成", "待审核","已完成","未完成"};
    private static final String TAG = "MyMissionSearchFragment";

    private String name;
    private int state;


    private Fragment mFragment;

    public MyMissionSearchFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_mission, container, false);

        init(v);
        mMissionBeginBeginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateTimePicker.selectDateTime(mFragment, mMissionBeginBeginBtn);
            }
        });

        mMissionBeginEndBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DateTimePicker.selectDateTime(mFragment, mMissionBeginEndBtn);
            }
        });

        mMissionEndBeginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mMissionEndBeginBtn);
            }
        });

        mMissionEndEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mMissionEndEndBtn);
            }
        });

        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMissionStateSpinner.setAdapter(adapter);
        mMissionStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mMisiionNameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                name = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

        return v;
    }

    private void init(View v){
        mMisiionNameET = (EditText) v.findViewById(R.id.my_mission_name_edittext);
        mMissionBeginBeginBtn = (Button) v.findViewById(R.id.my_mission_begin_time_button);
        mMissionBeginEndBtn = (Button) v.findViewById(R.id.my_mission_end_time_button);
        mMissionEndBeginBtn = (Button) v.findViewById(R.id.my_mission_end_begin_time_button);
        mMissionEndEndBtn = (Button) v.findViewById(R.id.my_mission_end_end_time_button);
        mMissionStateSpinner = (Spinner) v.findViewById(R.id.my_mission_state_sprinner);

        mSearchBtn = (Button) v.findViewById(R.id.my_mission_search_button);
    }

    private void search() {
        Fragment fragment = new MyMissionFragment();
        Bundle bundle = new Bundle();

        bundle.putString(Link.title, name);

        if (null != mMissionBeginBeginBtn.getText())
            bundle.putInt(Link.start_time_from, DateForGeLingWeiZhi.toGeLinWeiZhi3(mMissionBeginBeginBtn.getText().toString()));
        else bundle.putInt(Link.start_time_from, -1);

        if (null != mMissionBeginEndBtn.getText())
            bundle.putInt(Link.start_time_to, DateForGeLingWeiZhi.toGeLinWeiZhi3(mMissionBeginEndBtn.getText().toString()));
        else bundle.putInt(Link.start_time_to, -1);

        if (null != mMissionEndBeginBtn.getText())
            bundle.putInt(Link.end_time_from, DateForGeLingWeiZhi.toGeLinWeiZhi3(mMissionEndBeginBtn.getText().toString()));
        else bundle.putInt(Link.end_time_from, -1);

        if (null != mMissionEndEndBtn.getText())
            bundle.putInt(Link.end_time_to, DateForGeLingWeiZhi.toGeLinWeiZhi3(mMissionEndEndBtn.getText().toString()));
        else bundle.putInt(Link.end_time_to, -1);

        bundle.putInt(Link.status, state);

        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            transaction.hide(mFragment);
            transaction.add(R.id.blankActivity, fragment);
            transaction.commit();
        } else {
            transaction.hide(mFragment);
            transaction.show(fragment);
            transaction.commit();
        }
    }

}
