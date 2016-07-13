package com.example.zxl.cloudmanager.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/13.
 */
public class UseCaseLab {
    private UseCase useCase = new UseCase();
    private ArrayList<UseCase> mUseCases = new ArrayList<UseCase>();
    private static UseCaseLab sUseCaseLab;

    private String[] title;

    private UseCaseLab(Context c){
        title = new String[]{
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

        useCase.setName("张三");
        useCase.setAutorizedMan("张三");
        useCase.setAutorizedTime("1月1日");
        useCase.setCorrelationUseCase("张三");
        useCase.setDate("张三");
        mUseCases.add(useCase);
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

    public String[] getTitle() {
        return title;
    }
}
