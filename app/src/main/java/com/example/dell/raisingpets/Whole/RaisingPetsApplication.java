package com.example.dell.raisingpets.Whole;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.birbit.android.jobqueue.log.CustomLogger;
import com.example.dell.raisingpets.Module.ModuleMain.FroestActivity;
import com.example.dell.raisingpets.Util.TimeUtils;
import com.squareup.leakcanary.LeakCanary;

import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/7/30.
 */

public class RaisingPetsApplication extends Application {
    private JobManager jobManager;

    private static Context context;
    private static RaisingPetsApplication instance;

    public static boolean useMobile = false;
    private List<Activity> list = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        configureJobManager();
        LeakCanary.install(this);
    }

    public RaisingPetsApplication(){    //获取RaisingPetsApplication类的实例。
        instance = this;
    }

    public static Context getContext(){     //获取RaisingPetsApplication类的上下文，由于我们在mainfinest中已经设置，
        return context;                                                  //所以这里的上下文就是整个APP的上下文。
    }

    public static RaisingPetsApplication getInstance(){
        return instance;
    }

    public void configureJobManager(){
        Configuration configuration = new Configuration.Builder(this)
                .customLogger(new CustomLogger() {
                    private static final String TAG = "JOBS";

                    @Override
                    public boolean isDebugEnabled() {
                        return true;
                    }

                    @Override
                    public void d(String text, Object... args) {

                    }

                    @Override
                    public void e(Throwable t, String text, Object... args) {

                    }

                    @Override
                    public void e(String text, Object... args) {

                    }

                    @Override
                    public void v(String text, Object... args) {

                    }
                })
                .minConsumerCount(1)//always keep at least one consumer alive
                .maxConsumerCount(3)//up to 3 consumers at a time
                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(120)//wait 2 minute
                .build();
        jobManager = new JobManager(configuration);
    }

    public JobManager getJobManager(){
        return jobManager;
    }

    public void addActivity(Activity activity) {
        list.add(activity);
    }

    public void exit() {

        try {
            for (Activity activity : list) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
