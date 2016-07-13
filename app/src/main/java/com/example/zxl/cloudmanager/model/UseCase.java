package com.example.zxl.cloudmanager.model;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/13.
 */
public class UseCase {
    private String name;
    private String functionModule;
    private String usecasetNumber;
    private String versionNumber;
    private String autorizedMan;
    private String autorizedTime;
    private String correlationUseCase;
    private String functionCharacter;
    private String textAim;
    private String presetValue;
    private String referenceInformation;
    private String textDate;
    private String Scene;
    private String operationSequence;
    private String operationDescription;
    private String date;
    private String expectedOutcome;
    private String particalOutcome;
    private String textState;
    private String testMan;
    private String exploitMan;
    private String principal;

    public UseCase() {
    }

    public String getUsecasetNumber() {
        return usecasetNumber;
    }

    public void setUsecasetNumber(String usecasetNumber) {
        this.usecasetNumber = usecasetNumber;
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

    public String getTestMan() {
        return testMan;
    }

    public void setTestMan(String testMan) {
        this.testMan = testMan;
    }

    public String getExploitMan() {
        return exploitMan;
    }

    public void setExploitMan(String exploitMan) {
        this.exploitMan = exploitMan;
    }

    public String getAutorizedTime() {
        return autorizedTime;
    }

    public void setAutorizedTime(String autorizedTime) {
        this.autorizedTime = autorizedTime;
    }

    public String getFunctionModule() {
        return functionModule;
    }

    public void setFunctionModule(String functionModule) {
        this.functionModule = functionModule;
    }

    public String getAutorizedMan() {
        return autorizedMan;
    }

    public void setAutorizedMan(String autorizedMan) {
        this.autorizedMan = autorizedMan;
    }

    public String getCorrelationUseCase() {
        return correlationUseCase;
    }

    public void setCorrelationUseCase(String correlationUseCase) {
        this.correlationUseCase = correlationUseCase;
    }

    public String getFunctionCharacter() {
        return functionCharacter;
    }

    public void setFunctionCharacter(String functionCharacter) {
        this.functionCharacter = functionCharacter;
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

    public String getOperationSequence() {
        return operationSequence;
    }

    public void setOperationSequence(String operationSequence) {
        this.operationSequence = operationSequence;
    }

    public String getOperationDescription() {
        return operationDescription;
    }

    public void setOperationDescription(String operationDescription) {
        this.operationDescription = operationDescription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getTextState() {
        return textState;
    }

    public void setTextState(String textState) {
        this.textState = textState;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }
}
