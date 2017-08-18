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
import com.example.dell.raisingpets.NetWork.Result.LoginResult;
import com.example.dell.raisingpets.NetWork.RetrofitApi;

import de.greenrobot.event.EventBus;

/**
 * Created by root on 16-11-29.
 */

public class LoginJob extends Job {

    private String phoneNum;
    private String passWord;

    private static final int UX = 4;

    public LoginJob(String phoneNum,String passWord) {
        super(new Params(UX).requireNetwork());

        this.passWord = passWord;
        this.phoneNum = phoneNum;
    }

    @Override
    public void onAdded() {
        EventBus.getDefault().post(new ProgressEvent());
    }

    @Override
    public void onRun() throws Throwable {

        ApiResult<LoginResult> loginResult = RetrofitApi.login(phoneNum,passWord);
        String msg = loginResult.getMsg();

        Log.e("Result",loginResult+"");
        Log.e("testCode",loginResult.getCode()+"");
        Log.e("testMsg",loginResult.getMsg()+"");
        Log.e("testData",loginResult.getData()+"");
        Log.e("testToken",loginResult.getData().getToken()+"");
        Log.e("testUser",loginResult.getData().getUser()+"");

        if(loginResult.getCode() != 0){
            EventBus.getDefault().post(new LoginEvent(false,msg));
        }

        UserPreference.storeToken(loginResult.getData().getToken());
        UserPreference.storeUserInfo(loginResult.getData().getUser());

        EventBus.getDefault().post(new LoginEvent(true,msg));

    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
