package com.example.dell.raisingpets.Module.ModuleLogin.UI;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.birbit.android.jobqueue.JobManager;
import com.example.dell.raisingpets.Data.SharedPreferences.ToolPerference;
import com.example.dell.raisingpets.Module.ModuleMain.FroestActivity;
import com.example.dell.raisingpets.Module.ModuleLogin.Job_and_Event.RegisterEvent;
import com.example.dell.raisingpets.Module.ModuleLogin.Job_and_Event.RegisterJob;
import com.example.dell.raisingpets.Module.ModuleLogin.Job_and_Event.SMSVerificationJob;
import com.example.dell.raisingpets.Module.ModuleMain.Job_and_Event.ProgressEvent;
import com.example.dell.raisingpets.R;

import com.example.dell.raisingpets.Util.NoDoubleClickUtils;
import com.example.dell.raisingpets.Util.ToastUtil;
import com.example.dell.raisingpets.View.IphoneStyleDialog;
import com.example.dell.raisingpets.Whole.RaisingPetsApplication;
import com.example.dell.raisingpets.Whole.UI.BaseActivity;

import org.json.JSONObject;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

//import javax.inject.Inject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;
import de.greenrobot.event.EventBus;

import static com.mob.tools.utils.R.getStringRes;


/**
 * Created by dell on 2016/7/28.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener,View.OnTouchListener{

    private EditText phoneNum_edt;
    private EditText checkNum_edt;
    private EditText passWord_edt;
    private EditText passWord_edt_repeat;
    private Button send_btn;
    private Button register_btn;
    private TextView logo_tx;
    private Dialog lodingDialog;

    private RelativeLayout register_activity;
    private RelativeLayout show_password_relative;
    private RelativeLayout show_password_relative_repeat;

    private EventHandler handler;

    private TimerTask task;

    private static final int CHANGE_TIME = 1;
    private static final int CANCEL_TIME = 2;

    private JobManager jobManager;
    private boolean isShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        SMSVerificationJob.initSDK(this);//初始化SDK
        RaisingPetsApplication.getInstance().addActivity(this);

        phoneNum_edt = (EditText)findViewById(R.id.phoneNum_edt);
        checkNum_edt = (EditText)findViewById(R.id.checkCode_edt);
        passWord_edt = (EditText)findViewById(R.id.password_edt);
        passWord_edt_repeat = (EditText)findViewById(R.id.repeat_password_edt);
        logo_tx = (TextView)findViewById(R.id.logo_tx);
        send_btn = (Button)findViewById(R.id.send_checkCode_btn);
        register_btn = (Button)findViewById(R.id.register_btn);
        register_activity = (RelativeLayout)findViewById(R.id.register_activity);
        show_password_relative = (RelativeLayout)findViewById(R.id.show_password_relative);
        show_password_relative_repeat = (RelativeLayout)findViewById(R.id.show_password_relative_repeat);
        send_btn.setText(R.string.get_CheckCode);

        phoneNum_edt.setOnClickListener(this);
        checkNum_edt.setOnClickListener(this);
        send_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        logo_tx.setOnClickListener(this);
        logo_tx.setOnTouchListener(this);
        show_password_relative.setOnClickListener(this);
        show_password_relative.setOnTouchListener(this);
        show_password_relative_repeat.setOnClickListener(this);
        show_password_relative_repeat.setOnTouchListener(this);

        lodingDialog = IphoneStyleDialog.loadingDialog(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //ShowEnterAnimation();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    animateRevealShow();
                }
            }, 50);

        }

        setImmerseLayout(findViewById(R.id.register_activity));

        handler = new EventHandler(){
            @Override
            public void afterEvent(final int event, final int result, final Object data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("testResult",result+"");
                        Log.e("testEvent",event+"");
                        Log.e("testData",data+"");
                        if(result == SMSSDK.RESULT_COMPLETE){
                            if(event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                                lodingDialog.dismiss();
                                ToastUtil.showLongToast("短信已发送");

                                ToolPerference.putBeginTime(RegisterActivity.this,System.currentTimeMillis());
                                beginTimeCount();

                            } else if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                                ToastUtil.showLongToast("验证成功");

                                String phoneNum = phoneNum_edt.getText().toString().trim().replaceAll("\\s*", "");
                                String passWord = passWord_edt.getText().toString();

                                register(phoneNum,passWord);

                            }
                        }else{
                            int status = 0;
                            // 根据服务器返回的网络错误，给toast提示
                            try {
                                ((Throwable) data).printStackTrace();
                                Throwable throwable = (Throwable) data;

                                JSONObject object = new JSONObject(
                                        throwable.getMessage());
                                String des = object.optString("detail");
                                status = object.optInt("status");
                                if (!TextUtils.isEmpty(des)) {

                                    ToastUtil.showShortToast(des);
                                    return;
                                }
                            } catch (Exception e) {
                                SMSLog.getInstance().w(e);
                            }
                            // 如果木有找到资源，默认提示
                            int resId = 0;
                            if(status >= 400) {
                                resId = getStringRes(RegisterActivity.this, "smssdk_error_desc_"+status);
                            } else {
                                resId = getStringRes(RegisterActivity.this, "smssdk_network_error");
                            }

                            if (resId > 0) {
                                ToastUtil.showShortToast(resId);
                            }
                        }
                    }

                });
            }
        };

        jobManager = RaisingPetsApplication.getInstance().getJobManager();

    }

    public void register(String phoneNum,String passWord){
        jobManager.addJobInBackground(new RegisterJob(phoneNum,passWord));
    }


    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        SMSSDK.registerEventHandler(handler);//注册回调监听接口
    }


    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        SMSSDK.unregisterEventHandler(handler);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.send_checkCode_btn:
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    //不知道为什么,这里的电话号码以及验证码必须要用局部变量,否则识别不了.
                    String phone = phoneNum_edt.getText().toString().trim().replaceAll("\\s*", "");
                    //向服务器请求发送验证码的服务，需要传递国家代号和接收验证码的手机号码
                    SMSVerificationJob.getVerificationCode(phone);
                    lodingDialog.show();
                }

                break;

            case R.id.register_btn:
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    String phoneNum = phoneNum_edt.getText().toString().trim().replaceAll("\\s*", "");
                    String verificationCode = checkNum_edt.getText().toString().trim().replaceAll("\\s*", "");

                    String passWord = passWord_edt.getText().toString();
                    if(passWord.length() >= 6 && passWord.length() <= 12){
                        if(Objects.equals(passWord_edt.getText().toString(), passWord_edt_repeat.getText().toString())) {
                            register(phoneNum,passWord);
                            //向服务器提交验证码
                            //SMSVerificationJob.submitVerificationCode(phoneNum,verificationCode);
                        }else {
                            ToastUtil.showLongToast("两次输入密码不一致");
                        }
                    }else {
                        ToastUtil.showLongToast("密码格式有误,请检查密码长度是否为6-12位");
                    }
                }

                break;

            case R.id.logo_tx:
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    animateRevealClose();
                }

                break;

            case R.id.show_password_relative:
                if (!isShow){
                    isShow = true;
                    ((ImageView)findViewById(R.id.show_password_img)).setImageResource(R.mipmap.show_password_img);
                    passWord_edt.setTransformationMethod(new PasswordTransformationMethod());
                }else {
                    isShow = false;
                    passWord_edt.setTransformationMethod(null);
                    ((ImageView)findViewById(R.id.show_password_img)).setImageResource(R.mipmap.unshow_password_img);
                }
                break;

            case R.id.show_password_relative_repeat:

                if (!isShow){
                    isShow = true;
                    passWord_edt_repeat.setTransformationMethod(new PasswordTransformationMethod());
                    ((ImageView)findViewById(R.id.show_password_img_repeat)).setImageResource(R.mipmap.show_password_img);
                }else {
                    isShow = false;
                    passWord_edt_repeat.setTransformationMethod(null);
                    ((ImageView)findViewById(R.id.show_password_img_repeat)).setImageResource(R.mipmap.unshow_password_img);
                }
                break;

        }
    }

    private void beginTimeCount(){
        final long lastTime = ToolPerference.getBeginTime(this);
        long currentTime = System.currentTimeMillis();
        if((currentTime - lastTime) < 60*1000){
            send_btn.setClickable(false);
            task = new TimerTask() {
                @Override
                public void run() {
                    int lastT = 60 - (int)((System.currentTimeMillis() - lastTime)/1000);
                    Message msg = new Message();
                    msg.what = CHANGE_TIME;
                    msg.obj = lastT + "s";
                    mhandler.sendMessage(msg);
                    if(lastT < 0){
                        Message message = new Message();
                        message.what = CANCEL_TIME;
                        mhandler.sendMessage(message);
                    }
                }
            };

            new Timer().schedule(task,0,1000);
        }
    }


    Handler mhandler = new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case CHANGE_TIME:
                    send_btn.setText((String)message.obj);
                    break;
                case CANCEL_TIME:
                    task.cancel();
                    send_btn.setClickable(true);
                    send_btn.setText(R.string.get_CheckCode);
                    break;
            }
        }

    };


    public void onEventMainThread(RegisterEvent event) {

        boolean isSuccess = event.isSuccess();
        if(isSuccess){
            lodingDialog.dismiss();
            startActivity(new Intent(this,FroestActivity.class));
            //overridePendingTransition(R.anim.hide_rendering_delay, R.anim.fade_out);

            ToastUtil.showLongToast("注册成功");
        }else {
            ToastUtil.showLongToast("注册失败");
        }
        this.finish();

    }

    public void onEventMainThread(ProgressEvent event){
        lodingDialog.show();
    }


    private void ShowEnterAnimation() {
        Transition transition = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setSharedElementEnterTransition(transition);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                    //cvAdd.setVisibility(FragmentLayout.GONE);
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        transition.removeListener(this);
                    }
                    animateRevealShow();
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }


            });
        }
    }


    public void animateRevealShow() {
        Animator mAnimator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mAnimator = ViewAnimationUtils.createCircularReveal(register_activity, register_activity.getWidth()/2,0, 0, register_activity.getHeight());
        }
        mAnimator.setDuration(1000);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                register_activity.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }


    public void animateRevealClose() {
        Animator mAnimator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mAnimator = ViewAnimationUtils.createCircularReveal(register_activity,register_activity.getWidth()/2,0, register_activity.getHeight(), 0);
        }
        mAnimator.setDuration(1000);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                register_activity.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                //fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }
    @Override
    public void onBackPressed() {
        animateRevealClose();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.logo_tx:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        ((RelativeLayout)findViewById(R.id.logo_relative)).setBackgroundResource(R.color.clickDown);
                        break;
                    case MotionEvent.ACTION_UP:
                        ((RelativeLayout)findViewById(R.id.logo_relative)).setBackgroundResource(R.color.clickUp);
                        break;
                }
                break;

            case R.id.show_password_relative:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        ((RelativeLayout)findViewById(R.id.show_password_relative)).setBackgroundResource(R.color.clickDown);
                        break;
                    case MotionEvent.ACTION_UP:
                        ((RelativeLayout)findViewById(R.id.show_password_relative)).setBackgroundResource(R.color.clickUp);
                        break;
                }
                break;

            case R.id.show_password_relative_repeat:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        ((RelativeLayout)findViewById(R.id.show_password_relative_repeat)).setBackgroundResource(R.color.clickDown);
                        break;
                    case MotionEvent.ACTION_UP:
                        ((RelativeLayout)findViewById(R.id.show_password_relative_repeat)).setBackgroundResource(R.color.clickUp);
                        break;
                }
                break;

        }
        return false;

    }
}
