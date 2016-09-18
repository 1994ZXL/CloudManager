package com.example.zxl.cloudmanager.bug.publicSearchBug;

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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zxl.cloudmanager.bug.myBug.MyBugActivity;
import com.example.zxl.cloudmanager.bug.myBug.MyBugFragment;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.bug.projectManagerBug.PMBugActivity;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DateTimePicker;
import com.example.zxl.cloudmanager.model.Link;


public class BugSearchFragment extends Fragment {
    private static final String TAG = "BugSearchFragment";

    private EditText mProjectNameET;
    private TextView mBugBeginBtn;
    private TextView mBugEndBtn;
    private TextView mBugEditBeginBtn;
    private TextView mBugEditEndBtn;
    private EditText mFinderET;
    private EditText mReviserET;
    private Spinner mBugStateSpinner;
    private Spinner mBugLevelSpinner;

    private LinearLayout mFinderLinearLayout;
    private LinearLayout mReviserLinearLayout;
    private LinearLayout mEditTimeLinearLayout;

    private Button mSearchBtn;
    private TextView mBack;

    private ArrayAdapter<String> stateAdapter;
    private ArrayAdapter<String> levelAdapter;

    private static final String stateList[] =
            {"全部","待确认","已排除","不解决","不解决","待修改","待测试","已通过","已完成"};
    private static final String levelList[] =
            {"全部","一级","二级","三级","四级","五级","六级"};

    private String projectName;
    private String finder;
    private String reviser;
    private int bugState;
    private int bugLevel;

    private Fragment mFragment;
    private Fragment mAimFragment;

    public BugSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_bug_search, container, false);

        init(v);

        if (mFragment.getActivity().getClass() == PublicBugSearchActivity.class) {
            mFinderLinearLayout.setVisibility(View.GONE);
            mReviserLinearLayout.setVisibility(View.GONE);
            mEditTimeLinearLayout.setVisibility(View.GONE);
            mAimFragment = new MyBugFragment();
        } else if (mFragment.getActivity().getClass() == PMBugActivity.class) {
            mFinderLinearLayout.setVisibility(View.GONE);
            mReviserLinearLayout.setVisibility(View.GONE);
            mAimFragment = new MyBugFragment();
        } else if (mFragment.getActivity().getClass() == MyBugActivity.class) {
            mAimFragment = new MyBugFragment();
        }

        contorl();

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        return v;
    }

    private void init(View v){
        mFinderLinearLayout = (LinearLayout) v.findViewById(R.id.bug_finder_linearLayout);
        mReviserLinearLayout = (LinearLayout) v.findViewById(R.id.bug_reviser_linearLayout);
        mEditTimeLinearLayout = (LinearLayout) v.findViewById(R.id.bug_editTime_linearLayout);
        mBugBeginBtn = (TextView)v.findViewById(R.id.bug_begin_time_button);
        mBugEndBtn = (TextView)v.findViewById(R.id.bug_end_time_button);
        mBugEditBeginBtn = (TextView) v.findViewById(R.id.bug_edit_begin_time_button);
        mBugEditEndBtn = (TextView) v.findViewById(R.id.bug_edit_end_time_button);
        mProjectNameET = (EditText) v.findViewById(R.id.bug_project_name_edittext);
        mFinderET = (EditText) v.findViewById(R.id.bug_finder_edittext);
        mReviserET = (EditText) v.findViewById(R.id.bug_reviser_edittext);
        mBugStateSpinner = (Spinner) v.findViewById(R.id.bug_state_sprinner);
        mBugLevelSpinner = (Spinner) v.findViewById(R.id.bug_level_sprinner);
        mBack = (TextView) v.findViewById(R.id.bug_search_back);
        mSearchBtn = (Button) v.findViewById(R.id.bug_search_button);

    }

    private void contorl() {
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mBugBeginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mBugBeginBtn);
            }
        });

        mBugEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mBugEndBtn);
            }
        });

        mBugEditBeginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mBugEditBeginBtn);
            }
        });

        mBugEditEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateTimePicker.selectDateTime(mFragment, mBugEditEndBtn);
            }
        });

        mProjectNameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                projectName = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mFinderET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                finder = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mReviserET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                reviser = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        stateAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBugStateSpinner.setAdapter(stateAdapter);
        mBugStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bugState = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        levelAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, levelList);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBugLevelSpinner.setAdapter(levelAdapter);
        mBugLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bugLevel = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void search() {
        Bundle bundle = new Bundle();

        bundle.putString(Link.project_name, projectName);

        if (null != mBugBeginBtn.getText())
            bundle.putInt(Link.submit_time_from, DateForGeLingWeiZhi.toGeLinWeiZhi3(mBugBeginBtn.getText().toString()));
        else bundle.putInt(Link.submit_time_from, -1);

        if (null != mBugEndBtn.getText())
            bundle.putInt(Link.submit_time_to, DateForGeLingWeiZhi.toGeLinWeiZhi3(mBugEndBtn.getText().toString()));
        else bundle.putInt(Link.submit_time_to, -1);

        if (null != mBugEditBeginBtn.getText())
            bundle.putInt(Link.mofify_time_from, DateForGeLingWeiZhi.toGeLinWeiZhi3(mBugEditBeginBtn.getText().toString()));
        else bundle.putInt(Link.mofify_time_from, -1);

        if (null != mBugEditEndBtn.getText())
            bundle.putInt(Link.modify_time_to, DateForGeLingWeiZhi.toGeLinWeiZhi3(mBugEditEndBtn.getText().toString()));
        else bundle.putInt(Link.modify_time_to, -1);

        bundle.putString(Link.submitter, finder);

        bundle.putString(Link.modifier, reviser);

        bundle.putInt(Link.status, bugState);

        bundle.putInt(Link.level, bugLevel);

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
}
