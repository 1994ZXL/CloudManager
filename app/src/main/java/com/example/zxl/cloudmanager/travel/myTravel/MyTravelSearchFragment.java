package com.example.zxl.cloudmanager.travel.myTravel;

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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zxl.cloudmanager.Edit;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.DateTimePicker;
import com.example.zxl.cloudmanager.model.Link;
import com.example.zxl.cloudmanager.model.Travel;
import com.example.zxl.cloudmanager.model.TravelLab;
import com.example.zxl.cloudmanager.travel.checkManagerTravel.ManagerTravelActivity;
import com.example.zxl.cloudmanager.travel.checkManagerTravel.ManagerTravelAddFragment;
import com.example.zxl.cloudmanager.travel.checkManagerTravel.ManagerTravelListFragment;
import com.example.zxl.cloudmanager.travel.leader.LeaderTravelSearchActivity;

import java.util.ArrayList;
import java.util.Date;

public class MyTravelSearchFragment extends Fragment {

    private EditText mName;
    private TextView mBeginTimeBtn;
    private TextView mEndTimeBtn;
    private TextView mComeBeginBtn;
    private TextView mComeEndBtn;
    private LinearLayout mNameEditText;

    private Spinner mStateSpinner;

    private TextView mAddTextView;
    private TextView mBack;

    private ArrayAdapter<String> stateAdapter;
    private static final String[] stateList={"全部","确认","取消"}; //出差状态 2：确认，3：取消
    private static final String TAG = "MyTravelSearchFragment";
    private Button mSearchBtn;

    private String name;

    private int state;

    private Fragment mFragment;

    private Fragment mAimFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travelsearch, container, false);


        init(view);

        if (mFragment.getActivity().getClass() == MyTravelActivity.class) {
            mNameEditText.setVisibility(View.GONE);
            mAddTextView.setVisibility(View.INVISIBLE);
            mAimFragment = new MyTravelFragment();
        } else if (mFragment.getActivity().getClass() == ManagerTravelActivity.class) {
            mAimFragment = new ManagerTravelListFragment();
            mAddTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = new ManagerTravelAddFragment();
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
                }
            });
        } else if (mFragment.getActivity().getClass() == LeaderTravelSearchActivity.class) {
            mAimFragment = new ManagerTravelListFragment();
            mAddTextView.setVisibility(View.INVISIBLE);
        }



        contorl();
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        return view;
    }

    private void search() {
        Bundle bundle = new Bundle();
        if (null != name)
            bundle.putString(Link.mem_name, name);

        if (null != mBeginTimeBtn.getText()) {
            bundle.putInt(Link.start_time_s, DateForGeLingWeiZhi.toGeLinWeiZhi(mBeginTimeBtn.getText().toString()));
        } else {
            bundle.putInt(Link.start_time_s, -1);
        }

        if (null != mEndTimeBtn.getText()) {
            bundle.putInt(Link.start_time_e, DateForGeLingWeiZhi.toGeLinWeiZhi(mEndTimeBtn.getText().toString()));
        } else {
            bundle.putInt(Link.start_time_e, -1);
        }

        if (null != mComeBeginBtn.getText()) {
            bundle.putInt(Link.over_time_s, DateForGeLingWeiZhi.toGeLinWeiZhi(mComeBeginBtn.getText().toString()));
        } else {
            bundle.putInt(Link.over_time_s, -1);
        }

        if (null != mComeEndBtn.getText()) {
            bundle.putInt(Link.over_time_e, DateForGeLingWeiZhi.toGeLinWeiZhi(mComeEndBtn.getText().toString()));
        } else {
            bundle.putInt(Link.over_time_e, -1);
        }

        bundle.putInt(Link.status, state);

        mAimFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (!mAimFragment.isAdded()) {
            transaction.hide(mFragment);
            transaction.replace(R.id.blankActivity, mAimFragment);
            transaction.commit();
        } else {
            transaction.hide(mFragment);
            transaction.show(mAimFragment);
            transaction.commit();
        }
    }

    private void init(View v){
        mNameEditText = (LinearLayout) v.findViewById(R.id.fragment_travel_nameEditText);
        mName = (EditText) v.findViewById(R.id.cm_employer_name_edittext);
        mBeginTimeBtn = (TextView) v.findViewById(R.id.employer_travel_begin_time_button);
        mEndTimeBtn = (TextView) v.findViewById(R.id.employer_travel_end_time_button);
        mComeBeginBtn = (TextView) v.findViewById(R.id.employer_back_begin_time_button);
        mComeEndBtn = (TextView) v.findViewById(R.id.employer_back_end_time_button);
        mStateSpinner = (Spinner) v.findViewById(R.id.employer_travel_state_spinner);

        mAddTextView = (TextView) v.findViewById(R.id.manager_travel_add);
        mSearchBtn = (Button) v.findViewById(R.id.my_travel_search_button);
        mBack = (TextView) v.findViewById(R.id.travel_search_back);
    }

    private void contorl() {
        mName.addTextChangedListener(new TextWatcher() {
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

        mBeginTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDate(mFragment, mBeginTimeBtn);
            }
        });


        mEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDate(mFragment, mEndTimeBtn);
            }
        });

        mComeBeginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDate(mFragment, mComeBeginBtn);
            }
        });


        mComeEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDate(mFragment, mComeEndBtn);
            }
        });

        stateAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStateSpinner.setAdapter(stateAdapter);
        mStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //出差状态 0：等待，1：确认，2：取消
                if (stateList[i] == "全部")
                    state = 0;
                if (stateList[i] == "确认")
                    state = 2;
                if (stateList[i] == "取消")
                    state = 3;
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
    }
}
