package com.example.zxl.cloudmanager.model;

/**
 * Created by ZXL on 2016/7/13.
 */
public class Bug {
    private String bugNumber;
    private String bugVersion;
    private int status; //bug状态
    private String bugContent;
    private String useCaseNumber;
    private String operationMode;
    private String entranceMode;
    private int submit_time; //发现时间
    private String foundMan;
    private int modify_time; //修改时间
    private String editMan;
    private String underProgram;

    private String functionModel;

    public Bug(){}

    public void setContent(String[] content) {
        setFunctionModel(content[0]);
        setBugNumber(content[1]);
        setBugVersion(content[2]);
        //setStatus(content[3]);
        setBugContent(content[4]);
        setUseCaseNumber(content[5]);
        setOperationMode(content[6]);
        setEntranceMode(content[7]);
        //setSubmit_time(content[8]);
        setFoundMan(content[9]);
        /*setModify_time(content[10]);*/
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


    public String getUseCaseNumber() {
        return useCaseNumber;
    }

    public void setUseCaseNumber(String useCaseNumber) {
        this.useCaseNumber = useCaseNumber;
    }

    public String getStatus() {
        if (status == 1) {
            return "待确认";
        } else if (status == 2) {
            return "已排除";
        } else if (status == 3) {
            return "不解决";
        } else if (status == 4) {
            return "待修改";
        } else if (status == 5) {
            return "待测试";
        } else if (status == 6) {
            return "已通过";
        }else if (status == 7) {
            return "已修改";
        }
        return null;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(int submit_time) {
        this.submit_time = submit_time;
    }

    public int getModify_time() {
        return modify_time;
    }

    public void setModify_time(int modify_time) {
        this.modify_time = modify_time;
    }
}
