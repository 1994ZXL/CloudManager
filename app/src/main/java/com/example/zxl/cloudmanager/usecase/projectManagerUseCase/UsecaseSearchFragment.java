package com.example.zxl.cloudmanager.usecase.projectManagerUseCase;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;

public class UsecaseSearchFragment extends Fragment {

    private static final String TAG = "PMUsecaseSearch";
    private EditText mProjectNameET;
    private EditText mUsecaseIdET;
    private EditText mProgramNumberET;
    private EditText mTesterET;
    private EditText mDeveloperET;
    private TextView mUsecaseBeginBtn;
    private TextView mUsecaseEndBtn;

    private Button mSearchBtn;

    private Fragment mFragment;

    public UsecaseSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"UasecaseSearch: 实现了" );
        mFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_usecase_public, container, false);

        init(v);
        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.d(TAG,"searchButton : 被点击了" );
                Fragment fragment = new PMUseCaseFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (!fragment.isAdded()) {
                    transaction.addToBackStack(null);
                    transaction.hide(mFragment);
                    transaction.add(R.id.blankActivity, fragment);
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
        mUsecaseBeginBtn = (TextView)v.findViewById(R.id.usecase_begin_time_button);
        mUsecaseEndBtn = (TextView)v.findViewById(R.id.usecase_end_time_button);
        mProjectNameET = (EditText) v.findViewById(R.id.usecase_project_edittext);
        mUsecaseIdET = (EditText) v.findViewById(R.id.usecase_id_edittext);
        mProgramNumberET = (EditText) v.findViewById(R.id.usecase_program_number);
        mTesterET = (EditText) v.findViewById(R.id.usecase_tester);
        mDeveloperET = (EditText) v.findViewById(R.id.usecase_developer);

        mSearchBtn = (Button) v.findViewById(R.id.usecase_search_button);

    }
}
