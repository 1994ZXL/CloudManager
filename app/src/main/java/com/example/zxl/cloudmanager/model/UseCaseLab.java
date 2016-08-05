package com.example.zxl.cloudmanager.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZXL on 2016/7/13.
 */
public class UseCaseLab {
    private UseCase useCase = new UseCase();
    private ArrayList<UseCase> mUseCases = new ArrayList<UseCase>();
    private static UseCaseLab sUseCaseLab;

    private String[] content2;
    private String[] content;
    private int index;

    private Context context;

    private UseCaseLab(Context c){
        context = c;
        /*content2 = new String[]{
                "项目/软件",
                "功能模块",
                "用例编号",
                "程序版本",
                "编制人",
                "编制时间",
                "相关用例",
                "功能特性",
                "测试目的",
                "预置条件",
                "参考信息",
                "测试数据",
                "场景",
                "操作步骤",
                "操作描述",
                "数据",
                "期望结果",
                "实际结果",
                "测试状态",
                "测试人员",
                "开发人员",
                "负责人"};

        content = new String[]{
                "张三",
                "模块",
                "编号",
                "v1.0",
                "张三",
                "2016.7.18",
                "相关用例",
                "特性",
                "避免bug",
                "没有条件",
                "参考信息",
                "测试数据",
                "场景",
                "操作步骤",
                "操作描述",
                "数据",
                "期望结果",
                "实际结果",
                "测试状态",
                "测试人员",
                "开发人员",
                "负责人"};
        set(content2);
        set(content);*/
    }

    public static UseCaseLab newInstance(Context c) {
        if (null == sUseCaseLab) {
            sUseCaseLab = new UseCaseLab(c.getApplicationContext());
        }
        return sUseCaseLab;
    }

    public ArrayList<UseCase> getUseCase() {
        return mUseCases;
    }

    public void set(String[] content) {
        UseCase useCase = new UseCase();
        useCase.setContent(content);
        mUseCases.add(useCase);
    }
}
