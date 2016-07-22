package com.example.zxl.cloudmanager.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/22.
 */
public class BugLab {
    private static BugLab sBugLab;
    private ArrayList<Bug> mBugs = new ArrayList<Bug>();
    private Bug mBug = new Bug();

    private Context context;

    private String[] content;
    private String[] content2;
    private int index;

    private BugLab(Context context) {
        this.context = context;
        content = new String[]{
                "功能模块",
                "bug序号",
                "一级",
                "待确认",
                "bug内容",
                "用例编号",
                "操作模式",
                "进入方式",
                "发现时间",
                "发现人",
                "修改时间",
                "修改人",
                "所属项目"};

        content2 = new String[]{
                "功能模块",
                "111",
                "二级",
                "待修改",
                "bug",
                "111",
                "操作模式",
                "进入方式",
                "2016.7.22",
                "张三",
                "2016.7.22",
                "张三",
                "项目"};
        set(content);
        set(content2);
    }

    public static BugLab newInstance(Context context) {
        if (null == sBugLab) {
            sBugLab = new BugLab(context.getApplicationContext());
        }
        return sBugLab;
    }

    public ArrayList<Bug> get() {
        return mBugs;
    }

    public void set(String[] content) {
        Bug bug = new Bug();
        bug.setContent(content);
        mBugs.add(bug);
    }
}
