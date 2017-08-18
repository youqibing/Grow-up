package com.example.dell.raisingpets.Module.ModuleSetting.Job_and_Event;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.dell.raisingpets.Module.ModuleLogin.Job_and_Event.LoginEvent;
import com.example.dell.raisingpets.NetWork.Result.ApiResult;
import com.example.dell.raisingpets.NetWork.Result.LoginResult;
import com.example.dell.raisingpets.NetWork.Result.PassWordResult;
import com.example.dell.raisingpets.NetWork.RetrofitApi;

import de.greenrobot.event.EventBus;

/**
 * Created by root on 16-12-20.
 */

public class PassWordJob extends Job {

    private String oldPassWord;
    private String newPassWord;

    private static final int UX = 4;

    public PassWordJob(String oldPassWord, String newPassWord) {
        super(new Params(UX).requireNetwork());

        this.oldPassWord = oldPassWord;
        this.newPassWord = newPassWord;
    }


    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        ApiResult<PassWordResult> passWordResult = RetrofitApi.editPassWord(oldPassWord,newPassWord);

        if(passWordResult.getCode() != 0){
            EventBus.getDefault().post(new PassWordEvent(false,passWordResult.getMsg()));
        }else {
            EventBus.getDefault().post(new PassWordEvent(true,passWordResult.getMsg()));
        }
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
