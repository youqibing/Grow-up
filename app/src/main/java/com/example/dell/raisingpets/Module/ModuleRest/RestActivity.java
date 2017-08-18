package com.example.dell.raisingpets.Module.ModuleRest;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.birbit.android.jobqueue.JobManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dell.raisingpets.Data.SharedPreferences.ToolPerference;
import com.example.dell.raisingpets.Data.SharedPreferences.UserPreference;
import com.example.dell.raisingpets.Module.ModuleMain.CityActivity;
import com.example.dell.raisingpets.Module.ModuleMain.FroestActivity;
import com.example.dell.raisingpets.Module.ModuleMain.Job_and_Event.ProgressEvent;
import com.example.dell.raisingpets.Module.ModuleRest.finishRest.FinishRestEvent;
import com.example.dell.raisingpets.Module.ModuleRest.finishRest.FinishRestJob;
import com.example.dell.raisingpets.Module.ModuleSetting.SettingActivity;
import com.example.dell.raisingpets.Module.ModuleStep.SensorService;
import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.Util.NoDoubleClickUtils;
import com.example.dell.raisingpets.View.IphoneStyleDialog;
import com.example.dell.raisingpets.Whole.RaisingPetsApplication;
import com.example.dell.raisingpets.Whole.UI.BaseActivity;

import de.greenrobot.event.EventBus;
import me.zhouzhuo.zzhorizontalprogressbar.ZzHorizontalProgressBar;

/**
 * Created by root on 16-11-30.
 */

public class RestActivity extends BaseActivity {

    private TextView steps_tx;
    private TextView money_tx;

    private Dialog lodingDialog;

    private JobManager jobManager;
    private final static int NOTIFICATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(this,SensorService.class));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);   //取消状态栏
        setContentView(R.layout.activity_rest);

        RaisingPetsApplication.getInstance().addActivity(this);
        jobManager = RaisingPetsApplication.getInstance().getJobManager();

        lodingDialog = IphoneStyleDialog.loadingDialog(this);

        steps_tx = (TextView)findViewById(R.id.steps_rest_tx);
        steps_tx.setText( UserPreference.getTodayPaceNum() + "");

        money_tx = (TextView)findViewById(R.id.money_tx);
        money_tx.setText(UserPreference.getMoney() + "");

        ZzHorizontalProgressBar pbone = (ZzHorizontalProgressBar) findViewById(R.id.pbone);
        ZzHorizontalProgressBar pbtwo = (ZzHorizontalProgressBar) findViewById(R.id.pbtwo);

        pbone.setProgress(UserPreference.getHungryPoint());
        pbtwo.setProgress(UserPreference.getHP());
        pbone.setMax(100);
        pbtwo.setMax(100);

        ((TextView) findViewById(R.id.rest_dialog_tx)).setText("正在养精蓄锐中......");

        Glide.with(this)
                .load(UserPreference.getHeadUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop().into((ImageView)findViewById(R.id.avatar));

        ((TextView)findViewById(R.id.nickName)).setText(UserPreference.getNickName());

        RelativeLayout wake_relative = (RelativeLayout) findViewById(R.id.wake_relative);
        wake_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobManager.addJobInBackground(new FinishRestJob(1));
            }
        });

        ((ImageView)findViewById(R.id.setting)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RestActivity.this, SettingActivity.class));
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        UserPreference.storeState(2);
        EventBus.getDefault().register(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ){
            if (!NoDoubleClickUtils.isDoubleClick()) {
                RaisingPetsApplication.getInstance().exit();
            }
        }
        return false;
    }

    public void onEventMainThread(FinishRestEvent event) {

        if(event.isSuccess()){
            startService(new Intent(RestActivity.this,SensorService.class).putExtra("action",SensorService.ACTION_PAUSE));

            int restTime = event.getRestTime();

            ((TextView) findViewById(R.id.rest_dialog_tx)).setText("刚刚休息了" + event.getRestTime() +"秒钟");
            if(UserPreference.getBackground().equals("forest")){
                startActivity(new Intent(RestActivity.this, FroestActivity.class));
            }else if(UserPreference.getBackground().equals("city")){
                startActivity(new Intent(RestActivity.this, CityActivity.class));
            }
            overridePendingTransition(R.anim.hide_rendering_delay, R.anim.fade_out);
            UserPreference.storeState(1);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lodingDialog.dismiss();
                }
            },2500);

            /*
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },3500);
            */
        }
    }

    public void onEventMainThread(ProgressEvent event){
        lodingDialog.show();
    }



    @Override
    protected void onPause() {
        super.onPause();

        EventBus.getDefault().unregister(this);
        //RestActivity.this.finish();
    }


}
