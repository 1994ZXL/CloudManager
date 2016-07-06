package com.example.zxl.cloudmanager.model;

import java.util.Date;

/**
 * Created by zqq on 16-7-6.
 */
public class Memo {
    private String mMemoTitle;
    private Date mBeginDate;
    private Date mEndDate;

    public String getmMemoTitle() {
        return mMemoTitle;
    }

    public void setmMemoTitle(String mMemoTitle) {
        this.mMemoTitle = mMemoTitle;
    }

    public Date getmBeginDate() {
        return mBeginDate;
    }

    public void setmBeginDate(Date mBeginDate) {
        this.mBeginDate = mBeginDate;
    }

    public Date getmEndDate() {
        return mEndDate;
    }

    public void setmEndDate(Date mEndDate) {
        this.mEndDate = mEndDate;
    }
}
