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

    private String[] title;
    private String[] content;
    private int index;

    private Context context;

    private UseCaseLab(Context c){
        context = c;
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

        for (index = 1; index <= 22; index++) {
            if (index == 1) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 2) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 3) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 4) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 5) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 6) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 7) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 8) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 9) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 10) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 11) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 12) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 13) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 14) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 15) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 16) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 17) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 18) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 19) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 20) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 21) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            } else if (index == 22) {
                UseCase useCase = new UseCase();
                useCase.setTitle(title[index - 1]);
                useCase.setContent(content[index - 1]);
                mUseCases.add(useCase);
            }
        }
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
