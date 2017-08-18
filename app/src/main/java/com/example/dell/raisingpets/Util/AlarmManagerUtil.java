package com.example.dell.raisingpets.Util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

/**
 * Created by dell on 2016/8/31.
 */

public class AlarmManagerUtil {
    public static final String ALARM_ACTION = "alarm.clock";

    /**
     *
     * @param context
     * @param flag            周期性时间间隔的标志,flag = 0 表示一次性的闹钟, flag = 1 表示每天提醒的闹钟(1天的时间间隔),flag = 2
     *                        表示按周每周提醒的闹钟（一周的周期性时间间隔）
     * @param hour            时
     * @param minute          分
     * @param id              闹钟的id,这里主要是为了区别周几重复时所定的几个闹钟
     * @param week            week=0表示一次性闹钟或者按天的周期性闹钟，非0 的情况下是几就代表以周为周期性的周几的闹钟
     */
    public static void setAlarm(Context context, int flag, int hour, int minute, int id, int week ){

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        long intervalMillis = 0;
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),hour,minute,10);

        if(flag == 0){
            intervalMillis = 0;//重复一次
        }else if(flag == 1){
            intervalMillis = 24 * 3600 * 1000;//每天
        }else if(flag == 2){
            intervalMillis = 24 * 3600 * 1000 * 7;//周几重复,这里以一周为周期
        }

        Intent intent = new Intent(ALARM_ACTION);
        intent.putExtra("intervalMillis",intervalMillis);
        intent.putExtra("id",id);
        PendingIntent sendIntent = PendingIntent.getBroadcast(context,id,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            alarmManager.setWindow(AlarmManager.RTC_WAKEUP,begainTime(week, calendar.getTimeInMillis()),intervalMillis,sendIntent);
        }else {
            if(flag == 0){
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sendIntent);
            }else {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,begainTime(week, calendar.getTimeInMillis()),intervalMillis,sendIntent);
            }//calendar.getTimeInMillis()返回的是 “当前时间，以从格林威治标准时间 1970 年 1 月 1 日的 00:00:00.000，至现在所经过的 UTC 毫秒数形式.”
        }
    }


    /**
     *
     * @param weekflag 传入的是周几
     * @param dateTime 传入的是时间戳（设置当天的年月日+从选择框拿来的时分秒）
     * @return 返回起始闹钟时间的时间戳
     */
    private static long begainTime(int weekflag, long dateTime){
        long time = 0;

        if(weekflag != 0 ){//weekflag == 0表示是按天为周期性的时间间隔或者是一次行的，weekfalg非0时表示每周几的闹钟并以周为时间间隔
            Calendar calendar = Calendar.getInstance();
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            if(1 == week){//如果是一周第一天,说明是星期日,week=7。
                week = 7;
            }else if(2 == week){
                week = 1;
            }else if(3 == week){
                week = 2;
            }else if(4 == week){
                week = 3;
            }else if(5 == week){
                week = 4;
            }else if(6 == week){
                week = 5;
            }else if(7 == week){
                week = 6;
            }

            if(weekflag == week){//如果设置的星期数和当前星期数相同
                if(dateTime > System.currentTimeMillis()){//System.currentTimeMillis()与calendar.getTimeInMillis()在时间上没什么区别。
                    time = dateTime;//若果设置的时间大于当前时间，就没问题;如果小于就说明是下周的时间.
                }else {
                    time = dateTime + 7 * 24 * 3600 * 1000;//下周
                }
            }else if(weekflag > week){
                time = dateTime + (weekflag - week) * 24 * 3600 * 1000;
            }else if(weekflag < week){
                time = dateTime + (weekflag - week + 7) * 24 * 3600 * 1000;
            }

        }else {
            if (dateTime > System.currentTimeMillis()) {
                time = dateTime;
            } else {
                time = dateTime + 24 * 3600 * 1000;
            }
        }

        return time;
    }

}
