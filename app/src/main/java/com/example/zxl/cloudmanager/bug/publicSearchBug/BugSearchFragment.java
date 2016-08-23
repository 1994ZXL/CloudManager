package com.example.zxl.cloudmanager.bug.publicSearchBug;

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
import android.widget.Toast;

import com.example.zxl.cloudmanager.bug.myBug.MyBugActivity;
import com.example.zxl.cloudmanager.bug.myBug.MyBugFragment;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.bug.projectManagerBug.PMBugActivity;
import com.example.zxl.cloudmanager.bug.projectManagerBug.PMBugFragment;
import com.example.zxl.cloudmanager.model.Bug;
import com.example.zxl.cloudmanager.model.BugLab;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.Link;

import java.util.ArrayList;
import java.util.Date;


public class BugSearchFragment extends Fragment {
    private static final String TAG = "BugSearchFragment";

    private Spinner mProjectNameET;
    private Button mBugBeginBtn;
    private Button mBugEndBtn;
    private Button mBugEditBeginBtn;
    private Button mBugEditEndBtn;
    private Spinner mFinderET;
    private Spinner mReviserET;
    private Spinner mBugStateSpinner;
    private Spinner mBugLevelSpinner;

    private LinearLayout mFinderLinearLayout;
    private LinearLayout mReviserLinearLayout;
    private LinearLayout mEditTimeLinearLayout;

    private Button mSearchBtn;

    private ArrayAdapter<String> stateAdapter;
    private ArrayAdapter<String> levelAdapter;
    private ArrayAdapter<String> projectNameAdapter;
    private ArrayAdapter<String> finderAdapter;
    private ArrayAdapter<String> reviserAdapter;

    private static final String stateList[] =
            {"全部","待确认","已排除","不解决","不解决","待修改","待测试","已通过","已完成"};
    private static final String levelList[] =
            {"全部","一级","二级","三级","四级","五级","六级"};
    private ArrayList<String> project_name = new ArrayList<String>();
    private ArrayList<String> submitter = new ArrayList<String>();
    private ArrayList<String> modifier = new ArrayList<String>();

    private String projectName;
    private String finder;
    private String reviser;
    private int bugState;
    private int bugLevel;

    private Date beginTime;
    private String bgtime;

    private Date endTime;
    private String edtime;

    private Date editBeginTime;
    private String editbgtime;

    private Date editEndTime;
    private String editedtime;

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
        getActivity().getActionBar().setTitle("bug查询");

        View v = inflater.inflate(R.layout.fragment_bug_search, container, false);

        init(v);

        if (mFragment.getActivity().getClass() == PublicBugSearchActivity.class) {
            mFinderLinearLayout.setVisibility(View.GONE);
            mReviserLinearLayout.setVisibility(View.GONE);
            mEditTimeLinearLayout.setVisibility(View.GONE);
            mAimFragment = new PMBugFragment();
        } else if (mFragment.getActivity().getClass() == PMBugActivity.class) {
            mFinderLinearLayout.setVisibility(View.GONE);
            mReviserLinearLayout.setVisibility(View.GONE);
            mAimFragment = new PMBugFragment();
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
        mBugBeginBtn = (Button)v.findViewById(R.id.bug_begin_time_button);
        mBugEndBtn = (Button)v.findViewById(R.id.bug_end_time_button);
        mBugEditBeginBtn = (Button) v.findViewById(R.id.bug_edit_begin_time_button);
        mBugEditEndBtn = (Button) v.findViewById(R.id.bug_edit_end_time_button);
        mProjectNameET = (Spinner) v.findViewById(R.id.bug_project_name_edittext);
        mFinderET = (Spinner) v.findViewById(R.id.bug_finder_edittext);
        mReviserET = (Spinner) v.findViewById(R.id.bug_reviser_edittext);
        mBugStateSpinner = (Spinner) v.findViewById(R.id.bug_state_sprinner);
        mBugLevelSpinner = (Spinner) v.findViewById(R.id.bug_level_sprinner);

        mSearchBtn = (Button) v.findViewById(R.id.bug_search_button);

    }

    private void contorl() {
        mBugBeginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 12);
                fragment.setTargetFragment(BugSearchFragment.this, 12);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "BugSearchFragment");
            }
        });

        mBugEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 13);
                fragment.setTargetFragment(BugSearchFragment.this, 13);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "BugSearchFragment");
            }
        });

        mBugEditBeginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 14);
                fragment.setTargetFragment(BugSearchFragment.this, 14);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "BugSearchFragment");
            }
        });

        mBugEditEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 15);
                fragment.setTargetFragment(BugSearchFragment.this, 15);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "BugSearchFragment");
            }
        });

        projectNameAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, project_name);
        projectNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mProjectNameET.setAdapter(stateAdapter);
        mProjectNameET.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                projectName = project_name.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        finderAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, submitter);
        finderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFinderET.setAdapter(stateAdapter);
        mFinderET.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                finder = submitter.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        reviserAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, modifier);
        reviserAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mReviserET.setAdapter(stateAdapter);
        mReviserET.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                reviser = modifier.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        Fragment fragment = new MyBugFragment();
        Bundle bundle = new Bundle();

        bundle.putString(Link.project_name, projectName);

        if (null != bgtime)
            bundle.putInt(Link.submit_time_t, DateForGeLingWeiZhi.toGeLinWeiZhi(bgtime));
        else bundle.putInt(Link.submit_time_t, -1);

        if (null != edtime)
            bundle.putInt(Link.submit_time_f, DateForGeLingWeiZhi.toGeLinWeiZhi(edtime));
        else bundle.putInt(Link.submit_time_f, -1);

        if (null != editbgtime)
            bundle.putInt(Link.modify_time_t, DateForGeLingWeiZhi.toGeLinWeiZhi(editbgtime));
        else bundle.putInt(Link.modify_time_t, -1);

        if (null != editedtime)
            bundle.putInt(Link.modify_time_f, DateForGeLingWeiZhi.toGeLinWeiZhi(editedtime));
        else bundle.putInt(Link.modify_time_f, -1);

        bundle.putString(Link.submitter, finder);

        bundle.putString(Link.modifier, reviser);

        bundle.putInt(Link.status, bugState);

        bundle.putInt(Link.level, bugLevel);

        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            transaction.addToBackStack(null);
            transaction.hide(mFragment);
            transaction.replace(R.id.blankActivity, fragment);
            transaction.commit();
        } else {
            transaction.hide(mFragment);
            transaction.show(fragment);
            transaction.commit();
        }
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
            editBeginTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateEditBeginDate();
        } else if (requestCode == 15) {
            editEndTime = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            updateEditEndDate();
        }
    }

    private void updateBeginDate(){
        bgtime = android.text.format.DateFormat.format("yyyy年MM月dd", beginTime).toString();
        Log.d(TAG, "bgtime: " + bgtime);
        mBugBeginBtn.setText(bgtime);
        Log.d(TAG, "beginTimeButton: " + mBugBeginBtn.getText());
    }
    private void updateEndDate(){
        if (endTime.after(beginTime)) {
            edtime = android.text.format.DateFormat.format("yyyy年MM月dd", endTime).toString();
            Log.d(TAG, "edtime: " + edtime);
            mBugEndBtn.setText(edtime);
            Log.d(TAG, "endTimeButton: " + mBugEndBtn.getText());
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void updateEditBeginDate(){
        editbgtime = android.text.format.DateFormat.format("yyyy年MM月dd", editBeginTime).toString();
        Log.d(TAG, "editbgtime: " + editbgtime);
        mBugEditBeginBtn.setText(editbgtime);
        Log.d(TAG, "mBugEditBeginBtn: " + mBugEditBeginBtn.getText());
    }
    private void updateEditEndDate(){
        if (editEndTime.after(editBeginTime)) {
            editedtime = android.text.format.DateFormat.format("yyyy年MM月dd", editEndTime).toString();
            Log.d(TAG, "editEndTime: " + editedtime);
            mBugEditEndBtn.setText(editedtime);
            Log.d(TAG, "endTimeButton: " + mBugEditEndBtn.getText());
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
