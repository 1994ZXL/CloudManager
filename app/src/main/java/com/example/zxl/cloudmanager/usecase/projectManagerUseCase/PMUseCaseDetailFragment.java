package com.example.zxl.cloudmanager.usecase.projectManagerUseCase;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;
import com.example.zxl.cloudmanager.model.DateForGeLingWeiZhi;
import com.example.zxl.cloudmanager.model.UseCase;


/**
 * Created by ZXL on 2016/7/13.
 */
public class PMUseCaseDetailFragment extends Fragment {
    private static UseCase sUseCase = new UseCase();

    private TextView name; //项目名称
    private TextView test_app; //功能模块
    private TextView test_content; //测试内容
    private TextView versionNumber; //用例编号
    private TextView develop_name; //开发人员
    private TextView start_time; //开始时间
    private TextView end_time; //结束时间
    private TextView test_time; //测试时间
    private TextView header_name; //项目主管
    private TextView functionCharacter; //功能特性
    private TextView useCase_about; //相关用例
    private TextView test_aim; //测试目的
    private TextView presetValue; //预置条件
    private TextView referenceInformation; //参考信息
    private TextView textDate; //
    private TextView Scene; //用例场景
    private TextView content; //场景内容
    private TextView operationDescription; //操作描述
    private TextView operationSequence; //操作步骤
    private TextView data;
    private TextView expectedOutcome;
    private TextView particalOutcome;
    private TextView status; //测试状态
    private TextView testter_name; //测试人员
    private TextView submitter_name; //提交人员
    private TextView remark; //备注

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

    }

    public static PMUseCaseDetailFragment newInstance(Object data) {
        sUseCase = (UseCase) data;
        PMUseCaseDetailFragment fragment = new PMUseCaseDetailFragment();
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
        name = (TextView)view.findViewById(R.id.usecase_details_program_number);
        versionNumber = (TextView)view.findViewById(R.id.usecase_details_functionModule);
        test_app = (TextView)view.findViewById(R.id.usecase_details_id);
        header_name = (TextView)view.findViewById(R.id.usecase_details_program_number);
        versionNumber = (TextView)view.findViewById(R.id.usecase_details_autorized_strength);
        start_time = (TextView)view.findViewById(R.id.usecase_details_formation_time);
        useCase_about = (TextView)view.findViewById(R.id.usecase_details_correlation_usecase);
        functionCharacter = (TextView)view.findViewById(R.id.usecase_details_function_character);
        test_aim = (TextView)view.findViewById(R.id.usecase_details_text_aim);
        presetValue = (TextView)view.findViewById(R.id.usecase_details_presetValue);
        referenceInformation = (TextView)view.findViewById(R.id.usecase_details_referenceInformation);
        data = (TextView)view.findViewById(R.id.usecase_details_textDate);
        Scene = (TextView)view.findViewById(R.id.usecase_details_Scene);
        operationSequence = (TextView)view.findViewById(R.id.usecase_details_operationSequence);
        operationDescription = (TextView)view.findViewById(R.id.usecase_details_operationDescription);
        expectedOutcome = (TextView)view.findViewById(R.id.usecase_details_data);
        particalOutcome = (TextView)view.findViewById(R.id.usecase_details_expectedOutcome);
        status = (TextView)view.findViewById(R.id.usecase_details_particalOutcome);
        testter_name = (TextView)view.findViewById(R.id.usecase_details_textState);
        submitter_name = (TextView)view.findViewById(R.id.usecase_details_testMan);
        develop_name = (TextView)view.findViewById(R.id.usecase_details_exploitMan);
        remark = (TextView)view.findViewById(R.id.usecase_details_principal);
    }

    private void contorl() {
        name.setText(sUseCase.getName());
        test_app.setText(sUseCase.getFunctionModule());
        versionNumber.setText(sUseCase.getVersionNumber());
        header_name.setText(sUseCase.getVersionNumber());
        develop_name.setText(sUseCase.getDevelop_name());
        start_time.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(sUseCase.getStart_time()));
        end_time.setText(DateForGeLingWeiZhi.newInstance().fromGeLinWeiZhi(sUseCase.getEnd_time()));
        functionCharacter.setText(sUseCase.getFunctionCharacter());
        test_aim.setText(sUseCase.getTest_content());
        presetValue.setText(sUseCase.getPresetValue());
        referenceInformation.setText(sUseCase.getReferenceInformation());
        test_time.setText(sUseCase.getData());
        Scene.setText(sUseCase.getScene());
        content.setText(sUseCase.getContent());
        operationSequence.setText(sUseCase.getOperationSequence());
        operationDescription.setText(sUseCase.getOperationDescription());
        data.setText(sUseCase.getData());
        expectedOutcome.setText(sUseCase.getExpectedOutcome());
        particalOutcome.setText(sUseCase.getParticalOutcome());
        status.setText(sUseCase.getStatus());
        testter_name.setText(sUseCase.getTestter_name());
        submitter_name.setText(sUseCase.getSubmitter_name());
        remark.setText(sUseCase.getRemark());
    }

}
