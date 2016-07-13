package com.example.zxl.cloudmanager.model;

/**
 * Created by ZXL on 2016/7/13.
 */
public class Bug {
    private String functionModuel;
    private String bugVersion;
    private String bugState;
    private String useCaseNumber;
    private String foundTime;

    public Bug(){}

    public String getFunctionModuel() {
        return functionModuel;
    }

    public void setFunctionModuel(String functionModuel) {
        this.functionModuel = functionModuel;
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
