package com.example.zxl.cloudmanager.model;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by ZXL on 2016/8/31.
 */
public class DateTimePicker {
    private static final String TAG = "DateTimePicker";

    private static StringBuilder str = new StringBuilder("");

    public static void  selectDateTime(final Fragment mFragment, final Button button) {
        Calendar calendar = Calendar.getInstance();
        Dialog dateDialog = new DatePickerDialog(mFragment.getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                str.setLength(0);
                str.append(i + "年" + (i1 + 1) + "月" + i2 + " ");
                Calendar time = Calendar.getInstance();
                Dialog timeDialog = new TimePickerDialog(mFragment.getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        str.append(i + ":" + i1);
                        button.setText(str);
                    }
                }, time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), true);
                timeDialog.setTitle("请选择时间");
                timeDialog.show();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dateDialog.setTitle("请选择日期");
        dateDialog.show();
        Log.d(TAG, "选择的时间" + str);
    }
}
