package com.example.dell.raisingpets.Module.ModuleStep;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.birbit.android.jobqueue.JobManager;
import com.example.dell.raisingpets.Data.Database.DataBaseOpenHelper;
import com.example.dell.raisingpets.Data.SharedPreferences.ToolPerference;
import com.example.dell.raisingpets.Data.SharedPreferences.UserPreference;
import com.example.dell.raisingpets.Module.ModuleDeath.DeathActivity;
import com.example.dell.raisingpets.Module.ModuleMain.CityActivity;
import com.example.dell.raisingpets.Module.ModuleMain.FroestActivity;
import com.example.dell.raisingpets.Module.ModuleMain.Job_and_Event.PostStepsAndMoneyJob;
import com.example.dell.raisingpets.Module.ModuleRest.RestActivity;
import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.Util.TimeUtils;
import com.example.dell.raisingpets.Util.ToastUtil;
import com.example.dell.raisingpets.Whole.RaisingPetsApplication;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by root on 16-11-24.
 */

public class SensorService extends Service implements SensorEventListener {

    public final static String ACTION_PAUSE = "pause";

    private SensorManager sensorManager;
    private JobManager jobManager;

    public static int todayOffset ;
    private static int steps;
    private int since_boot ;
    private int addPaceFlag = 0;

    private static boolean WAIT_FOR_VALID_STEPS = false;
    private final static int NOTIFICATION_ID = 1;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate() {
        super.onCreate();

        updateNotificationState();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        jobManager = RaisingPetsApplication.getInstance().getJobManager();

        if(ToolPerference.getPedometerpreferences().contains("pauseCount")){
            sensorManager.unregisterListener(this);
        }else {
            sensorManager.registerListener(
                    this,   //监听传感器事件的监听器
                    sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),  //传感器的类型,计步传感器
                    SensorManager.SENSOR_DELAY_NORMAL,  //指定获取传感器数据的频率,SENSOR_DELAY_NORMAL:正常频率
                    5 * 60000000
            );
        }

        DataBaseOpenHelper dataBaseOpenHelper = DataBaseOpenHelper.getInstance(this);
        todayOffset = dataBaseOpenHelper.getSteps(TimeUtils.getToday());   //todayOffset得到的是今天行走的步数,在新的一天开始的时候,这个值必然为MIN_VALUE.
        dataBaseOpenHelper.close();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onSensorChanged(final SensorEvent event) {

        steps = (int)event.values[0];

        if(WAIT_FOR_VALID_STEPS && steps > 0){
            WAIT_FOR_VALID_STEPS = false;

            DataBaseOpenHelper dataBaseOpenHelper = DataBaseOpenHelper.getInstance(this);

            if (todayOffset == Integer.MIN_VALUE) {
                //表示新的一天,开始检测到走动的时候,触发该方法,此时上面的if()中的值为ture.
                todayOffset = -(int) event.values[0];   //event.val ues[0] 加速度在X轴的负值,这是为了和since_boot的值相加之后得到初始0值.
                dataBaseOpenHelper.insertNewDaysteps(TimeUtils.getToday(), (int) event.values[0]);//此时将今天的值储存为初始值(int) event.values[0],注意该值不一定是0.
                //dataBaseOpenHelper.saveCurrentSteps(0);
                ToolPerference.storeMoneyOffset(0);
                ToolPerference.storeShutDownOffset(0);
                UserPreference.storeTodayPaceNum(0);

            }
            dataBaseOpenHelper.close();
            since_boot = (int) event.values[0];

            if((todayOffset + since_boot) < 0){
                todayOffset = 0;
                //ToolPerference.storeShutDownOffset(0);
            }

            int TodayPace = Math.max(todayOffset + since_boot , 0);
            int TodayPaceOffset = TodayPace - ToolPerference.getShutDownOffset();
            ToolPerference.storeShutDownOffset(TodayPace);

            UserPreference.storeTodayPaceNum(UserPreference.getTodayPaceNum() + TodayPaceOffset);

            addPaceFlag += TodayPaceOffset;
            if(addPaceFlag > 100){
                addPaceFlag = 0;
                PostSteps();
                updateNotificationState();
            }

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        DataBaseOpenHelper dataBaseOpenHelper = DataBaseOpenHelper.getInstance(this);
        todayOffset = dataBaseOpenHelper.getSteps(TimeUtils.getToday());   //todayOffset得到的是今天行走的步数,在新的一天开始的时候,这个值必然为MIN_VALUE.
        dataBaseOpenHelper.close();
        PostSteps();

        jobManager = RaisingPetsApplication.getInstance().getJobManager();
        //ToastUtil.showLongToast("后台进程活了");

        if( ACTION_PAUSE.equals(intent.getStringExtra("action"))){

            if(ToolPerference.getPedometerpreferences().contains("pauseCount")){

                sensorManager.registerListener(
                        this,   //监听传感器事件的监听器
                        sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),  //传感器的类型,计步传感器
                        SensorManager.SENSOR_DELAY_NORMAL,  //指定获取传感器数据的频率,SENSOR_DELAY_NORMAL:正常频率
                        5 * 60000000
                );

                ToolPerference.removePauseCount();
                updateNotificationState();
            }else {

                sensorManager.unregisterListener(this);

                ((AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE))
                        .cancel(PendingIntent.getService(
                                getApplicationContext(),
                                2,
                                new Intent(this,SensorService.class),
                                PendingIntent.FLAG_UPDATE_CURRENT
                        )
                );

                //ToolPerference.putSteps(steps);
                updateNotificationState();
                return START_NOT_STICKY;
            }
        }

        ((AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE))
                .setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis() , AlarmManager.INTERVAL_HALF_HOUR ,
                        PendingIntent.getService(
                                getApplicationContext(),
                                2,
                                new Intent(this,SensorService.class),
                                PendingIntent.FLAG_UPDATE_CURRENT
                        ));

        WAIT_FOR_VALID_STEPS = true;
        updateNotificationState();

        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(final Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        // Restart service in 500 ms
        ((AlarmManager) getSystemService(Context.ALARM_SERVICE))
                .set(AlarmManager.RTC, System.currentTimeMillis() + AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                        PendingIntent.getService(this, 3, new Intent(this, SensorService.class), 0));
    }

    @Override
    public void onDestroy() {
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void PostSteps(){
        jobManager.addJobInBackground(new PostStepsAndMoneyJob(
                UserPreference.getTodayPaceNum(),UserPreference.getMoney()));
    }

    private void updateNotificationState() {

        stopForeground(true);
        //NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(!UserPreference.getToken().equals("")){
            Notification.Builder notificationBuilder = new Notification.Builder(this);

            switch (UserPreference.getState()){
                case 0:
                    notificationBuilder.setContentTitle("你已死亡")
                            .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, DeathActivity.class), PendingIntent.FLAG_UPDATE_CURRENT))
                            .setContentText("你已存活:"+UserPreference.getLiveDays()+"天");
                    break;
                case 1:
                    notificationBuilder.setContentTitle("正在计步");
                    if (UserPreference.getBackground().equals("forest")){
                        notificationBuilder
                                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, FroestActivity.class), PendingIntent.FLAG_UPDATE_CURRENT))
                                .setContentText("今日步数:"+UserPreference.getTodayPaceNum()+"步");
                    }else if(UserPreference.getBackground().equals("city")){
                        notificationBuilder
                                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, CityActivity.class), PendingIntent.FLAG_UPDATE_CURRENT))
                                .setContentText("今日步数:"+UserPreference.getTodayPaceNum()+"步");
                    }
                    break;
                case 2:
                    notificationBuilder.setContentTitle("正在休息")
                            .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, RestActivity.class), PendingIntent.FLAG_UPDATE_CURRENT))
                            .setContentText("当前总步数:"+UserPreference.getAllPace()+"步");
                    break;
            }

            notificationBuilder.setPriority(Notification.PRIORITY_MIN)
                    .setShowWhen(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                    .setOngoing(true);

            //nm.notify(NOTIFICATION_ID, notificationBuilder.build());
            startForeground(NOTIFICATION_ID, notificationBuilder.build());
        }

    }

}
