package com.example.dell.raisingpets.Module.ModuleSetting.Job_and_Event;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.dell.raisingpets.Module.ModuleLogin.Job_and_Event.LoginEvent;
import com.example.dell.raisingpets.Module.ModuleMain.Job_and_Event.ProgressEvent;
import com.example.dell.raisingpets.NetWork.Result.ApiResult;
import com.example.dell.raisingpets.NetWork.Result.LoginResult;
import com.example.dell.raisingpets.NetWork.Result.SendWordsReult;
import com.example.dell.raisingpets.NetWork.RetrofitApi;

import de.greenrobot.event.EventBus;

/**
 * Created by root on 16-12-19.
 */

public class SendWordsJob extends Job {

    private String contacts;
    private String content;

    private static final int UX = 4;

    public SendWordsJob(String contacts,String content) {
        super(new Params(UX).requireNetwork());

        this.contacts = contacts;
        this.content = content;
    }


    @Override
    public void onAdded() {
        EventBus.getDefault().post(new ProgressEvent());
    }

    @Override
    public void onRun() throws Throwable {

        ApiResult<SendWordsReult> sendWordResult = RetrofitApi.sendWord(contacts,content);

        if(sendWordResult.getCode() != 0){
            EventBus.getDefault().post(new SendWordEvent(false,sendWordResult.getMsg()));
        }else {
            EventBus.getDefault().post(new SendWordEvent(true,sendWordResult.getMsg()));
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
