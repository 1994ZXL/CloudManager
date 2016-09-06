package com.example.zxl.cloudmanager.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ZXL on 2016/8/2.
 */
public class DateForGeLingWeiZhi {
    private static long time;
    private static String data;

    public DateForGeLingWeiZhi() {

    }

    public static DateForGeLingWeiZhi newInstance() {
        DateForGeLingWeiZhi dateForGeLingWeiZhi = new DateForGeLingWeiZhi();
        return dateForGeLingWeiZhi;
    }

    public static String fromGeLinWeiZhi(int gmtime) {
        time = (long) gmtime;
        time = time * 1000;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.valueOf(time));
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd");
        data = time.format(cal.getTime());
        return data;
    }

    public static String fromGeLinWeiZhi2(int gmtime) {
        time = (long) gmtime;
        time = time * 1000;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.valueOf(time));
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        data = time.format(cal.getTime());
        return data;
    }

    public static String fromGeLinWeiZhi3(int gmtime) {
        time = (long) gmtime;
        time = time * 1000;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.valueOf(time));
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        data = time.format(cal.getTime());
        return data;
    }

    public static int toGeLinWeiZhi(String date) {
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd");
        try {
            long millionSeconds = time.parse(date).getTime();
            millionSeconds = millionSeconds/1000;
            return (int)millionSeconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int toGeLinWeiZhi3(String date) {
        SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            long millionSeconds = time.parse(date).getTime();
            millionSeconds = millionSeconds/1000;
            return (int)millionSeconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
