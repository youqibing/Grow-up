package com.example.dell.raisingpets.Module.ModuleLogin.Job_and_Event;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.dell.raisingpets.Data.SharedPreferences.UserPreference;
import com.example.dell.raisingpets.Module.ModuleMain.Job_and_Event.ProgressEvent;
import com.example.dell.raisingpets.NetWork.Result.ApiResult;
import com.example.dell.raisingpets.NetWork.Result.RegisterResult;
import com.example.dell.raisingpets.NetWork.RetrofitApi;

import de.greenrobot.event.EventBus;

/**
 * Created by dell on 2016/8/16.
 */

public class RegisterJob extends Job {
    String phoneNum;
    String passWord;

    private static final int UX = 4;

    public RegisterJob(String phoneNum,String passWord){
        super(new Params(UX).requireNetwork());

        this.phoneNum = phoneNum;
        this.passWord = passWord;
    }

    @Override
    public void onAdded() {
        EventBus.getDefault().post(new ProgressEvent());
    }

    @Override
    public void onRun() throws Throwable {

        ApiResult<RegisterResult> result = RetrofitApi.register(phoneNum,passWord);

        if(result.getCode() != 0){
            EventBus.getDefault().post(new RegisterEvent(false));
            return;
        }
        UserPreference.storeToken(result.getData().getToken());
        UserPreference.storeUserInfo(result.getData().getUser());

        EventBus.getDefault().post(new RegisterEvent(true));

        Log.e("RegisterJobCode",result.getCode()+"");
        Log.e("RegisterJobMsg",result.getMsg()+"");
        Log.e("RegisterJobData",result.getData()+"");
        Log.e("RegisterJobToken",result.getData().getToken()+"");
        Log.e("RegisterJobUser",result.getData().getUser()+"");
        Log.e("RegisterJobBackground",result.getData().getUser().getBackground()+"");
        Log.e("RegisterJobCharacter",result.getData().getUser().getCharacter()+"");
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }

}
