package com.example.zxl.cloudmanager.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ZXL on 2016/8/2.
 */
public class DateForGeLingWeiZhi {
    private static int time;
    private static String data;

    public DateForGeLingWeiZhi() {

    }

    public static DateForGeLingWeiZhi newInstance() {
        DateForGeLingWeiZhi dateForGeLingWeiZhi = new DateForGeLingWeiZhi();
        return dateForGeLingWeiZhi;
    }

    public static String getData(int gmtime) {
        time = gmtime * 1000;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.valueOf(time));
        SimpleDateFormat time=new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
        data = time.format(cal.getTime());
        return data;
    }

    public static int setTime(String date) {
        SimpleDateFormat time=new SimpleDateFormat("yyyy年MM月dd HH:mm:ss");
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
