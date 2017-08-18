package com.example.dell.raisingpets.Module.ModuleLogin.UI;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.birbit.android.jobqueue.JobManager;
import com.example.dell.raisingpets.Module.ModuleMain.FroestActivity;
import com.example.dell.raisingpets.Module.ModuleLogin.Job_and_Event.LoginEvent;
import com.example.dell.raisingpets.Module.ModuleLogin.Job_and_Event.LoginJob;
import com.example.dell.raisingpets.Module.ModuleMain.Job_and_Event.ProgressEvent;
import com.example.dell.raisingpets.Module.ModuleStep.SensorService;
import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.Util.NoDoubleClickUtils;
import com.example.dell.raisingpets.Util.ToastUtil;
import com.example.dell.raisingpets.View.IphoneStyleDialog;
import com.example.dell.raisingpets.Whole.RaisingPetsApplication;
import com.example.dell.raisingpets.Whole.UI.BaseActivity;

import de.greenrobot.event.EventBus;

/**
 * Created by dell on 2016/7/30.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener,View.OnTouchListener{

    private EditText phoneNume_edt;
    private EditText etPassword;
    private Button login_btn;
    private TextView register_tx;
    private Dialog lodingDialog;
    private RelativeLayout show_password_relative;

    private JobManager jobManager;
    private boolean isShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        startService(new Intent(this, SensorService.class));

        phoneNume_edt = (EditText)findViewById(R.id.phoneNum_edt);
        etPassword = (EditText)findViewById(R.id.password_edt);
        login_btn = (Button)findViewById(R.id.logo_btn);
        register_tx = (TextView)findViewById(R.id.register_tx);
        show_password_relative = (RelativeLayout)findViewById(R.id.show_password_relative);
        show_password_relative.setOnClickListener(this);
        show_password_relative.setOnTouchListener(this);

        login_btn.setOnClickListener(this);
        register_tx.setOnClickListener(this);
        register_tx.setOnTouchListener(this);

        lodingDialog = IphoneStyleDialog.loadingDialog(this);

        RaisingPetsApplication.getInstance().addActivity(this);
        setImmerseLayout(findViewById(R.id.login_activity));

        jobManager = RaisingPetsApplication.getInstance().getJobManager();

    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.register_tx:
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setExitTransition(null);
                        getWindow().setEnterTransition(null);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options =ActivityOptions.makeSceneTransitionAnimation(this);
                        startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
                    }else {
                        startActivity(new Intent(this, RegisterActivity.class));
                    }
                }

                break;

            case R.id.logo_btn:
                if (!NoDoubleClickUtils.isDoubleClick()) {
                    String phoneNum = phoneNume_edt.getText().toString();
                    String passWord = etPassword.getText().toString();

                    login(phoneNum,passWord);
                }

                break;

            case R.id.show_password_relative:
                if (!isShow){
                    isShow = true;
                    ((ImageView)findViewById(R.id.show_password_img)).setImageResource(R.mipmap.show_password_img);
                    etPassword.setTransformationMethod(new PasswordTransformationMethod());
                }else {
                    isShow = false;
                    etPassword.setTransformationMethod(null);
                    ((ImageView)findViewById(R.id.show_password_img)).setImageResource(R.mipmap.unshow_password_img);
                }

        }
    }

    private void login(String phoneNum,String passWord){
        if(phoneNum.equals("")){
            ToastUtil.showLongToast("手机号不能为空");
        }else if(passWord.equals("")){
            ToastUtil.showLongToast("密码不能为空");
        }else {
            jobManager.addJobInBackground(new LoginJob(phoneNum,passWord));
            ToastUtil.showLongToast("正在登录,请稍候~~");
        }
    }

    public void onEventMainThread(ProgressEvent event){
        lodingDialog.show();
    }

    public void onEventMainThread(LoginEvent event){
        boolean isSuccess = event.isSuccess();
        String msg = event.getMsg();

        if(isSuccess){
            lodingDialog.dismiss();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(LoginActivity.this, FroestActivity.class));
                    overridePendingTransition(R.anim.hide_rendering_delay, R.anim.fade_out);
                }
            },200);
        }

        ToastUtil.showShortToast(msg);

    }

    @Override
    protected void onPause() {
        super.onPause();

        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.register_tx:
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        ((RelativeLayout)findViewById(R.id.register_relative)).setBackgroundResource(R.color.clickDown);
                        break;
                    case MotionEvent.ACTION_UP:
                        ((RelativeLayout)findViewById(R.id.register_relative)).setBackgroundResource(R.color.clickUp);
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
        }
        return false;
    }
}
