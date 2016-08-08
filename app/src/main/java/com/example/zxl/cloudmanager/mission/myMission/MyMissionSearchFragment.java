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
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.Mission;
import com.example.zxl.cloudmanager.model.MissionLab;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMissionSearchFragment extends Fragment {
    private EditText mMisiionNameET;
    private Button mMissionBeginBtn;
    private Button mMissionEndBtn;
    private Spinner mMissionStateSpinner;

    private Button mSearchBtn;

    private ArrayAdapter<String> adapter;
    private static final String[] list={"全部","待完成", "待审核","已完成","未完成"};
    private static final String TAG = "MyMissionSearchFragment";

    private String name;
    private Date beginTime;
    private String bgtime;
    private Date endTime;
    private String edtime;
    private String state;

    private ArrayList<Mission> mMissions = new ArrayList<Mission>();
    private int index;
    private static final String SEARCH_KEY = "search_key";

    private Fragment mFragment;

    public MyMissionSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_mission, container, false);
        getActivity().getActionBar().setTitle("任务查询");
        init(v);
        mMissionBeginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 12);
                fragment.setTargetFragment(MyMissionSearchFragment.this, 12);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyMissionSearchFragment");
            }
        });

        mMissionEndBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 13);
                fragment.setTargetFragment(MyMissionSearchFragment.this, 13);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyMissionSearchFragment");
            }
        });

        adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMissionStateSpinner.setAdapter(adapter);
        mMissionStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state = list[i];
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
        mMissionBeginBtn = (Button) v.findViewById(R.id.my_mission_begin_time_button);
        mMissionEndBtn = (Button) v.findViewById(R.id.my_mission_end_time_button);
        mMissionStateSpinner = (Spinner) v.findViewById(R.id.my_mission_state_sprinner);

        mSearchBtn = (Button) v.findViewById(R.id.my_mission_search_button);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "进入回调 " + " resultCode:" + requestCode);
        if (resultCode != Activity.RESULT_OK){
            Log.d(TAG, "未进入判断");
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
        mMissionBeginBtn.setText(bgtime);
    }
    private void updateEndDate(){
        if (endTime.after(beginTime)) {
            edtime = android.text.format.DateFormat.format("yyyy.M.dd", endTime).toString();
            mMissionEndBtn.setText(edtime);
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void search() {
        mMissions = MissionLab.newInstance(mFragment.getActivity()).get();
        for (index = 0; index < mMissions.size(); index++) {
            if (name.equals(mMissions.get(index).getName())
                    && bgtime.equals(mMissions.get(index).getStart_time())
                    && edtime.equals(mMissions.get(index).getOver_time())
                    && state.equals(mMissions.get(index).getStatus())) {
                Fragment fragment = new MyMissionFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(SEARCH_KEY, index);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (!fragment.isAdded()) {
                    transaction.hide(mFragment);
                    transaction.replace(R.id.blankActivity, fragment);
                    transaction.commit();
                } else {
                    transaction.hide(mFragment);
                    transaction.show(fragment);
                    transaction.commit();
                }
            } else {

            }
        }
    }

}
