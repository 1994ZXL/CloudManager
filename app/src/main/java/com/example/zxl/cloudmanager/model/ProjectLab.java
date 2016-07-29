package com.example.zxl.cloudmanager.model;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/28.
 */
public class ProjectLab {
    private static ProjectLab sProjectLab;
    private static ArrayList<Project> mProjects = new ArrayList<Project>();

    private Context context;

    private ProjectLab(Context context) {
        this.context = context;
    }

    public static ProjectLab newInstance(Context context) {
        if (null == sProjectLab) {
            sProjectLab = new ProjectLab(context.getApplicationContext());
        }
        return sProjectLab;
    }

    public ArrayList<Project> getmProjects() {
        return mProjects;
    }

    public void setmProjects(ArrayList<Project> mProjects) {
        this.mProjects = mProjects;
    }
}
