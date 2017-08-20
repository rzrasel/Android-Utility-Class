package com.sm.cmdss.Utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mortuza Shahariar on 12/17/2016.
 */

public class DateDiffConvert {

    public boolean dateComparisonNoLimit(String date1, String date2) {

        if (date1.contains("start date") || date2.contains("end date")) {
            return false;
        }
        long diffInDays = -1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date s1 = sdf.parse(date1);
            Date s2 = sdf.parse(date2);
            long diffInMillisec = s2.getTime() - s1.getTime();
            diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateVerifyUnlimited(diffInDays);
        }
        return dateVerifyUnlimited(diffInDays);
    }

    public boolean dateComparison(String date1, String date2) {

        if (date1.contains("start date") || date2.contains("end date")) {
            return false;
        }
        long diffInDays = -1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date s1 = sdf.parse(date1);
            Date s2 = sdf.parse(date2);
            long diffInMillisec = s2.getTime() - s1.getTime();
            diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisec);
            Log.d("TAGTU", diffInDays + "");

        } catch (ParseException e) {
            e.printStackTrace();
            return dateVerify(diffInDays);
        }
        return dateVerify(diffInDays);
    }

    public boolean timeComparison(String time1, String time2) {
        Log.d("TAGTU", time1 + "timeComparison==" + time2);
        if (time1.contains("start time") || time2.contains("end time")) {
            return false;
        }
        long diffInTime = -1;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        //            Date s1 = sdf.parse(time1);
//            Date s2 = sdf.parse(time2);
//            long diffInMillisec = s2.getTime() - s1.getTime();
//            diffInTime = TimeUnit.MILLISECONDS.toHours(diffInMillisec);
//
        try {
            diffInTime = Integer.valueOf(time2) - Integer.valueOf(time1);
            Log.d("TAGTU", diffInTime + "");
        } catch (Exception e) {
            e.getStackTrace();
            return timeVerify(diffInTime);
        }


        return timeVerify(diffInTime);
    }

    private boolean dateVerify(long i) {
        return i > 0 && i <= 30;
    }

    private boolean dateVerifyUnlimited(long i) {
        return i > 0;
    }

    private boolean timeVerify(long i) {
        return i >= 0 && i <= 23;
    }

}
