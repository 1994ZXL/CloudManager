package com.example.zxl.cloudmanager.usecase.publicSearchUseCase;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.zxl.cloudmanager.usecase.myUseCase.MyUseCaseFragment;
import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.UseCase;
import com.example.zxl.cloudmanager.model.UseCaseLab;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsecaseFragment extends Fragment {

    private EditText mProjectNameET;
    private EditText mUsecaseIdET;
    private EditText mProgramNumberET;
    private EditText mTesterET;
    private EditText mDeveloperET;
    private Button mUsecaseBeginBtn;
    private Button mUsecaseEndBtn;

    private Button mSearchBtn;

    private String name;
    private String useCaseId;
    private String programNumber;
    private String tester;
    private String developer;

    private  static ArrayList<UseCase> sUsecase = new ArrayList<UseCase>();
    private static final String SEARCH_KEY = "search_key";
    private static final String TAG = "UsecaseFragment";

    private Fragment mFragment;

    private int index;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        this.mFragment = this;
    }

    public UsecaseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_usecase_public, container, false);
        init(v);
        contorl();

        mSearchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                search();
            }
        });
        return v;
    }

    private void init(View v){
        mUsecaseBeginBtn = (Button)v.findViewById(R.id.usecase_begin_time_button);
        mUsecaseEndBtn = (Button)v.findViewById(R.id.usecase_end_time_button);
        mProjectNameET = (EditText) v.findViewById(R.id.usecase_project_edittext);
        mUsecaseIdET = (EditText) v.findViewById(R.id.usecase_id_edittext);
        mProgramNumberET = (EditText) v.findViewById(R.id.usecase_program_number);
        mTesterET = (EditText) v.findViewById(R.id.usecase_tester);
        mDeveloperET = (EditText) v.findViewById(R.id.usecase_developer);

        mSearchBtn = (Button) v.findViewById(R.id.usecase_search_button);
    }

    private void contorl() {
        mProjectNameET.addTextChangedListener(new TextWatcher() {
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
        mUsecaseIdET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                useCaseId = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mProgramNumberET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                programNumber = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mTesterET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tester = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mDeveloperET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                developer = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void search() {
        sUsecase = UseCaseLab.newInstance(mFragment.getActivity()).getUseCase();
        for (index = 0; index < sUsecase.size(); index++ ) {
            if (name.equals(sUsecase.get(index).getName()) && useCaseId.equals(sUsecase.get(index).getTest_content())
                    && programNumber.equals(sUsecase.get(index).getVersionNumber()) && tester.equals(sUsecase.get(index).getTestter_name())
                    && developer.equals(sUsecase.get(index).getSubmitter_name())) {
                Fragment fragment = new MyUseCaseFragment();
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
            } else {
                Log.d(TAG, name + "   " + sUsecase.get(index).getName());
                Log.d(TAG, useCaseId + "   " + sUsecase.get(index).getTest_content());
                Log.d(TAG, programNumber + "   " + sUsecase.get(index).getVersionNumber());
                Log.d(TAG, tester + "   " + sUsecase.get(index).getTestter_name());
                Log.d(TAG, developer + "   " + sUsecase.get(index).getSubmitter_name());
            }
        }
    }

}
