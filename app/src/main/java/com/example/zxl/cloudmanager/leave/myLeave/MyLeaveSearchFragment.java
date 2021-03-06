package com.example.zxl.cloudmanager.leave.myLeave;

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

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.leave.checkManagerLeave.ManagerLeaveActivity;
import com.example.zxl.cloudmanager.leave.checkManagerLeave.ManagerLeaveListFragment;
import com.example.zxl.cloudmanager.leave.leader.LeaderLeaveSearchActivity;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.DateTimePicker;
import com.example.zxl.cloudmanager.model.Link;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.Date;

//我的请假 领导查询 请假查询界面
public class MyLeaveSearchFragment extends Fragment {
    private static final String TAG = "MyLeaveSearchFragment";

    private EditText mName;
    private TextView mLeaveBeginBtn;
    private TextView mLeaveEndBtn;
    private Spinner mLeaveKindSpinner;
    private Spinner mLeaveStateSpinner;
    private LinearLayout mNameLinearLayout;

    private int mState;
    private int mType;

    private Button mSearchBtn;

    private TextView mBack;

    private ArrayAdapter<String> stateAdapter;
    //状态:1:待批准,2:已批准,3:拒绝
    private static final String[] stateList={"全部", "待批准","已批准","拒绝"};

    private ArrayAdapter<String> kindAdapter;
    //请假类型:1事假,2病假,3休假,4婚假,5其他
    private static final String[] kindList={"全部", "事假", "病假", "休假", "婚假", "其他"};

    private static AsyncHttpClient mHttpc = new AsyncHttpClient();
    private RequestParams mParams = new RequestParams();
    private JSONObject keyObj = new JSONObject();
    private String key = "";

    private String name;

    private String url;

    private Fragment mFragment;

    private Activity mActivity;

    private Fragment mAimFragment;

    public MyLeaveSearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_leavesearch, container, false);


        init(v);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        if (mFragment.getActivity().getClass() == MyLeaveSearchActivity.class) {
            mNameLinearLayout.setVisibility(View.GONE);
            url = Link.my_leave + Link.get_list;
            mAimFragment = new MyLeaveQueryFragment();
        } else if (mFragment.getActivity().getClass() == LeaderLeaveSearchActivity.class) {
            url = Link.leave_list + Link.get_list;
            mAimFragment = new ManagerLeaveListFragment();
        } else if (mFragment.getActivity().getClass() == ManagerLeaveActivity.class) {
            url = Link.manage_leave + Link.get_list;
            mAimFragment = new ManagerLeaveListFragment();
        }

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

        mLeaveBeginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mLeaveBeginBtn);
            }
        });

        mLeaveEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mLeaveEndBtn);
            }
        });

        stateAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLeaveStateSpinner.setAdapter(stateAdapter);
        mLeaveStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //状态:1:待批准,2:已批准,3:拒绝
                if (stateList[i] == "全部")
                    mState = 0;
                if (stateList[i] == "待批准")
                    mState = 1;
                if (stateList[i] == "已批准")
                    mState = 2;
                if (stateList[i] == "拒绝")
                    mState = 3;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        kindAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, kindList);
        kindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLeaveKindSpinner.setAdapter(kindAdapter);
        mLeaveKindSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //请假类型:1事假,2病假,3休假,4婚假,5其他
                if (kindList[i] == "全部")
                    mType = 0;
                if (kindList[i] == "事假")
                    mType = 1;
                if (kindList[i] == "病假")
                    mType = 2;
                if (kindList[i] == "休假")
                    mType = 3;
                if (kindList[i] == "婚假")
                    mType = 4;
                if (kindList[i] == "其他")
                    mType = 5;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Search();
            }
        });
        return v;
    }

    private void Search() {
        Bundle bundle = new Bundle();

        if (null != name)
            bundle.putString(Link.mem_name, name);

        if (null != mLeaveBeginBtn.getText())
            bundle.putInt(Link.start_time, DateForGeLingWeiZhi.toGeLinWeiZhi3(mLeaveBeginBtn.getText().toString()));
        else bundle.putInt(Link.start_time, -1);

        if (null != mLeaveEndBtn.getText())
            bundle.putInt(Link.end_time, DateForGeLingWeiZhi.toGeLinWeiZhi3(mLeaveEndBtn.getText().toString()));
        else bundle.putInt(Link.end_time, -1);

        if (mType == 0) {
            bundle.putInt(Link.leave_type, 0);
        } else {
            bundle.putInt(Link.leave_type, mType);
        }

        if (mState == 0) {
            bundle.putInt(Link.status, 0);
        } else {
            bundle.putInt(Link.status, mState);
        }

        mAimFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (!mAimFragment.isAdded()) {
            transaction.addToBackStack(null);
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
        mNameLinearLayout = (LinearLayout) v.findViewById(R.id.leave_search_nameLinearLayout);
        mName = (EditText) v.findViewById(R.id.my_leave_name);
        mLeaveBeginBtn = (TextView) v.findViewById(R.id.my_leave_begin_time_button);
        mLeaveEndBtn = (TextView) v.findViewById(R.id.my_leave_end_time_button);
        mLeaveKindSpinner = (Spinner) v.findViewById(R.id.my_leave_kind_sprinner);
        mLeaveStateSpinner = (Spinner) v.findViewById(R.id.my_leave_state_sprinner);

        mSearchBtn = (Button) v.findViewById(R.id.my_leave_search_button);
        mBack = (TextView) v.findViewById(R.id.leave_search_back);
    }

}
