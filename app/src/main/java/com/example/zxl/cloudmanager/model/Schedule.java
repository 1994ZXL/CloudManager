package com.example.zxl.cloudmanager.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ZXL on 2016/9/5.
 */
public class Schedule {
    private String pmsch_id; //任务进度id
    private String pmtask_id; //任务id
    private int pmsch_time; //任务生成时间
    private int report_time; //提交时间
    private int percent; //进度
    private String title; //任务标题

    private static final String JSON_PMSCH_ID = "pmsch_id";
    private static final String JSON_PMTASK_ID = "pmtask_id";
    private static final String JSON_PMSCH_TIME = "pmsch_time";
    private static final String JSON_REPORT_TIME = "report_time";
    private static final String JSON_PERCENT = "percent";
    private static final String JSON_TITLE = "title";

    public Schedule(){}

    public Schedule(JSONObject json) throws JSONException {
        if (json.has(JSON_PMSCH_ID))
            pmsch_id = json.getString(JSON_PMSCH_ID);
        if (json.has(JSON_PMTASK_ID))
            pmtask_id = json.getString(JSON_PMTASK_ID);
        if (json.has(JSON_PMSCH_TIME))
            pmsch_time = json.getInt(JSON_PMSCH_TIME);
        if (json.has(JSON_REPORT_TIME))
            report_time = json.getInt(JSON_REPORT_TIME);
        if (json.has(JSON_PERCENT))
            percent = json.getInt(JSON_PERCENT);
        if (json.has(JSON_TITLE))
            title = json.getString(JSON_TITLE);
    }

    public String getPmsch_id() {
        return pmsch_id;
    }

    public void setPmsch_id(String pmsch_id) {
        this.pmsch_id = pmsch_id;
    }

    public String getPmtask_id() {
        return pmtask_id;
    }

    public void setPmtask_id(String pmtask_id) {
        this.pmtask_id = pmtask_id;
    }

    public int getPmsch_time() {
        return pmsch_time;
    }

    public void setPmsch_time(int pmsch_time) {
        this.pmsch_time = pmsch_time;
    }

    public int getReport_time() {
        return report_time;
    }

    public void setReport_time(int report_time) {
        this.report_time = report_time;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
