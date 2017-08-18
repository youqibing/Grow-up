package com.example.dell.raisingpets.Module.ModuleDeath;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.birbit.android.jobqueue.JobManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dell.raisingpets.Data.SharedPreferences.ToolPerference;
import com.example.dell.raisingpets.Data.SharedPreferences.UserPreference;
import com.example.dell.raisingpets.Module.ModuleDeath.Job_and_Event.ReliveEvent;
import com.example.dell.raisingpets.Module.ModuleDeath.Job_and_Event.ReliveJob;
import com.example.dell.raisingpets.Module.ModuleMain.FroestActivity;
import com.example.dell.raisingpets.Module.ModuleRest.RestActivity;
import com.example.dell.raisingpets.Module.ModuleRest.startRest.StartRestEvent;
import com.example.dell.raisingpets.Module.ModuleSplash.UI.SplashActivity;
import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.Util.AsyncTaskLoader;
import com.example.dell.raisingpets.Util.IAsyncCallback;
import com.example.dell.raisingpets.Util.ToastUtil;
import com.example.dell.raisingpets.View.IphoneStyleDialog;
import com.example.dell.raisingpets.Whole.RaisingPetsApplication;

import org.andengine.engine.camera.Camera;
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
import org.andengine.ui.activity.LayoutGameActivity;

import de.greenrobot.event.EventBus;
import me.zhouzhuo.zzhorizontalprogressbar.ZzHorizontalProgressBar;

/**
 * Created by root on 16-12-29.
 */

public class DeathActivity extends LayoutGameActivity implements View.OnClickListener {

    private static final int CAMERA_WIDTH = 650;
    private static final int CAMERA_HEIGHT = 1000;

    private AnimatedSprite player;
    private TiledTextureRegion mPlayerTextureRegion;
    private AutoParallaxBackground autoParallaxBackground;

    private TextureRegion mParallaxLayerFour;
    private TextureRegion mParallaxLayerOne;
    private BitmapTextureAtlas mAutoFourTexture;
    private BitmapTextureAtlas mAutoOneTexture;
    private BitmapTextureAtlas mBitmapGoastTextureAtlas;

    private TextView steps_tx;
    private TextView money_tx;
    private ImageView setting_img;
    private Button relive_btn;
    private Button restart_btn;

    private Dialog reliveDialog;
    private JobManager jobManager;

    @Override
    protected void onCreate(Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);

        jobManager = RaisingPetsApplication.getInstance().getJobManager();
        RaisingPetsApplication.getInstance().addActivity(this);
        reliveDialog = IphoneStyleDialog.reliveDialog(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected synchronized void onResume() {
        super.onResume();
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
                    2048, 4096, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
            mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory
                    .createTiledFromAsset(mBitmapGoastTextureAtlas, this,
                            "goast.png", 0, 0, 5, 6);  //0,0代表图片从哪个位置开始取起始点剪裁,5代表一行有5个人物,31代表一共有31行.
            mBitmapGoastTextureAtlas.load();
        }

        mAutoFourTexture = new BitmapTextureAtlas(
                getTextureManager(), 4096, 2048, TextureOptions.DEFAULT);
        mAutoOneTexture = new BitmapTextureAtlas(
                getTextureManager(), 4096, 2048, TextureOptions.DEFAULT);

        this.mParallaxLayerFour = (TextureRegion) BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(mAutoFourTexture, this, "four_snow_mountain.png", 0, 0);
        this.mParallaxLayerOne = (TextureRegion) BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(mAutoOneTexture, this, "one_tree.png", 0, 0);

        mAutoFourTexture.load();
        mAutoOneTexture.load();

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
        final Scene mScene = new Scene();

        autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 0);

        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-3.0f,
                new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerFour.getHeight()
                        , this.mParallaxLayerFour, getVertexBufferObjectManager())));

        autoParallaxBackground.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-22.0f,
                new Sprite(0, CAMERA_HEIGHT - this.mParallaxLayerOne.getHeight(),
                        this.mParallaxLayerOne, getVertexBufferObjectManager())));
        // 设置背景
        mScene.setBackground(autoParallaxBackground);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                IAsyncCallback callback = new IAsyncCallback() {
                    @Override
                    public void workToDo() {
                        mBitmapGoastTextureAtlas = new BitmapTextureAtlas(getTextureManager(),
                                2048, 4096, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

                        mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory
                                .createTiledFromAsset(mBitmapGoastTextureAtlas, DeathActivity.this,
                                        "goast.png", 0, 0, 5, 6);  //0,0代表图片从哪个位置开始取起始点剪裁,5代表一行有5个人物,31代表一共有31行.
                        mBitmapGoastTextureAtlas.load();
                    }

                    @Override
                    public void onComplete() {
                        initView();

                    }
                };
                new AsyncTaskLoader().execute(callback);
            }
        });

        final int playerX = (int) (CAMERA_WIDTH - mPlayerTextureRegion.getWidth()) / 2 ;
        final int playerY = (int) (CAMERA_HEIGHT - mPlayerTextureRegion.getHeight()) - 80;

        initSprit(playerX,playerY);
        mScene.attachChild(player);

        pOnCreateSceneCallback.onCreateSceneFinished(mScene);
    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
        pOnPopulateSceneCallback.onPopulateSceneFinished();
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
            }, 0, 29, true);//3,5表示从第3张图片到第5张图片,true表示循环播放。
    }

    private void initView(){

        setting_img = (ImageView)findViewById(R.id.setting);
        setting_img.setOnClickListener(this);

        ((RelativeLayout)findViewById(R.id.headPhoto_realtive)).setBackgroundResource(R.mipmap.headphoto);
        ((ImageView)findViewById(R.id.money)).setBackgroundResource(R.mipmap.money);
        ((ImageView)findViewById(R.id.steps)).setBackgroundResource(R.mipmap.steps);

        Glide.with(this)
                .load(UserPreference.getHeadUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop().into((ImageView)findViewById(R.id.avatar));

        ((TextView)findViewById(R.id.nickName)).setText(UserPreference.getNickName());

        steps_tx = (TextView)findViewById(R.id.steps_tx);
        steps_tx.setText( UserPreference.getTodayPaceNum()+"");

        money_tx = (TextView)findViewById(R.id.money_tx);
        money_tx.setText(UserPreference.getMoney() + "");

        ((ImageView)findViewById(R.id.setting)).setBackgroundResource(R.mipmap.night_setting);
        ((TextView)findViewById(R.id.nickName)).setTextColor(Color.WHITE);
        steps_tx.setTextColor(Color.WHITE);
        money_tx.setTextColor(Color.WHITE);

        relive_btn = (Button)reliveDialog.findViewById(R.id.relive);
        restart_btn = (Button)reliveDialog.findViewById(R.id.restart);
        relive_btn.setOnClickListener(this);
        restart_btn.setOnClickListener(this);

        reliveDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relive:
                jobManager.addJobInBackground(new ReliveJob(1));
                break;

            case R.id.restart:
                jobManager.addJobInBackground(new ReliveJob(0));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            reliveDialog.dismiss();
            RaisingPetsApplication.getInstance().exit();
        }
        return false;
    }

    public void onEventMainThread(ReliveEvent event){
        if(event.isSuccess()){
            ToastUtil.showShortToast("复活成功");
            startActivity(new Intent(DeathActivity.this, SplashActivity.class));
            finish();
        }else {
            ToastUtil.showShortToast(event.getMsg());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
