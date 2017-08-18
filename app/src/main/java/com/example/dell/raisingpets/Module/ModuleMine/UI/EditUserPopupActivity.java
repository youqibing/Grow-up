package com.example.dell.raisingpets.Module.ModuleMine.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.birbit.android.jobqueue.JobManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.dell.raisingpets.Data.SharedPreferences.UserPreference;
import com.example.dell.raisingpets.Module.ModuleMine.Job_and_Event.NickNameEvent;
import com.example.dell.raisingpets.Module.ModuleMine.Job_and_Event.NickNameJob;
import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.Util.ToastUtil;
import com.example.dell.raisingpets.Whole.RaisingPetsApplication;

import de.greenrobot.event.EventBus;

/**
 * Created by root on 16-12-4.
 */

public class EditUserPopupActivity extends Activity implements View.OnClickListener{

    private RelativeLayout change_relative;
    private ImageView avatarImage;
    private EditText nickname;
    private ImageView confirm_img;

    private JobManager jobManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edituser);

        Glide.with(this)
                .load(UserPreference.getHeadUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop().into((ImageView)findViewById(R.id.avatar));


        nickname = (EditText)findViewById(R.id.nickname_edt);
        confirm_img = (ImageView)findViewById(R.id.confirm_img);
        confirm_img.setOnClickListener(this);

        EventBus.getDefault().register(this);
        jobManager = RaisingPetsApplication.getInstance().getJobManager();

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.confirm_img:
                String nickName = nickname.getText().toString();

                //jobManager.addJobInBackground(new NickNameJob(nickName));
        }

    }


    public void onEvent(NickNameEvent event){
        if(event.isSucess()){
            ToastUtil.showShortToast("昵称修改成功!");

            UserPreference.storeNickName(event.nickName());
            Intent intent = new Intent();
            intent.putExtra("newUrl", UserPreference.getHeadUrl());
            intent.putExtra("nickName", event.nickName());

            this.setResult(RESULT_OK, intent);

            this.finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);


    }
}
