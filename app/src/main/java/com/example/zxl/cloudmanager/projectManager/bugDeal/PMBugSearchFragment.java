package com.example.zxl.cloudmanager.projectManager.bugDeal;

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
import android.widget.Spinner;

import com.example.zxl.cloudmanager.MyBugFragment;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.Bug;
import com.example.zxl.cloudmanager.model.BugLab;

import java.util.ArrayList;


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
            {"全部","待确认","待修改","待测试","已通过","已排除","不解决"};
    private static final String levelList[] =
            {"全部","一级","二级","三级","四级","五级","六级"};

    private String projectName;
    private String finder;
    private String reviser;
    private String bugState;
    private String bugLevel;

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
                /*Bundle bundle = new Bundle();
                bundle.putInt(SEARCH_KEY, index);
                fragment.setArguments(bundle);*/
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
                bugState = stateList[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mBugLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bugLevel = levelList[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /*private void search() {
        sBugs = BugLab.newInstance(mFragment.getActivity()).get();
        for (index = 0; index < sBugs.size(); index++ ) {
            if (projectName.equals(sBugs.get(index).getFunctionModel()) && finder.equals(sBugs.get(index).getFoundMan())
                    && reviser.equals(sBugs.get(index).getEditMan()) && bugState.equals(sBugs.get(index).getStatus())
                    && bugLevel.equals(sBugs.get(index).getBugVersion())) {
                Fragment fragment = new MyBugFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(SEARCH_KEY, index);
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
        }
    }*/
}
