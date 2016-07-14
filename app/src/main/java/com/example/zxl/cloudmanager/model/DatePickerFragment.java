package com.example.zxl.cloudmanager.model;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ZQQ on 2015/10/12.
 * 工厂方法newInstance（）的主要作用——new一个自己的实例，使用时只需要调用这个方法
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    private Date mDate;
    public static final String EXTRA_DATE = "crimeDate";
    public static final String EXTRA_CODE = "requestCode";

    private int sRequsetCode = 0;
    /* fragment之间传递数据用bundle
    * activity之间传递数据用intent
    */
     public DatePickerFragment(){
     }

    public static DatePickerFragment newInstance(Date date, int requestCode){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE,date);
        args.putSerializable(EXTRA_CODE,requestCode);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date)getArguments().getSerializable(EXTRA_DATE);
        Calendar cal = Calendar.getInstance();//默认为当前时间
        cal.setTime(mDate);
        int year = cal.get(Calendar.YEAR);
        int mouth = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(),3,this,year,mouth,day);
        /*第一个参数指当前环境——如果在fragment中就获取它的activity，使用getActivity()方法
                              ——如果在activity中就将它本身传入，用this 表示
        * 第二个参数指回调函数)
        * 第三个参数指日期*/
        return dialog;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Log.d("Test", String.format("用户选中了%d年%d月%d日", year, monthOfYear, dayOfMonth));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year);
        cal.set(Calendar.MONTH,monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        if(getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, cal.getTime());

        int requestCode = (Integer)getArguments().getSerializable(EXTRA_CODE);

        getTargetFragment().onActivityResult(requestCode, Activity.RESULT_OK,intent);
    }
}
