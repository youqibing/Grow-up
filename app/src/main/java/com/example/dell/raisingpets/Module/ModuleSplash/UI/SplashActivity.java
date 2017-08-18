package com.example.dell.raisingpets.Module.ModuleSplash.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.birbit.android.jobqueue.JobManager;
import com.example.dell.raisingpets.Data.SharedPreferences.ToolPerference;
import com.example.dell.raisingpets.Data.SharedPreferences.UserPreference;
import com.example.dell.raisingpets.Module.ModuleDeath.DeathActivity;
import com.example.dell.raisingpets.Module.ModuleLogin.UI.RegisterActivity;
import com.example.dell.raisingpets.Module.ModuleMain.FroestActivity;
import com.example.dell.raisingpets.Module.ModuleMain.Job_and_Event.PostStepsAndMoneyJob;
import com.example.dell.raisingpets.Module.ModuleMain.CityActivity;
import com.example.dell.raisingpets.Module.ModuleRest.RestActivity;
import com.example.dell.raisingpets.Module.ModuleSetting.SettingActivity;
import com.example.dell.raisingpets.Module.ModuleStep.SensorService;
import com.example.dell.raisingpets.Module.ModuleLogin.UI.LoginActivity;
import com.example.dell.raisingpets.Module.ModuleSplash.Job_and_Event.PostTokenJob;
import com.example.dell.raisingpets.Module.ModuleWelcome.Activity.WelcomeActivity;
import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.Util.ToastUtil;
import com.example.dell.raisingpets.Whole.RaisingPetsApplication;
import com.example.dell.raisingpets.Whole.UI.BaseActivity;

/**
 * Created by dell on 2016/8/10.
 *
 */

public class SplashActivity extends BaseActivity {

    private JobManager jobManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        setImmerseLayout(findViewById(R.id.splash_activity));
        //startService(new Intent(SplashActivity.this, SensorService.class));

        //RaisingPetsApplication.getInstance().addActivity(this);
        jobManager = RaisingPetsApplication.getInstance().getJobManager();

        init();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    public void init(){
        /*
        if(ToolPerference.isAPPFirstUse()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
                    SplashActivity.this.finish();
                }
            }, 2000);
        }else
         */

        if (UserPreference.getToken().equals("") ){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    SplashActivity.this.finish();
                }
            },1000);
        }else {

            PostToken();
            PostSteps();


            if(UserPreference.getBackground().equals("forest")){    //这里直接进入主页比较好,不要干等着下载数据
                if(UserPreference.getState() == 1){

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SplashActivity.this, FroestActivity.class));
                            //overridePendingTransition(R.anim.hide_rendering_delay, R.anim.fade_out);
                            SplashActivity.this.finish();
                        }
                    },200);

                }else if(UserPreference.getState() == 2){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SplashActivity.this, RestActivity.class));
                        }
                    },1000);
                }else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SplashActivity.this, DeathActivity.class));
                            overridePendingTransition(R.anim.hide_rendering_delay, R.anim.fade_out);
                        }
                    },200);
                }
            }else if(UserPreference.getBackground().equals("city")){
                if(UserPreference.getState() == 1){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SplashActivity.this, CityActivity.class));
                            overridePendingTransition(R.anim.hide_rendering_delay, R.anim.fade_out);
                        }
                    },200);
                }else if(UserPreference.getState() == 2){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SplashActivity.this, RestActivity.class));
                        }
                    },1000);
                }
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, DeathActivity.class));
                        overridePendingTransition(R.anim.hide_rendering_delay, R.anim.fade_out);
                    }
                },200);
            }


        }

    }


    public void PostToken(){
        ToastUtil.showLongToast("正在上传Token,请稍后~~");
        jobManager.addJobInBackground(new PostTokenJob(UserPreference.getToken()));
    }

    public void PostSteps(){
        jobManager.addJobInBackground(new PostStepsAndMoneyJob(
                UserPreference.getTodayPaceNum(),UserPreference.getMoney()));
    }

    /*
    public void onEventMainThread(PostTokenEvent event){

        boolean isSuccess = event.isSuccess();
        if(isSuccess){
            if(UserPreference.getState() == 2){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, RestActivity.class));
                    }
                },2000);
            }else if(UserPreference.getState() == 1){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, FroestActivity.class));
                        overridePendingTransition(R.anim.hide_rendering_delay, R.anim.fade_out);
                    }
                },200);
            }

        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    ToastUtil.showLongToast("Token上传失败,请重新登陆!!!");
                }
            },2000);
        }
    }
    */



}
