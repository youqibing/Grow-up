package com.example.dell.raisingpets.Module.ModuleMain;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.timer.SystemTimer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dell.raisingpets.Data.SharedPreferences.ToolPerference;
import com.example.dell.raisingpets.Data.SharedPreferences.UserPreference;
import com.example.dell.raisingpets.Module.ModuleBackgroud.Job_and_Event.ChangeBackgroudEvent;
import com.example.dell.raisingpets.Module.ModuleFood.FoodAdapter;
import com.example.dell.raisingpets.Module.ModuleFood.Job_and_Event.FoodEvent;
import com.example.dell.raisingpets.Module.ModuleFood.Job_and_Event.FoodJob;
import com.example.dell.raisingpets.Module.ModuleFood.Job_and_Event.PurchaseFoodEvent;
import com.example.dell.raisingpets.Module.ModuleFood.Job_and_Event.PurchaseFoodJob;
import com.example.dell.raisingpets.Module.ModuleMain.Job_and_Event.FinishChangeEvent;
import com.example.dell.raisingpets.Module.ModuleMain.Job_and_Event.PostStepsAndMoneyJob;
import com.example.dell.raisingpets.Module.ModuleMain.Job_and_Event.ProgressEvent;
import com.example.dell.raisingpets.Module.ModuleMine.Job_and_Event.NickNameEvent;
import com.example.dell.raisingpets.Module.ModuleRest.RestActivity;
import com.example.dell.raisingpets.Module.ModuleRest.startRest.StartRestEvent;
import com.example.dell.raisingpets.Module.ModuleRest.startRest.StartRestJob;
import com.example.dell.raisingpets.Module.ModuleMine.Job_and_Event.HeaderPhotoEvent;
import com.example.dell.raisingpets.Module.ModuleSetting.SettingActivity;
import com.example.dell.raisingpets.Module.ModuleSplash.UI.SplashActivity;
import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.Module.ModuleStep.SensorService;
import com.example.dell.raisingpets.Util.AsyncTaskLoader;
import com.example.dell.raisingpets.Util.IAsyncCallback;
import com.example.dell.raisingpets.Util.NoDoubleClickUtils;
import com.example.dell.raisingpets.Util.TimeUtils;
import com.example.dell.raisingpets.Util.ToastUtil;
import com.example.dell.raisingpets.View.FragmentLayout;
import com.example.dell.raisingpets.View.IphoneStyleDialog;
import com.example.dell.raisingpets.View.VerticalAnimator;
import com.example.dell.raisingpets.Whole.RaisingPetsApplication;

import org.andengine.engine.Engine;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.view.RenderSurfaceView;
import org.andengine.ui.activity.LayoutGameActivity;

import de.greenrobot.event.EventBus;
import me.zhouzhuo.zzhorizontalprogressbar.ZzHorizontalProgressBar;

import static android.graphics.PixelFormat.RGBA_4444;


/**
 * Created by dell on 2016/8/27.
 *
 */

public class FroestActivity extends LayoutGameActivity implements View.OnClickListener ,View.OnTouchListener, SensorEventListener {

    private static final int CAMERA_WIDTH = 580;
    private static final int CAMERA_HEIGHT = 1000;

    private AutoParallaxBackground autoParallaxBackground;
    private TiledTextureRegion mPlayerTextureRegion;


    private TextureRegion mParallaxLayerFour;
    private TextureRegion mParallaxLayerThree;
    private TextureRegion mParallaxLayerTwo;
    private TextureRegion mParallaxLayerOne;


    private BitmapTextureAtlas mBitmapGoastTextureAtlas;
    private BitmapTextureAtlas mAutoFourTexture;
    private BitmapTextureAtlas mAutoThreeTexture;
    private BitmapTextureAtlas mAutoTwoTexture;
    private BitmapTextureAtlas mAutoOneTexture;

    private AnimatedSprite player;
    private PhysicsHandler physicsHandler;

    private RelativeLayout animatorView;
    private LinearLayout functionView;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private VerticalAnimator animator;
    private TextView steps_tx;
    private TextView money_tx;
    private ImageView rest_img;
    private ImageView front_img;
    private ImageView setting_img;
    private ImageView eat_img;

    private RecyclerView eat_recyclerView;
    private FoodAdapter eat_adapter;

    private SensorManager sensorManager;
    private Sensor sensor;
    private JobManager jobManager;

    private boolean isShow = true;
    private boolean isInit = false;
    private int since_boot ;

    private Dialog foodDialog;
    private Button purchase_btn;
    private Dialog textDialog;
    private Dialog lodingDialog;
    private int addPaceFlag = 0;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isInit = false;
        initLayout();

        jobManager = RaisingPetsApplication.getInstance().getJobManager();
        startService(new Intent(this, SensorService.class));
        sensorManager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        super.onResume();
        startService(new Intent(FroestActivity.this, SensorService.class));

        if (!ToolPerference.getPedometerpreferences().contains("pauseCount")) {
            if (sensor == null) {
                ToastUtil.showLongToast("未检测到计步传感器,您的应用暂不支持计步应用哟!!!");
            } else {
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI, 0);
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_froest;
    }

    @Override
    protected int getRenderSurfaceViewID() {
        return R.id.gameSurfaceView;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        Camera mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        EngineOptions mEngineOptions = new EngineOptions(true,
                ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), mCamera);
        mEngineOptions.getRenderOptions().setDithering(true);
        return mEngineOptions;
    }


    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {

        if(!ToolPerference.isFroestBackgroudFirstUse()){
            BitmapTextureAtlas mBitmapGoastTextureAtlas = new BitmapTextureAtlas(getTextureManager(),
                    1024, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory
                    .createTiledFromAsset(mBitmapGoastTextureAtlas, this,
                            "player.png", 0, 0, 5, 9);  //0,0代表图片从哪个位置开始取起始点剪裁,5代表一行有5个人物,31代表一共有31行.
            mBitmapGoastTextureAtlas.load();
        }


        mAutoFourTexture = new BitmapTextureAtlas(getTextureManager(), 2048, 1024, TextureOptions.DEFAULT);
        mAutoThreeTexture = new BitmapTextureAtlas(getTextureManager(), 2048, 1024, TextureOptions.DEFAULT);
        mAutoTwoTexture = new BitmapTextureAtlas(getTextureManager(), 2048, 1024, TextureOptions.DEFAULT);
        mAutoOneTexture = new BitmapTextureAtlas(getTextureManager(), 2048, 1024, TextureOptions.DEFAULT);

        if(TimeUtils.isNight()){
            this.mParallaxLayerFour = BitmapTextureAtlasTextureRegionFactory
                    .createFromAsset(mAutoFourTexture, this, "four_snow_mountain.png", 0, 0);
            this.mParallaxLayerThree = BitmapTextureAtlasTextureRegionFactory
                    .createFromAsset(mAutoThreeTexture, this, "turee_bush.png", 0, 0);
            this.mParallaxLayerTwo = BitmapTextureAtlasTextureRegionFactory
                    .createFromAsset(mAutoTwoTexture, this, "two_hill.png", 0, 0);
            this.mParallaxLayerOne = BitmapTextureAtlasTextureRegionFactory
                    .createFromAsset(mAutoOneTexture, this, "one_tree.png", 0, 0);
        }else {
            this.mParallaxLayerFour = BitmapTextureAtlasTextureRegionFactory
                    .createFromAsset(mAutoFourTexture, this, "day_four.png", 0, 0);
            this.mParallaxLayerThree = BitmapTextureAtlasTextureRegionFactory
                    .createFromAsset(mAutoThreeTexture, this, "day_three.png", 0, 0);
            this.mParallaxLayerTwo = BitmapTextureAtlasTextureRegionFactory
                    .createFromAsset(mAutoTwoTexture, this, "day_two.png", 0, 0);
            this.mParallaxLayerOne = BitmapTextureAtlasTextureRegionFactory
                    .createFromAsset(mAutoOneTexture, this, "day_one.png", 0, 0);
        }

        mAutoFourTexture.load();
        mAutoThreeTexture.load();
        mAutoTwoTexture.load();
        mAutoOneTexture.load();

        pOnCreateResourcesCallback.onCreateResourcesFinished();

    }


    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
        final Scene mScene = new Scene();

        if(ToolPerference.isFroestBackgroudFirstUse()){
            // 最后一个参数这里设为0，意思是让它一开始不要滚动
            autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 0);
        }else {
            autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, -5);
        }

        // 0.0f，-0.5f，-10.0f：你可以把它们理解为相对位置差
        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-3.0f,
                new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerFour.getHeight()
                        , this.mParallaxLayerFour, getVertexBufferObjectManager())));

        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-10.0f,
                new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerThree.getHeight(),
                        this.mParallaxLayerThree, getVertexBufferObjectManager())));

        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-15.0f,
                new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerTwo.getHeight(),
                        this.mParallaxLayerTwo, getVertexBufferObjectManager())));

        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-22.0f,
                new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerOne.getHeight(),
                        this.mParallaxLayerOne, getVertexBufferObjectManager())));
        // 设置背景
        mScene.setBackground(autoParallaxBackground);

        if(ToolPerference.isFroestBackgroudFirstUse()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    IAsyncCallback callback = new IAsyncCallback() {
                        @Override
                        public void workToDo() {
                            mBitmapGoastTextureAtlas = new BitmapTextureAtlas(getTextureManager(),
                                    1024, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

                            mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory
                                    .createTiledFromAsset(mBitmapGoastTextureAtlas, FroestActivity.this,
                                            "player.png", 0, 0, 5, 9);  //0,0代表图片从哪个位置开始取起始点剪裁,5代表一行有5个人物,31代表一共有31行.
                            mBitmapGoastTextureAtlas.load();
                        }

                        @Override
                        public void onComplete() {
                            initView();

                            final int playerX = (int) (CAMERA_WIDTH - mPlayerTextureRegion.getWidth()) / 2 + 500;
                            final int playerY = (int) (CAMERA_HEIGHT - mPlayerTextureRegion.getHeight()) - 70;

                            initSprit(playerX,playerY);
                            //playerX,playerY,Sprite对象的位置，其位置是相对于上级对象的;
                            // mPlayerTextureRegion:存放精灵桢动画的TitleTextureRegion对象。与普通的Sprite对象一样，也是需要通过TextureRegionFactory用一张图片创建TextureRegion对象,
                            // 并载入到Texture中去，然后再用TextureManager将Texture载入缓存中。
                            // getVertexBufferObjectManager():返回VertexBufferObjectManager对象,该参数是OpenGL的数据结构，用来修改精灵的显示方式
                            physicsHandler = new PhysicsHandler(player);
                            player.registerUpdateHandler(physicsHandler);

                            mScene.attachChild(player);
                            SpriteEnterAnimator();
                        }
                    };
                    new AsyncTaskLoader().execute(callback);
                }
            });

        }
        else {

            runOnUiThread(new Runnable() {
                //这里的这个AsyncTask完全就是用来测量游戏界面渲染完成时间的,界面完成后原生的界面上血条什么的才可以加载,不然会出现血条先出来而画面还没出来的情况.
                @Override
                public void run() {
                    IAsyncCallback callback = new IAsyncCallback() {
                        @Override
                        public void workToDo() {
                            mBitmapGoastTextureAtlas = new BitmapTextureAtlas(getTextureManager(),
                                    1024, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

                            mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory
                                    .createTiledFromAsset(mBitmapGoastTextureAtlas, FroestActivity.this,
                                            "player.png", 0, 0, 5, 9);  //0,0代表图片从哪个位置开始取起始点剪裁,5代表一行有5个人物,31代表一共有31行.
                            mBitmapGoastTextureAtlas.load();
                        }

                        @Override
                        public void onComplete() {
                            initView();
                            showTextDialog();
                        }
                    };
                    new AsyncTaskLoader().execute(callback);
                }
            });


            final int playerX =  (int) (CAMERA_WIDTH - mPlayerTextureRegion.getWidth()) /2 ;
            final int playerY = (int) (CAMERA_HEIGHT - mPlayerTextureRegion.getHeight()) - 70;

            initSprit(playerX,playerY);

            mScene.attachChild(player);
            physicsHandler = new PhysicsHandler(player);
            player.registerUpdateHandler(physicsHandler);

        }

        pOnCreateSceneCallback.onCreateSceneFinished(mScene);
    }


    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }


    private void SpriteEnterAnimator(){
        new Handler().postDelayed(new Runnable() {   //开始人物移动进场的代码,这样写真的好蠢,但暂时找不到更好的不出BUG的方法,辣鸡引擎
            @Override
            public void run() {

                int middle = (int)(CAMERA_WIDTH - mPlayerTextureRegion.getWidth()) / 2;
                for(; player.getX()> middle;){
                    physicsHandler.setVelocity(-250,0);
                    if(player.getX() <= middle + 10){
                        showTextDialog();
                        physicsHandler.setVelocity(0,0);
                        autoParallaxBackground.setParallaxChangePerSecond(-5);
                    }
                }

            }
        },2000);

        ToolPerference.storeFroestBackgroudFirstUse(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.buttonFront:
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    if(isShow) {
                        isShow = false;
                        animator.extendFunctionView();
                        front_img.setImageResource(R.mipmap.arrow_down);

                    } else {
                        isShow = true;
                        animator.shrinkFunctionView();
                        showTextDialog();
                        front_img.setImageResource(R.mipmap.arrow_up);
                    }
                }

                break;

            case R.id.setting:
                startActivity(new Intent(FroestActivity.this, SettingActivity.class));
                finish();

                break;

            case R.id.rest:
                if(ToolPerference.getPedometerpreferences().contains("pauseCount")){
                    sensorManager.registerListener(
                            FroestActivity.this,
                            sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),
                            SensorManager.SENSOR_DELAY_UI,
                            0
                    );
                    rest_img.setImageResource(R.mipmap.rest);
                }else {
                    sensorManager.unregisterListener(FroestActivity.this);
                    ToastUtil.showShortToast("正在进入休息状态");

                    jobManager.addJobInBackground(new StartRestJob(0));
                }
                startService(new Intent(FroestActivity.this,SensorService.class).putExtra("action",SensorService.ACTION_PAUSE));

                break;

            case R.id.eat:
                jobManager.addJobInBackground(new FoodJob());
                foodDialog.show();

                break;

            case R.id.purchase:

                RaisingPetsApplication.getInstance().getJobManager()
                        .addJobInBackground(new PurchaseFoodJob(ToolPerference.getFoodIdentifier(),foodDialog));

                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.setting:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        findViewById(R.id.setting_relative).setBackgroundResource(R.color.clickDown);
                        break;
                    case MotionEvent.ACTION_UP:
                        findViewById(R.id.setting_relative).setBackgroundResource(R.color.clickUp);
                        break;
                }
                break;
        }

        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ){
            if(!ToolPerference.getIsBackEnable()){
                if(!isShow) {

                }else {
                    //RaisingPetsApplication.getInstance().exit();
                    PostSteps();
                    finish();
                    //结束所有的Activity,主要注册界面在跳转的时候没有finish掉,为了掩盖MainActivity开始渲染时由于延时会露出桌面的bug.
                }
            }else {
                if(!isShow) {
                    isShow = true;
                    animator.shrinkFunctionView();
                }else {
                    //RaisingPetsApplication.getInstance().exit();
                    finish();
                    PostSteps();
                }
            }

        }
        return false;
    }

    public void onEventMainThread(final FinishChangeEvent event) {

        lodingDialog.dismiss();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isShow) {
                    isShow = true;
                    animator.shrinkFunctionView();
                }
            }
        },1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                autoParallaxBackground.setParallaxChangePerSecond(0);
                physicsHandler.setVelocity(-250,0);
            }
        },3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (event.identifier) {
                    case "city":
                        startActivity(new Intent(FroestActivity.this, CityActivity.class));
                        ToolPerference.storeCityBackgroudFirstUse(true);
                        finish();
                        break;
                    case "desert":

                        break;
                    default:

                        break;
                }

                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        },5000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FroestActivity.this.finish();
            }
        },7000);

    }


    @Override
    protected void onStop() {
        super.onStop();

        EventBus.getDefault().unregister(this);
        try {
            sensorManager.unregisterListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        mAutoFourTexture.unload();
        mAutoThreeTexture.unload();
        mAutoTwoTexture.unload();
        mAutoOneTexture.unload();
        mBitmapGoastTextureAtlas.unload();
    }




    public void onEventMainThread(StartRestEvent event){
        if(event.isSuccess()){
            lodingDialog.dismiss();
            ToastUtil.showShortToast("成功进入休息状态");
            startActivity(new Intent(FroestActivity.this, RestActivity.class));
            //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            FroestActivity.this.finish();

            /*
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            },3000);
            */

        }
    }

    public void onEventMainThread(PurchaseFoodEvent event) {
        if(event.isSuccess()){
            ToastUtil.showShortToast("购买成功");
            lodingDialog.dismiss();
            money_tx.setText(UserPreference.getMoney() + "");
            ((ZzHorizontalProgressBar) findViewById(R.id.pbone)).setProgress(UserPreference.getHungryPoint());
            ((ZzHorizontalProgressBar) findViewById(R.id.pbtwo)).setProgress(UserPreference.getHP());
            event.getFoodDialog().dismiss();
        }else{
            ToastUtil.showLongToast(event.getMsg());
        }
    }

    public void onEventMainThread(ProgressEvent event){
        lodingDialog.show();
    }

    public void onEventMainThread(FoodEvent event) {
        eat_adapter = new FoodAdapter(foodDialog.getContext(), event.foodLists);

        eat_adapter.notifyDataSetChanged();
        eat_recyclerView.setLayoutManager(new LinearLayoutManager(foodDialog.getContext()));
        eat_recyclerView.setAdapter(eat_adapter);
    }


    public void onEventMainThread(ChangeBackgroudEvent event) {

        boolean isSuccess = event.isSuccess();
        String msg = event.getMsg();
        if(isSuccess){
            lodingDialog.dismiss();
            UserPreference.storeBackground(event.getIdentifier());
            EventBus.getDefault().post(new FinishChangeEvent(true,event.getIdentifier()));
            event.getBackgroudDialog().dismiss();
        }

        ToastUtil.showLongToast(msg);
    }

    public void onEventMainThread(HeaderPhotoEvent event){

        if(event.isSucess()){
            ToastUtil.showShortToast("头像上传成功!");
            lodingDialog.dismiss();
        }else {
            ToastUtil.showShortToast("头像上传失败,请检查网络连接");
        }
    }

    public void onEventMainThread(final NickNameEvent event){
        if(event.isSucess()){
            lodingDialog.dismiss();
            ToastUtil.showShortToast("昵称修改成功!");
            UserPreference.storeNickName(event.nickName());

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    event.getTextView().setText(event.nickName());
                    ((TextView)findViewById(R.id.nickName)).setText(UserPreference.getNickName());
                }
            },1000);
        }
    }



    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.values[0] > Integer.MAX_VALUE || event.values[0] == 0) {
            return;
        }
        since_boot = (int) event.values[0];

        if((SensorService.todayOffset + since_boot) < 0){
            if (SensorService.todayOffset == Integer.MIN_VALUE) {
                //SensorService.todayOffset = -(int) event.values[0];
                UserPreference.storeTodayPaceNum(0);
            }else {
                SensorService.todayOffset = 0;
            }
        }

        int TodayPace = Math.max(SensorService.todayOffset + since_boot , 0);
        int TodayPaceOffset = TodayPace - ToolPerference.getShutDownOffset();
        ToolPerference.storeShutDownOffset(TodayPace);
        UserPreference.storeTodayPaceNum(UserPreference.getTodayPaceNum() + TodayPaceOffset);

        if(isInit){
            steps_tx.setText(UserPreference.getTodayPaceNum()+"");
            if(UserPreference.getTodayPaceNum() < 8000){
                setAllMoney(Math.abs((int)(Math.abs(UserPreference.getTodayPaceNum()*0.02))));
            }else if(UserPreference.getTodayPaceNum() < 1000){
                setAllMoney((int)(Math.abs(160 + (UserPreference.getTodayPaceNum() - 8000)*0.01)));
            }
            money_tx.setText(Math.abs(Math.abs(UserPreference.getMoney())) + "");
        }

        addPaceFlag += TodayPaceOffset; //每隔100步上传一次步数.
        if(addPaceFlag > 100){
            addPaceFlag = 0;
            PostSteps();
        }
    }


    public void PostSteps(){
        jobManager.addJobInBackground(new PostStepsAndMoneyJob(
                UserPreference.getTodayPaceNum(),UserPreference.getMoney()));
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void initLayout(){

        animatorView = (RelativeLayout) findViewById(R.id.animator);
        functionView = (LinearLayout)findViewById(R.id.function);

        tabLayout = (TabLayout)findViewById(R.id.tab);
        viewPager = (ViewPager)findViewById(R.id.viewpager);

        new FragmentLayout(this,viewPager,tabLayout,getSupportFragmentManager());
        animator = new VerticalAnimator(functionView,animatorView,this);

        ((ZzHorizontalProgressBar)findViewById(R.id.pbone)).setVisibility(View.INVISIBLE);
        ((ZzHorizontalProgressBar)findViewById(R.id.pbtwo)).setVisibility(View.INVISIBLE);
        ((RelativeLayout)findViewById(R.id.buttonFront_relative)).setVisibility(View.INVISIBLE);
        ((ImageView)findViewById(R.id.buttonFront)).setVisibility(View.INVISIBLE);

        textDialog = IphoneStyleDialog.textDialog(this);
        lodingDialog = IphoneStyleDialog.loadingDialog(this);
    }

    private void initView(){

        ((RelativeLayout)findViewById(R.id.buttonFront_relative)).setVisibility(View.VISIBLE);
        front_img = (ImageView) findViewById(R.id.buttonFront);
        front_img.setVisibility(View.VISIBLE);
        front_img.setImageResource(R.mipmap.arrow_up);
        front_img.setOnClickListener(this);

        setting_img = (ImageView)findViewById(R.id.setting);
        setting_img.setOnClickListener(this);
        setting_img.setOnTouchListener(this);

        rest_img = (ImageView)findViewById(R.id.rest);
        rest_img.setOnClickListener(this);

        eat_img = (ImageView)findViewById(R.id.eat);
        eat_img.setOnClickListener(this);

        ((RelativeLayout)findViewById(R.id.headPhoto_realtive)).setBackgroundResource(R.mipmap.headphoto);
        ((ImageView)findViewById(R.id.money)).setBackgroundResource(R.mipmap.money);
        ((ImageView)findViewById(R.id.steps)).setBackgroundResource(R.mipmap.steps);
        ((ImageView)findViewById(R.id.booled)).setBackgroundResource(R.mipmap.booled);
        ((ImageView)findViewById(R.id.food)).setBackgroundResource(R.mipmap.food);
        ((ImageView)findViewById(R.id.eat)).setBackgroundResource(R.mipmap.eat);
        ((ImageView)findViewById(R.id.rest)).setBackgroundResource(R.mipmap.rest);

        Glide.with(this)
                .load(UserPreference.getHeadUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop().into((ImageView)findViewById(R.id.avatar));

        ((TextView)findViewById(R.id.nickName)).setText(UserPreference.getNickName());

        if(ToolPerference.getPedometerpreferences().contains("pauseCount")){
            rest_img.setImageResource(R.mipmap.eat);
        }else {
            rest_img.setImageResource(R.mipmap.rest);
        }

        steps_tx = (TextView)findViewById(R.id.steps_tx);
        steps_tx.setText( Math.abs(UserPreference.getTodayPaceNum())+"");

        money_tx = (TextView)findViewById(R.id.money_tx);
        if(UserPreference.getTodayPaceNum() < 8000){
            setAllMoney(Math.abs((int)(UserPreference.getTodayPaceNum()*0.02)));
        }else if(UserPreference.getTodayPaceNum() < 1000){
            setAllMoney(Math.abs((int)(160 + (UserPreference.getTodayPaceNum() - 8000)*0.01)));
        }
        money_tx.setText(Math.abs(Math.abs(UserPreference.getMoney())) + "");

        if(TimeUtils.isNight()){
            ((ImageView)findViewById(R.id.setting)).setBackgroundResource(R.mipmap.night_setting);
            ((TextView)findViewById(R.id.nickName)).setTextColor(Color.WHITE);
            steps_tx.setTextColor(Color.WHITE);
            money_tx.setTextColor(Color.WHITE);
        }else {
            ((ImageView)findViewById(R.id.setting)).setBackgroundResource(R.mipmap.day_setting);
            ((TextView)findViewById(R.id.nickName)).setTextColor(Color.BLACK);
            steps_tx.setTextColor(Color.BLACK);
            money_tx.setTextColor(Color.BLACK);
        }

        ZzHorizontalProgressBar pbone = (ZzHorizontalProgressBar) findViewById(R.id.pbone);
        ZzHorizontalProgressBar pbtwo = (ZzHorizontalProgressBar) findViewById(R.id.pbtwo);
        pbone.setVisibility(View.VISIBLE);
        pbtwo.setVisibility(View.VISIBLE);
        pbone.setProgress(UserPreference.getHungryPoint());
        pbtwo.setProgress(UserPreference.getHP());
        pbone.setMax(100);
        pbtwo.setMax(100);

        foodDialog = IphoneStyleDialog.foodDialog(this);
        eat_recyclerView = (RecyclerView)foodDialog.findViewById(R.id.food_recycle);
        purchase_btn = (Button)foodDialog.findViewById(R.id.purchase);
        purchase_btn.setOnClickListener(this);

        isInit = true;
    }

    public void setAllMoney(int todayMoney){
        int money = todayMoney - ToolPerference.getMoneyOffset();
        ToolPerference.storeMoneyOffset(todayMoney);
        UserPreference.storeMoney(UserPreference.getMoney() + money);
        PostSteps();    //把钱算完了再传一遍.
    }

    public void initSprit(int playerX,int playerY){
        player = new AnimatedSprite(playerX, playerY, mPlayerTextureRegion, getVertexBufferObjectManager());
        player.setScaleCenterY(mPlayerTextureRegion.getHeight());
        player.setCullingEnabled(true);
        player.setScale(2);
        player.animate(new long[]{
                50, 50, 50, 50, 50,
                50, 50, 50, 50, 50,
                50, 50, 50, 50, 50,
                50, 50, 50, 50, 50,
                50, 50, 50, 50, 50,
                50, 50, 50, 50, 50,
                50, 50, 50, 50, 50,
                50, 50, 50, 50, 50,
                50, 50, 50, 50, 50,
                }, 0, 44, true);//3,5表示从第3张图片到第5张图片,true表示循环播放。
    }

    private void showTextDialog(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textDialog.show();
            }
        },800);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textDialog.dismiss();
            }
        },3000);
    }



}
