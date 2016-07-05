package com.example.zxl.cloudmanager;

import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainFragment extends Fragment {
    private RadioGroup mRadioGroup;
    private RadioButton mRB_Message;
    private RadioButton mRB_Check;
    private RadioButton mRB_Leave;
    private RadioButton mRB_Mission;
    private RadioButton mRB_Maintain;
    private RadioButton mRB_Post;
    private RadioButton mRB_Overtime;
    private RadioButton mRB_Travel;
    private RadioButton mRB_Memo;
    private RadioButton mRB_Bug;
    private RadioButton mRB_Usecase;
    private ImageView mImageView;
    private float mCurrentCheckedRadioLeft;  //所选中radiobutton距离左侧的距离
    private HorizontalScrollView mHorizontalScrollView;  //上滑动条
    private ViewPager mViewPager;

}
