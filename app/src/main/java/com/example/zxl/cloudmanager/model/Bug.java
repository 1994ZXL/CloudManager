package com.example.zxl.cloudmanager.model;

/**
 * Created by ZXL on 2016/7/13.
 */
public class Bug {
    private String bugNumber;
    private String bugVersion;
    private String bugState;
    private String bugContent;
    private String useCaseNumber;
    private String operationMode;
    private String entranceMode;
    private String foundTime;
    private String foundMan;
    private String editTime;
    private String editMan;
    private String underProgram;

    private String functionModel;

    public Bug(){}

    public void setContent(String[] content) {
        setFunctionModel(content[0]);
        setBugNumber(content[1]);
        setBugVersion(content[2]);
        setBugState(content[3]);
        setBugContent(content[4]);
        setUseCaseNumber(content[5]);
        setOperationMode(content[6]);
        setEntranceMode(content[7]);
        setFoundTime(content[8]);
        setFoundMan(content[9]);
        setEditTime(content[10]);
        setEditMan(content[11]);
        setUnderProgram(content[12]);
    }

    public String getFunctionModel() {
        return functionModel;
    }

    public void setFunctionModel(String functionModel) {
        this.functionModel = functionModel;
    }

    public String getBugNumber() {
        return bugNumber;
    }

    public void setBugNumber(String bugNumber) {
        this.bugNumber = bugNumber;
    }

    public String getBugContent() {
        return bugContent;
    }

    public void setBugContent(String bugContent) {
        this.bugContent = bugContent;
    }

    public String getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(String operationMode) {
        this.operationMode = operationMode;
    }

    public String getEntranceMode() {
        return entranceMode;
    }

    public void setEntranceMode(String entranceMode) {
        this.entranceMode = entranceMode;
    }

    public String getFoundMan() {
        return foundMan;
    }

    public void setFoundMan(String foundMan) {
        this.foundMan = foundMan;
    }

    public String getEditTime() {
        return editTime;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public String getEditMan() {
        return editMan;
    }

    public void setEditMan(String editMan) {
        this.editMan = editMan;
    }

    public String getUnderProgram() {
        return underProgram;
    }

    public void setUnderProgram(String underProgram) {
        this.underProgram = underProgram;
    }

    public String getBugVersion() {
        return bugVersion;
    }

    public void setBugVersion(String bugVersion) {
        this.bugVersion = bugVersion;
    }

    public String getBugState() {
        return bugState;
    }

    public void setBugState(String bugState) {
        this.bugState = bugState;
    }

    public String getUseCaseNumber() {
        return useCaseNumber;
    }

    public void setUseCaseNumber(String useCaseNumber) {
        this.useCaseNumber = useCaseNumber;
    }

    public String getFoundTime() {
        return foundTime;
    }

    public void setFoundTime(String foundTime) {
        this.foundTime = foundTime;
    }
}
