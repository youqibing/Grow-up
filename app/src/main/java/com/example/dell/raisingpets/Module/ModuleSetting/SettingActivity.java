package com.example.dell.raisingpets.Module.ModuleSetting;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dell.raisingpets.Data.SharedPreferences.UserPreference;
import com.example.dell.raisingpets.Module.ModuleMain.CityActivity;
import com.example.dell.raisingpets.Module.ModuleMain.FroestActivity;
import com.example.dell.raisingpets.Module.ModuleRest.RestActivity;
import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.Util.NoDoubleClickUtils;
import com.example.dell.raisingpets.View.IphoneStyleDialog;
import com.example.dell.raisingpets.Whole.RaisingPetsApplication;
import com.example.dell.raisingpets.Whole.UI.BaseActivity;

/**
 * Created by root on 16-11-21.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener,View.OnTouchListener{
    private ImageView attention_img;
    private ImageView password_img;
    private ImageView help_img;
    private ImageView back_img;
    private Dialog loading_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setImmerseLayout(findViewById(R.id.activity_setting));
        RaisingPetsApplication.getInstance().addActivity(this);

        initView();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new AttentionFragment()).commit();
    }

    public void initView(){
        attention_img = (ImageView)findViewById(R.id.attention);
        password_img = (ImageView)findViewById(R.id.password);
        help_img = (ImageView)findViewById(R.id.help);
        back_img = (ImageView)findViewById(R.id.back_img);

        attention_img.setImageResource(R.mipmap.attention_click);
        password_img.setImageResource(R.mipmap.password_unclick);
        help_img.setImageResource(R.mipmap.help_unclick);

        attention_img.setOnClickListener(this);
        password_img.setOnClickListener(this);
        help_img.setOnClickListener(this);
        back_img.setOnClickListener(this);
        back_img.setOnTouchListener(this);

        ((TextView)findViewById(R.id.attention_tx)).setTextColor(getResources().getColor(R.color.boldTx));
        ((TextView)findViewById(R.id.password_tx)).setTextColor(getResources().getColor(R.color.dialogColor));
        ((TextView)findViewById(R.id.help_tx)).setTextColor(getResources().getColor(R.color.dialogColor));
        loading_dialog = IphoneStyleDialog.loadingDialog(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.attention:
                attention_img.setImageResource(R.mipmap.attention_click);
                password_img.setImageResource(R.mipmap.password_unclick);
                help_img.setImageResource(R.mipmap.help_unclick);
                ((TextView)findViewById(R.id.attention_tx)).setTextColor(getResources().getColor(R.color.boldTx));
                ((TextView)findViewById(R.id.password_tx)).setTextColor(getResources().getColor(R.color.dialogColor));
                ((TextView)findViewById(R.id.help_tx)).setTextColor(getResources().getColor(R.color.dialogColor));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new AttentionFragment()).commit();
                break;

            case R.id.password:
                password_img.setImageResource(R.mipmap.password_click);
                attention_img.setImageResource(R.mipmap.attention_unclick);
                help_img.setImageResource(R.mipmap.help_unclick);
                ((TextView)findViewById(R.id.attention_tx)).setTextColor(getResources().getColor(R.color.dialogColor));
                ((TextView)findViewById(R.id.password_tx)).setTextColor(getResources().getColor(R.color.boldTx));
                ((TextView)findViewById(R.id.help_tx)).setTextColor(getResources().getColor(R.color.dialogColor));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new PasswordFragment()).commit();
                break;

            case R.id.help:
                help_img.setImageResource(R.mipmap.help_click);
                password_img.setImageResource(R.mipmap.password_unclick);
                attention_img.setImageResource(R.mipmap.attention_unclick);
                ((TextView)findViewById(R.id.attention_tx)).setTextColor(getResources().getColor(R.color.dialogColor));
                ((TextView)findViewById(R.id.password_tx)).setTextColor(getResources().getColor(R.color.dialogColor));
                ((TextView)findViewById(R.id.help_tx)).setTextColor(getResources().getColor(R.color.boldTx));
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new HelpFragment()).commit();
                break;

            case R.id.back_img:
                back();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.back_img:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((RelativeLayout) findViewById(R.id.back_relative)).setBackgroundResource(R.color.clickDown);
                        break;
                    case MotionEvent.ACTION_UP:
                        ((RelativeLayout) findViewById(R.id.back_relative)).setBackgroundResource(R.color.clickUp);
                        break;
                }
                break;
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ){
            if (!NoDoubleClickUtils.isDoubleClick()) {
                finish();
            }
        }
        return false;
    }

    private void back(){

        if(UserPreference.getState() == 1){
            if(UserPreference.getBackground().equals("forest")){
                startActivity(new Intent(SettingActivity.this, FroestActivity.class));
            }else if(UserPreference.getBackground().equals("city")){
                startActivity(new Intent(SettingActivity.this, CityActivity.class));
            }
        }else if(UserPreference.getState() == 2){
            startActivity(new Intent(SettingActivity.this, RestActivity.class));
        }
        finish();

        /*
        //overridePendingTransition(R.anim.hide_rendering_delay, R.anim.fade_out);
        //loading_dialog.show();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                loading_dialog.dismiss();

            }
        },2000);
        */



    }

}
