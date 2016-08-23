package com.example.zxl.cloudmanager.bug.projectManagerBug;

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
import com.example.zxl.cloudmanager.model.Bug;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.DatePickerFragment;
import com.example.zxl.cloudmanager.model.Link;

import java.util.ArrayList;
import java.util.Date;

//被弃用
public class PMBugSearchFragment extends Fragment {
    private  static ArrayList<Bug> sBugs = new ArrayList<Bug>();
    private static final String SEARCH_KEY = "search_key";
    private static final String TAG = "BugSearchFragment";

    private EditText mProjectNameET;
    private Button mBugBeginBtn;
    private Button mBugEndBtn;
    private EditText mFinderET;
    private EditText mReviserET;
    private Spinner mBugStateSpinner;
    private Spinner mBugLevelSpinner;

    private Button mSearchBtn;
    private ArrayAdapter<String> stateAdapter;
    private ArrayAdapter<String> levelAdapter;

    private static final String stateList[] =
            {"全部","待确认","待修改","待测试","已通过","已排除","不解决"}; //状态 1:一级 2:二级 3:三级 4:四级 5:五级 6:六级
    private static final String levelList[] =
            {"全部","一级","二级","三级","四级","五级","六级"}; //状态 1:待确认 2:已排除 3:不解决 4:待修改 5:待测试 6:已通过 7:已修改

    private String projectName;
    private String finder;
    private String reviser;
    private String bugState;
    private int status;
    private String bugLevel;
    private int level;

    private Date beginTime;
    private String bgtime;

    private Date endTime;
    private String edtime;

    private int index;
    private Fragment mFragment;

    public PMBugSearchFragment() {
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

        stateAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBugStateSpinner.setAdapter(stateAdapter);

        levelAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, levelList);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBugLevelSpinner.setAdapter(levelAdapter);

        contorl();

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PMBugFragment();
                Bundle bundle = new Bundle();

                if (null != bgtime) {
                    bundle.putInt(Link.start_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(bgtime));
                } else {
                    bundle.putInt(Link.start_time, -1);
                }
                if (null != edtime) {
                    bundle.putInt(Link.end_time, DateForGeLingWeiZhi.newInstance().toGeLinWeiZhi(edtime));
                } else {
                    bundle.putInt(Link.end_time, -1);
                }


                fragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.blankActivity, fragment);
                transaction.commit();
            }
        });

        return v;
    }

    private void init(View v){

        mBugBeginBtn = (Button)v.findViewById(R.id.bug_begin_time_button);
        mBugEndBtn = (Button)v.findViewById(R.id.bug_end_time_button);
        mProjectNameET = (EditText) v.findViewById(R.id.bug_project_name_edittext);
        mFinderET = (EditText) v.findViewById(R.id.bug_finder_edittext);
        mReviserET = (EditText) v.findViewById(R.id.bug_reviser_edittext);
        mBugStateSpinner = (Spinner) v.findViewById(R.id.bug_state_sprinner);
        mBugLevelSpinner = (Spinner) v.findViewById(R.id.bug_level_sprinner);

        mSearchBtn = (Button) v.findViewById(R.id.bug_search_button);

    }

    private void contorl() {
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
        mBugStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //等级 1:待确认 2:已排除 3:不解决 4:待修改 5:待测试 6:已通过 7:已修改
                bugState = stateList[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mBugLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //状态 1:一级 2:二级 3:三级 4:四级 5:五级 6:六级
                bugLevel = levelList[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mBugBeginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 12);
                fragment.setTargetFragment(PMBugSearchFragment.this, 12);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "PMBugSearchFragment");
            }
        });
        mBugEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment fragment = DatePickerFragment.newInstance(new Date(), 13);
                fragment.setTargetFragment(PMBugSearchFragment.this, 13);
                fragment.setStyle(DialogFragment.STYLE_NO_FRAME, 1);
                fragment.show(getFragmentManager(), "PMBugSearchFragment");
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
        }
    }

    private void updateBeginDate(){
        bgtime = android.text.format.DateFormat.format("yyyy年MM月dd", beginTime).toString();
        Log.d(TAG, "bgtime: " + bgtime);
        mBugBeginBtn.setText(bgtime);
        Log.d(TAG, "mBugBeginBtn: " + mBugBeginBtn.getText());
    }
    private void updateEndDate(){
        if (endTime.after(beginTime)) {
            edtime = android.text.format.DateFormat.format("yyyy年MM月dd", endTime).toString();
            Log.d(TAG, "edtime: " + edtime);
            mBugEndBtn.setText(edtime);
            Log.d(TAG, "mBugEndBtn: " + mBugEndBtn.getText());
        } else {
            Toast.makeText(getActivity(),
                    R.string.time_erro,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
