package com.example.zxl.cloudmanager.leave.myLeave;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.zxl.cloudmanager.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ZXL on 2016/7/12.
 */
public class MyLeaveFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{
    private RadioGroup mRadioGroup;
    private RadioButton mQueryButton;
    private RadioButton mApplyButton;
    private ImageView mImageView;
    private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    private HorizontalScrollView mHorizontalScrollView;
    private ViewPager mViewPager;
    private ArrayList<View> mViews;
    private Fragment mFragment;

    private TextView mBack;
    private TextView mSearch;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        this.setHasOptionsMenu(true);
        mFragment = this;
    }
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle saveInstanceState) {
        View view = layoutInflater.inflate(R.layout.main_fragment_my_leave, parent, false);



        initController(view);
        initListener();
        FragmentManager fragmentManager = getFragmentManager();
        initPager(fragmentManager);

        mQueryButton.setChecked(true);
        mViewPager.setCurrentItem(0);
        mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragment.getActivity().finish();
            }
        });

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mFragment.getActivity(), MyLeaveSearchActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void initPager(FragmentManager fragmentManager) {
        mViewPager.setAdapter(new  FragmentStatePagerAdapter(fragmentManager) {
            Fragment fragment;
            @Override
            public Fragment getItem(int position) {
                if (0 == position) {
                    fragment = new MyLeaveQueryFragment();
                } else if (1 == position) {
                    fragment = new MyLeaveApplyFragment();
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        AnimationSet _AnimationSet = new AnimationSet(true);
        TranslateAnimation _TranslateAnimation;

        Log.i("zj", "checkedid=" + checkedId);
        if (checkedId == R.id.main_fragment_my_leave_query) {
            _TranslateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, getResources().getDimension(R.dimen.rdo1), 0f, 0f);
            _AnimationSet.addAnimation(_TranslateAnimation);
            _AnimationSet.setFillBefore(false);
            _AnimationSet.setFillAfter(true);
            _AnimationSet.setDuration(100);
            mImageView.startAnimation(_AnimationSet);//开始上面蓝色横条图片的动画切换
            mViewPager.setCurrentItem(0);//让下方ViewPager跟随上面的HorizontalScrollView切换
        }else if (checkedId == R.id.main_fragment_my_leave_apply) {
            _TranslateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, getResources().getDimension(R.dimen.rdo2), 0f, 0f);

            _AnimationSet.addAnimation(_TranslateAnimation);
            _AnimationSet.setFillBefore(false);
            _AnimationSet.setFillAfter(true);
            _AnimationSet.setDuration(100);

            //mImageView.bringToFront();
            mImageView.startAnimation(_AnimationSet);

            mViewPager.setCurrentItem(1);
        }

        mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();

        Log.i("zj", "getCurrentCheckedRadioLeft=" + getCurrentCheckedRadioLeft());
        Log.i("zj", "getDimension=" + getResources().getDimension(R.dimen.rdo2));

        mHorizontalScrollView.smoothScrollTo((int) mCurrentCheckedRadioLeft - (int) getResources().getDimension(R.dimen.rdo2), 0);
    }

    private float getCurrentCheckedRadioLeft() {

        if (mQueryButton.isChecked()) {

            return getResources().getDimension(R.dimen.rdo1);
        }else if (mApplyButton.isChecked()) {

            return getResources().getDimension(R.dimen.rdo2);
        }
        return 0f;
    }

    private void initListener() {
        mRadioGroup.setOnCheckedChangeListener(this);
        mViewPager.setOnPageChangeListener(new MyPagerOnPageChangeListener());
    }

    private void initController(View v) {
        mRadioGroup = (RadioGroup)v.findViewById(R.id.main_fragment_my_leave_radioGroup);
        mQueryButton = (RadioButton)v.findViewById(R.id.main_fragment_my_leave_query);
        mApplyButton = (RadioButton)v.findViewById(R.id.main_fragment_my_leave_apply);

        mImageView = (ImageView)v.findViewById(R.id.main_fragment_my_leave_image);

        mHorizontalScrollView = (HorizontalScrollView)v.findViewById(R.id.horizontalScrollView);

        mViewPager = (ViewPager)v.findViewById(R.id.main_fragment_my_leave_viewPager);

        mBack = (TextView) v.findViewById(R.id.my_leave_back);
        mSearch = (TextView) v.findViewById(R.id.my_leave_search);
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public void destroyItem(View v, int position, Object obj) {
//            ((ViewPager)v).removeView(mViews.get(position));
        }

        @Override
        public void finishUpdate(View arg0) {

        }

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public Object instantiateItem(View v, int position) {
            ((ViewPager)v).addView(mViews.get(position));
            return mViews.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }
    }

    private class MyPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
        /**
         * 滑动ViewPager的时候,让上方的HorizontalScrollView自动切换
         */
        @Override
        public void onPageSelected(int position) {

            if (position == 0) {
                mQueryButton.performClick();
            }else if (position == 1) {
                mApplyButton.performClick();
            }
        }
    }
}
