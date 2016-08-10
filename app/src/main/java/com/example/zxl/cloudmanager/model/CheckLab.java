package com.example.zxl.cloudmanager.model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/19.
 */
public class CheckLab {
    private Check mCheck;
    private ArrayList<Check> mChecks = new ArrayList<Check>();
    private static CheckLab sCheckLab;

    private Context context;

    private String[] content;
    private String[] content2;

    private int index;

    private CheckLab(Context context) {
        this.context = context;
        /*content = new String[]{
                "张三",
                "项目名称",
                "公司",
                "考勤主管",
                "规定上班时间",
                "规定下班时间",
                "2016.7.19",
                "2016.8.19",
                "上班处理时间",
                "下班处理时间",
                "处理状态",
                "是",
                "是",
                "是",
                "是"};

        content2 = new String[]{
                "李四",
                "项目名称",
                "外面",
                "考勤主管",
                "规定上班时间",
                "规定下班时间",
                "2017.7.19",
                "2018.7.19",
                "上班处理时间",
                "下班处理时间",
                "处理状态",
                "否",
                "否",
                "否",
                "否"};

        setChecks(content);
        setChecks(content2);*/
    }

    public static CheckLab newInstance(Context context) {
        if (null == sCheckLab) {
            sCheckLab = new CheckLab(context.getApplicationContext());
        }
        return sCheckLab;
    }

//    private void setChecks(String[] content) {
//        Check check = new Check();
//        check.setContent(content);
//        Log.d("CheckLab", check.getMem_name());
//        mChecks.add(check);
//    }

    public ArrayList<Check> get() {
        return mChecks;
    }

    public void add(Check check) {
        mChecks.add(check);
    }

}
