package com.example.zxl.cloudmanager.model;

import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZXL on 2016/7/13.
 */
public class UseCase {
    private String name; //项目名称
    private String usecase_number; //用例编号
    private String test_app; //功能模块
    private String test_content; //测试内容
    private String versionNumber; //版本程序
    private String develop_name; //开发人员
    private int start_time; //开始时间
    private int end_time; //结束时间
    private int test_time; //测试时间
    private String header_name; //项目主管
    private String functionCharacter; //功能特性
    private String textAim; //测试目的
    private String presetValue; //预置条件
    private String referenceInformation; //参考信息
    private String textDate;
    private String Scene; //用例场景
    private String content; //场景内容
    private String operationDescription; //操作描述
    private String operationSequence; //操作步骤
    private String data; //数据
    private String expectedOutcome; //期望结果
    private String particalOutcome; //实际结果
    private int status; //测试状态
    private String testter_name; //测试人员
    private String submitter_name; //提交人员
    private String remark; //备注

    private static final String JSON_DEVELOPPER = "develop_name";
    private static final String JSON_STATE = "status";
    private static final String JSON_PROJECT = "project_name";
    private static final String JSON_HEADER_NAME = "header_name";
    private static final String JSON_TEST_TIME = "test_time";
    private static final String JSON_START_TIME = "start_time";
    private static final String JSON_END_TIME = "over_time";
    private static final String JSON_TEST_CONTENT = "test_content";
    private static final String JSON_CONTENT = "content";
    private static final String JSON_APP = "test_app";
    private static final String JSON_SUBMITTER = "submitter_name";
    private static final String JSON_REMARK = "remark";
    private static final String JSON_TESTER = "tester_name";
    private static final String JSON_DATA = "data";
    private static final String JSON_EXCEPT = "expect_result";
    private static final String JSON_REAL = "real_result";
    private static final String JSON_DESCRIPTION= "description";
    public UseCase() {
    }

    public void setContent(String[] content) {
        setName(content[0]);
        setFunctionModule(content[1]);
        setTest_content(content[2]);
        setVersionNumber(content[3]);
        setDevelop_name(content[4]);
        //setStart_time(content[5]);
        setCorrelationUseCase(content[6]);
        setFunctionCharacter(content[7]);
        setTextAim(content[8]);
        setPresetValue(content[9]);
        setReferenceInformation(content[10]);
        setTextDate(content[11]);
        setScene(content[12]);
        setContent(content[13]);
        setOperationDescription(content[14]);
        setData(content[15]);
        setExpectedOutcome(content[16]);
        setParticalOutcome(content[17]);
        //setStatus(content[18]);
        setTestter_name(content[19]);
        setSubmitter_name(content[20]);
        setRemark(content[21]);
    }

    public UseCase(JSONObject json) throws JSONException {
        if (json.has(JSON_DEVELOPPER))
            develop_name = json.getString(JSON_DEVELOPPER);
        if (json.has(JSON_STATE))
            status = json.getInt(JSON_STATE);
        if (json.has(JSON_START_TIME))
            start_time = json.getInt(JSON_START_TIME);
        if (json.has(JSON_END_TIME))
            end_time = json.getInt(JSON_END_TIME);
        if (json.has(JSON_APP))
            test_app = json.getString(JSON_APP);
        if (json.has(JSON_TEST_CONTENT))
            test_content = json.getString(JSON_TEST_CONTENT);
        if (json.has(JSON_REMARK))
            remark = json.getString(JSON_REMARK);
        if (json.has(JSON_PROJECT))
            name = json.getString(JSON_PROJECT);
        if (json.has(JSON_HEADER_NAME))
            header_name = json.getString(JSON_HEADER_NAME);
        if (json.has(JSON_TESTER))
            testter_name = json.getString(JSON_TESTER);
        if (json.has(JSON_CONTENT))
            content = json.getString(JSON_CONTENT);
        if (json.has(JSON_TEST_TIME))
            test_time = json.getInt(JSON_TEST_TIME);
        if (json.has(JSON_SUBMITTER))
            submitter_name = json.getString(JSON_SUBMITTER);
        if (json.has(JSON_DATA))
            data = json.getString(JSON_DATA);
        if (json.has(JSON_EXCEPT))
            expectedOutcome = json.getString(JSON_EXCEPT);
        if (json.has(JSON_REAL))
            particalOutcome = json.getString(JSON_REAL);
        if (json.has(JSON_DESCRIPTION))
            operationDescription = json.getString(JSON_DESCRIPTION);
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_DEVELOPPER, develop_name);
        json.put(JSON_HEADER_NAME, status);
        json.put(JSON_START_TIME, start_time);
        json.put(JSON_END_TIME, end_time);
        json.put(JSON_TEST_CONTENT, test_content);
        json.put(JSON_APP, test_app);
        json.put(JSON_TESTER, testter_name);
        json.put(JSON_HEADER_NAME, header_name);
        json.put(JSON_PROJECT, name);
        json.put(JSON_CONTENT, content);
        json.put(JSON_TEST_TIME, test_time);
        json.put(JSON_SUBMITTER, submitter_name);
        json.put(JSON_DATA, data);
        json.put(JSON_EXCEPT, expectedOutcome);
        json.put(JSON_REAL, particalOutcome);
        json.put(JSON_DESCRIPTION, operationDescription);
        return json;
    }

    public String getOperationSequence() {
        return operationSequence;
    }

    public void setOperationSequence(String operationSequence) {
        this.operationSequence = operationSequence;
    }

    public String getUsecase_number() {
        return usecase_number;
    }

    public void setUsecase_number(String usecase_number) {
        this.usecase_number = usecase_number;
    }

    public String getTest_app() {
        return test_app;
    }

    public void setTest_app(String test_app) {
        this.test_app = test_app;
    }

    public String getHeader_name() {
        return header_name;
    }

    public void setHeader_name(String header_name) {
        this.header_name = header_name;
    }

    public String getTest_content() {
        return test_content;
    }

    public void setTest_content(String test_content) {
        this.test_content = test_content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getTestter_name() {
        return testter_name;
    }

    public void setTestter_name(String testter_name) {
        this.testter_name = testter_name;
    }

    public String getSubmitter_name() {
        return submitter_name;
    }

    public void setSubmitter_name(String submitter_name) {
        this.submitter_name = submitter_name;
    }

    public String getFunctionModule() {
        return test_app;
    }

    public void setFunctionModule(String functionModule) {
        this.test_app = functionModule;
    }

    public String getDevelop_name() {
        return develop_name;
    }

    public void setDevelop_name(String develop_name) {
        this.develop_name = develop_name;
    }

    public String getCorrelationUseCase() {
        return header_name;
    }

    public void setCorrelationUseCase(String correlationUseCase) {
        this.header_name = correlationUseCase;
    }

    public String getFunctionCharacter() {
        return functionCharacter;
    }

    public void setFunctionCharacter(String functionCharacter) {
        this.functionCharacter = functionCharacter;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public int getTest_time() {
        return test_time;
    }

    public void setTest_time(int test_time) {
        this.test_time = test_time;
    }

    public String getStatus() {
        if(status == 0)
            return "待测试";
        if(status == 1)
            return "待修改";
        if(status == 2)
            return "已通过";
        return null;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTextAim() {
        return textAim;
    }

    public void setTextAim(String textAim) {
        this.textAim = textAim;
    }

    public String getPresetValue() {
        return presetValue;
    }

    public void setPresetValue(String presetValue) {
        this.presetValue = presetValue;
    }

    public String getTextDate() {
        return textDate;
    }

    public void setTextDate(String textDate) {
        this.textDate = textDate;
    }

    public String getReferenceInformation() {
        return referenceInformation;
    }

    public void setReferenceInformation(String referenceInformation) {
        this.referenceInformation = referenceInformation;
    }

    public String getScene() {
        return Scene;
    }

    public void setScene(String scene) {
        Scene = scene;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperationDescription() {
        return operationDescription;
    }

    public void setOperationDescription(String operationDescription) {
        this.operationDescription = operationDescription;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getParticalOutcome() {
        return particalOutcome;
    }

    public void setParticalOutcome(String particalOutcome) {
        this.particalOutcome = particalOutcome;
    }

    public String getExpectedOutcome() {
        return expectedOutcome;
    }

    public void setExpectedOutcome(String expectedOutcome) {
        this.expectedOutcome = expectedOutcome;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
