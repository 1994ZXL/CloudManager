package com.example.zxl.cloudmanager.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZXL on 2016/7/13.
 */
public class Bug {
    private String bugNumber;
    private int level; //bug等级
    private int status; //bug状态
    private String project_name;
    private String mem_name;
    private int case_mode; //用例模型
    private int submit_time;
    private int modify_time;
    private int submit_time_start; //发现时间 开始
    private int submit_time_end; //发现时间 结束
    private String submitter; //发现人
    private int modify_time_start; //修改时间 开始
    private int modify_time_end; //修改时间 结束
    private String modifier; //修改人
    private String underProgram;

    private String functionModel;

    public Bug(){}

    private static final String JSON_LEVEL = "level";
    private static final String JSON_STATE = "status";
    private static final String JSON_PROJECT = "project_name";
    private static final String JSON_NAME = "mem_name";
    private static final String JSON_SUBMIT = "submit_time";
    private static final String JSON_SUBMIT_START = "submit_time_start";
    private static final String JSON_SUBMIT_END = "submit_time_end";
    private static final String JSON_MODIFY_START = "modify_time_start";
    private static final String JSON_MODIFY_END = "modify_time_end";
    private static final String JSON_MODIFY = "modify_tine";
    private static final String JSON_CASE_MODE = "case_mode";
    private static final String JSON_SUBMITTER = "submitter";
    private static final String JSON_MODIFIER = "modifier" ;

    public void setContent(String[] content) {
        setFunctionModel(content[0]);
        setBugNumber(content[1]);
        //setLevel(content[2]);
        //setStatus(content[3]);
        setProject_name(content[4]);
        setMem_name(content[5]);
        //setCase_mode(content[6]);
        //setSubmit_time(content[7]);
        //setSubmit_time(content[8]);
        setSubmitter(content[9]);
        /*setModify_time(content[10]);*/
        setEditMan(content[11]);
        setUnderProgram(content[12]);
    }

    public Bug(JSONObject json) throws JSONException {
        if (json.has(JSON_LEVEL))
            level = json.getInt(JSON_LEVEL);
        if (json.has(JSON_STATE))
            status = json.getInt(JSON_STATE);
        if (json.has(JSON_SUBMIT_START))
            submit_time_start = json.getInt(JSON_SUBMIT_START);
        if (json.has(JSON_SUBMIT_END))
            submit_time_end = json.getInt(JSON_SUBMIT_END);
        if (json.has(JSON_MODIFY_END))
            modify_time_start = json.getInt(JSON_MODIFY_END);
        if (json.has(JSON_MODIFY_START))
            modify_time_end = json.getInt(JSON_MODIFY_START);
        if (json.has(JSON_CASE_MODE))
            case_mode = json.getInt(JSON_CASE_MODE);
        if (json.has(JSON_PROJECT))
            project_name = json.getString(JSON_PROJECT);
        if (json.has(JSON_NAME))
            mem_name = json.getString(JSON_NAME);
        if (json.has(JSON_SUBMITTER))
            submitter = json.getString(JSON_SUBMITTER);
        if (json.has(JSON_MODIFIER))
            modifier = json.getString(JSON_MODIFIER);
        if (json.has(JSON_SUBMIT))
            submit_time = json.getInt(JSON_SUBMIT);
        if (json.has(JSON_MODIFY))
            modify_time = json.getInt(JSON_MODIFY);
    }

    public JSONObject toJSON() throws JSONException{
        JSONObject json = new JSONObject();
        json.put(JSON_LEVEL, level);
        json.put(JSON_NAME, status);
        json.put(JSON_SUBMIT_START, submit_time_start);
        json.put(JSON_SUBMIT_END, submit_time_end);
        json.put(JSON_MODIFY_START, modify_time_start);
        json.put(JSON_MODIFY_END, modify_time_end);
        json.put(JSON_SUBMITTER, case_mode);
        json.put(JSON_NAME, mem_name);
        json.put(JSON_PROJECT, project_name);
        json.put(JSON_SUBMITTER, submitter);
        json.put(JSON_MODIFIER, modifier);
        json.put(JSON_SUBMIT, submit_time);
        json.put(JSON_MODIFY, modify_time);
        return json;
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

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getCase_mode() {
        String caseMode = " " + this.case_mode;
        return caseMode;
    }

    public void setCase_mode(int case_mode) {
        this.case_mode = case_mode;
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

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getEditMan() {
        return modifier;
    }

    public void setEditMan(String editMan) {
        this.modifier = editMan;
    }

    public String getUnderProgram() {
        return underProgram;
    }

    public void setUnderProgram(String underProgram) {
        this.underProgram = underProgram;
    }

    public String getLevel() {
        if (status == 1) {
            return "一级";
        } else if (status == 2) {
            return "二级";
        } else if (status == 3) {
            return "三级";
        } else if (status == 4) {
            return "四级";
        } else if (status == 5) {
            return "五级";
        } else if (status == 6) {
            return "六级";
        }
        return null;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getMem_name() {
        return mem_name;
    }

    public void setMem_name(String mem_name) {
        this.mem_name = mem_name;
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

    public int getSubmit_time_start() {
        return submit_time_start;
    }

    public void setSubmit_time_start(int submit_time_start) {
        this.submit_time_start = submit_time_start;
    }

    public int getSubmit_time_end() {
        return submit_time_end;
    }

    public void setSubmit_time_end(int submit_time_end) {
        this.submit_time_end = submit_time_end;
    }

    public int getModify_time_start() {
        return modify_time_start;
    }

    public void setModify_time_start(int modify_time_start) {
        this.modify_time_start = modify_time_start;
    }

    public int getModify_time_end() {
        return modify_time_end;
    }

    public void setModify_time_end(int modify_time_end) {
        this.modify_time_end = modify_time_end;
    }
}
