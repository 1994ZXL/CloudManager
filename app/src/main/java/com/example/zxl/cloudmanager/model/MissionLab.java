package com.example.zxl.cloudmanager.model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/21.
 */
public class MissionLab {
    private Mission mMission = new Mission();
    private static MissionLab sMission;
    private ArrayList<Mission> mMissions = new ArrayList<Mission>();

    private Context context;

    private String[] content;
    private String[] content2;

    private MissionLab(Context context) {
        this.context = context;
        content = new String[] {
                "任务名称",
                "任务内容",
                "高",
                "任务内容说明",
                "2016.7.21",
                "2016.7.21",
                "80%",
                "未完成",
                "处理意见"};
        content2 = new String[] {
                "任务名称",
                "任务内容",
                "低",
                "任务内容说明",
                "2016.7.21",
                "2016.7.21",
                "100%",
                "完成",
                "处理意见"};
        setMissions(content);
        setMissions(content2);
    }

    public static MissionLab newInstance(Context context) {
        if (null == sMission) {
            sMission = new MissionLab(context.getApplicationContext());
        }
        return sMission;
    }

    public ArrayList<Mission> get() {
        return mMissions;
    }

    private void setMissions(String[] content) {
        Mission mission = new Mission();
        mission.setData(content);
        mMissions.add(mission);
    }
}
