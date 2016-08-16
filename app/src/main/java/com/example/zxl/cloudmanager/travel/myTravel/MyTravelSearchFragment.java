package com.example.zxl.cloudmanager.travel.myTravel;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
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
import com.example.zxl.cloudmanager.model.Travel;
import com.example.zxl.cloudmanager.model.TravelLab;

import java.util.ArrayList;
import java.util.Date;

public class MyTravelSearchFragment extends Fragment {

    private Button mBeginTimeBtn;
    private Button mEndTimeBtn;
    private Button mComeBeginBtn;
    private Button mComeEndBtn;

    private Spinner mStateSpinner;

    private ArrayAdapter<String> stateAdapter;
    private static final String[] stateList={"等待","确认","取消"}; //出差状态 0：等待，1：确认，2：取消
    private static final String TAG = "MyTravelSearchFragment";
    private Button mSearchBtn;

    private Date beginTime;
    private String bgtime;
    private Date endTime;
    private String edtime;
    private Date comeBeginTime;
    private String cbgtime;
    private Date comeEndTime;
    private String cedtime;
    private int state;

    private Fragment mFragment;

    public MyTravelSearchFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travelsearch, container, false);
        getActivity().getActionBar().setTitle("出差查询");

        init(view);
        stateAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStateSpinner.setAdapter(stateAdapter);
        mStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //出差状态 0：等待，1：确认，2：取消
                if (stateList[i] == "等待")
                    state = 0;
                if (stateList[i] == "确认")
                    state = 1;
                if (stateList[i] == "取消")
                    state = 2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
        Fragment fragment = new MyTravelFragment();
        Bundle bundle = new Bundle();

        if (null != bgtime) {
            bundle.putInt(Link.start_time_s, DateForGeLingWeiZhi.toGeLinWeiZhi(bgtime));
        } else {
            bundle.putInt(Link.start_time_s, -1);
        }

        if (null != edtime) {
            bundle.putInt(Link.start_time_e, DateForGeLingWeiZhi.toGeLinWeiZhi(edtime));
        } else {
            bundle.putInt(Link.start_time_e, -1);
        }

        if (null != cbgtime) {
            bundle.putInt(Link.over_time_s, DateForGeLingWeiZhi.toGeLinWeiZhi(cbgtime));
        } else {
            bundle.putInt(Link.over_time_s, -1);
        }

        if (null != cedtime) {
            bundle.putInt(Link.over_time_e, DateForGeLingWeiZhi.toGeLinWeiZhi(cedtime));
        } else {
            bundle.putInt(Link.over_time_e, -1);
        }

        bundle.putInt(Link.status, state);

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
    }

    private void init(View v){
        mBeginTimeBtn = (Button) v.findViewById(R.id.employer_travel_begin_time_button);
        mEndTimeBtn = (Button) v.findViewById(R.id.employer_travel_end_time_button);
        mComeBeginBtn = (Button) v.findViewById(R.id.employer_back_begin_time_button);
        mComeEndBtn = (Button) v.findViewById(R.id.employer_back_end_time_button);
        mStateSpinner = (Spinner) v.findViewById(R.id.employer_travel_state_spinner);

        mSearchBtn = (Button) v.findViewById(R.id.my_travel_search_button);
    }

    private void contorl() {
        mBeginTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 12);
                fragment.setTargetFragment(MyTravelSearchFragment.this, 12);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyTravelSearchFragment");
            }
        });


        mEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 13);
                fragment.setTargetFragment(MyTravelSearchFragment.this, 13);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyTravelSearchFragment");
            }
        });

        mComeBeginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 14);
                fragment.setTargetFragment(MyTravelSearchFragment.this, 14);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyTravelSearchFragment");
            }
        });


        mComeEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 15);
                fragment.setTargetFragment(MyTravelSearchFragment.this, 15);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "MyTravelSearchFragment");
            }
        });
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
        } else if (requestCode == 14) {
            comeBeginTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateComeBeginTime();
        } else if (requestCode == 15) {
            comeEndTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateComneEndDate();
        }
    }

    private void updateBeginDate(){
        bgtime = android.text.format.DateFormat.format("yyyy.M.dd", beginTime).toString();
        mBeginTimeBtn.setText(bgtime);
    }
    private void updateEndDate(){
        if (endTime.after(beginTime)) {
            edtime = android.text.format.DateFormat.format("yyyy.M.dd", endTime).toString();
            mEndTimeBtn.setText(edtime);
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void updateComeBeginTime() {
        cbgtime = android.text.format.DateFormat.format("yyyy.M.dd", comeBeginTime).toString();
        mComeBeginBtn.setText(cbgtime);
    }
    private void updateComneEndDate(){
        if (comeEndTime.after(comeBeginTime)) {
            cedtime = android.text.format.DateFormat.format("yyyy.M.dd", comeEndTime).toString();
            mComeEndBtn.setText(cedtime);
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
