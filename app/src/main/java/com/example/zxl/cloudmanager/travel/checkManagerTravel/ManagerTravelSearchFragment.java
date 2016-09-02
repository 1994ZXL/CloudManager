package com.example.zxl.cloudmanager.travel.checkManagerTravel;

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
import com.example.zxl.cloudmanager.model.Link;

import java.util.Date;

//被弃用
public class ManagerTravelSearchFragment extends Fragment {
    private static final String TAG = "MTSFragment";

    private Button mBeginTimeBtn;
    private Button mEndTimeBtn;
    private Button mComeBeginBtn;
    private Button mComeEndBtn;
    private EditText mEmployerNameET;

    private Spinner mStateSpinner;
    private Fragment mFragment;

    private String name;
    private Date beginTime;
    private String bgtime;
    private Date endTime;
    private String edtime;
    private Date comeBeginTime;
    private String cbgTime;
    private Date comeEndTime;
    private String cedtime;
    private String state;
    private int status;


    private ArrayAdapter<String> stateAdapter;
    private static final String[] stateList={"未选择","确认","取消"};//出差状态 2：确认，3：取消
    private Button mSearchBtn;

    public ManagerTravelSearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travelsearch, container, false);


        init(view);
        stateAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStateSpinner.setAdapter(stateAdapter);
        mStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //出差状态 2：确认，3：取消
                state = stateList[i];
                if (state == "确认")
                    status = 2;
                if (state == "取消")
                    status = 3;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mEmployerNameET.addTextChangedListener(new TextWatcher() {
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
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 12);
                fragment.setTargetFragment(ManagerTravelSearchFragment.this, 12);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "ManagerTravelSearchFragment");
            }
        });
        mEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 13);
                fragment.setTargetFragment(ManagerTravelSearchFragment.this, 13);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "ManagerTravelSearchFragment");
            }
        });
        mComeBeginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 14);
                fragment.setTargetFragment(ManagerTravelSearchFragment.this, 14);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "ManagerTravelSearchFragment");
            }
        });
        mComeEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 15);
                fragment.setTargetFragment(ManagerTravelSearchFragment.this, 15);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "ManagerTravelSearchFragment");
            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Fragment fragment = new ManagerTravelListFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Link.mem_name, name);
                if (null != bgtime) {
                    bundle.putInt(Link.start_time_s, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(bgtime));
                } else {
                    bundle.putInt(Link.start_time_s, -1);
                }
                if (null != edtime) {
                    bundle.putInt(Link.start_time_e, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(edtime));
                } else {
                    bundle.putInt(Link.start_time_e, -1);
                }
                if (null != cbgTime) {
                    bundle.putInt(Link.over_time_s, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(cbgTime));
                } else {
                    bundle.putInt(Link.over_time_s, -1);
                }
                if (null != cedtime) {
                    bundle.putInt(Link.over_time_e, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(cedtime));
                } else {
                    bundle.putInt(Link.over_time_e, -1);
                }
                bundle.putInt(Link.status, status);
                Log.d(TAG, "选择条件："
                        + " mem_name: " + name
                        + " start_time_s: " + bgtime
                        + " start_time_e: " + edtime
                        + " over_time_s: " + cbgTime
                        + " over_time_e: " + cedtime
                        + " status: " + status);
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.blankActivity, fragment);
                transaction.commit();
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
            updateComeBeginDate();
        } else if (requestCode == 15) {
            comeEndTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateComeEndDate();
        }
    }

    private void updateBeginDate(){
        bgtime = android.text.format.DateFormat.format("yyyy年MM月dd", beginTime).toString();
        Log.d(TAG, "bgtime: " + bgtime);
        mBeginTimeBtn.setText(bgtime);
        Log.d(TAG, "beginTimeButton: " + mBeginTimeBtn.getText());
    }
    private void updateEndDate(){
        if (endTime.after(beginTime)) {
            edtime = android.text.format.DateFormat.format("yyyy年MM月dd", endTime).toString();
            Log.d(TAG, "edtime: " + edtime);
            mEndTimeBtn.setText(edtime);
            Log.d(TAG, "endTimeButton: " + mEndTimeBtn.getText());
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void updateComeBeginDate(){
        cbgTime = android.text.format.DateFormat.format("yyyy年MM月dd", comeBeginTime).toString();
        Log.d(TAG, "cbgTime: " + cbgTime);
        mComeBeginBtn.setText(cbgTime);
        Log.d(TAG, "mComeBeginBtn: " + mComeBeginBtn.getText());
    }
    private void updateComeEndDate(){
        if (comeEndTime.after(comeBeginTime)) {
            cedtime = android.text.format.DateFormat.format("yyyy年MM月dd", comeEndTime).toString();
            Log.d(TAG, "cedtime: " + cedtime);
            mComeEndBtn.setText(cedtime);
            Log.d(TAG, "mComeEndBtn: " + mComeEndBtn.getText());
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
