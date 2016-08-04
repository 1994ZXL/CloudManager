package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxl.cloudmanager.model.UseCase;


/**
 * Created by ZXL on 2016/7/13.
 */
public class MyUseCaseDetailFragment extends Fragment {
    private static UseCase sUseCase = new UseCase();

    private TextView mName;
    private TextView mFunctionModel;
    private TextView mUsecaseNumber;
    private TextView mVersionNumber;
    private TextView mAutorizedMan;
    private TextView mAutorizedTime;
    private TextView mCorrelationUseCase;
    private TextView mFunctionCharacter;
    private TextView mTextAim;
    private TextView mPresetValue;
    private TextView mReferenceInformation;
    private TextView mTextDate;
    private TextView mScene;
    private TextView mOperationSequence;
    private TextView mOperationDescription;
    private TextView mDate;
    private TextView mExpectedOutcome;
    private TextView mParticalOutcome;
    private TextView mTextState;
    private TextView mTestMan;
    private TextView mExploitMan;
    private TextView mPrincipal;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

    }

    public static MyUseCaseDetailFragment newInstance(Object data) {
        sUseCase = (UseCase) data;
        MyUseCaseDetailFragment fragment = new MyUseCaseDetailFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.usecase_details, parent, false);

        init(view);
        contorl();

        return view;
    }

    private void init(View view) {
        mName = (TextView)view.findViewById(R.id.usecase_details_program_number);
        mFunctionModel = (TextView)view.findViewById(R.id.usecase_details_functionModule);
        mUsecaseNumber = (TextView)view.findViewById(R.id.usecase_details_id);
        mVersionNumber = (TextView)view.findViewById(R.id.usecase_details_program_number);
        mAutorizedMan = (TextView)view.findViewById(R.id.usecase_details_autorized_strength);
        mAutorizedTime = (TextView)view.findViewById(R.id.usecase_details_formation_time);
        mCorrelationUseCase = (TextView)view.findViewById(R.id.usecase_details_correlation_usecase);
        mFunctionCharacter = (TextView)view.findViewById(R.id.usecase_details_function_character);
        mTextAim = (TextView)view.findViewById(R.id.usecase_details_text_aim);
        mPresetValue = (TextView)view.findViewById(R.id.usecase_details_presetValue);
        mReferenceInformation = (TextView)view.findViewById(R.id.usecase_details_referenceInformation);
        mTextDate = (TextView)view.findViewById(R.id.usecase_details_textDate);
        mScene = (TextView)view.findViewById(R.id.usecase_details_Scene);
        mOperationSequence = (TextView)view.findViewById(R.id.usecase_details_operationSequence);
        mOperationDescription = (TextView)view.findViewById(R.id.usecase_details_operationDescription);
        mDate = (TextView)view.findViewById(R.id.usecase_details_data);
        mExpectedOutcome = (TextView)view.findViewById(R.id.usecase_details_expectedOutcome);
        mParticalOutcome = (TextView)view.findViewById(R.id.usecase_details_particalOutcome);
        mTextState = (TextView)view.findViewById(R.id.usecase_details_textState);
        mTestMan = (TextView)view.findViewById(R.id.usecase_details_testMan);
        mExploitMan = (TextView)view.findViewById(R.id.usecase_details_exploitMan);
        mPrincipal = (TextView)view.findViewById(R.id.usecase_details_principal);
    }

    private void contorl() {
        mName.setText(sUseCase.getName());
        mFunctionModel.setText(sUseCase.getFunctionModule());
        mUsecaseNumber.setText(sUseCase.getTest_content());
        mVersionNumber.setText(sUseCase.getVersionNumber());
        mAutorizedMan.setText(sUseCase.getDevelop_name());
        mAutorizedTime.setText(sUseCase.getStart_time());
        mCorrelationUseCase.setText(sUseCase.getCorrelationUseCase());
        mFunctionCharacter.setText(sUseCase.getFunctionCharacter());
        mTextAim.setText(sUseCase.getTextAim());
        mPresetValue.setText(sUseCase.getPresetValue());
        mReferenceInformation.setText(sUseCase.getReferenceInformation());
        mTextDate.setText(sUseCase.getTextDate());
        mScene.setText(sUseCase.getScene());
        mOperationSequence.setText(sUseCase.getContent());
        mOperationDescription.setText(sUseCase.getOperationDescription());
        mDate.setText(sUseCase.getDate());
        mExpectedOutcome.setText(sUseCase.getExpectedOutcome());
        mParticalOutcome.setText(sUseCase.getParticalOutcome());
        mTextState.setText(sUseCase.getStatus());
        mTestMan.setText(sUseCase.getTestter_name());
        mExploitMan.setText(sUseCase.getSubmitter_name());
        mPrincipal.setText(sUseCase.getRemark());
    }

}
