package com.example.dell.raisingpets.Module.ModuleDeath.Job_and_Event;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.dell.raisingpets.Data.SharedPreferences.UserPreference;
import com.example.dell.raisingpets.Module.ModuleLogin.Job_and_Event.RegisterEvent;
import com.example.dell.raisingpets.NetWork.Result.ApiResult;
import com.example.dell.raisingpets.NetWork.Result.PurchaseFoodResult;
import com.example.dell.raisingpets.NetWork.Result.ReliveResult;
import com.example.dell.raisingpets.NetWork.RetrofitApi;

import de.greenrobot.event.EventBus;

/**
 * Created by root on 16-12-29.
 */

public class ReliveJob extends Job {

    private int option;
    private static final int UX = 4;

    public ReliveJob(int option) {
        super(new Params(UX).requireNetwork());
        this.option = option;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {
        ApiResult<ReliveResult> reliveResult = RetrofitApi.relive(option);
        String msg = reliveResult.getMsg();
        if(reliveResult.getCode() != 0){
            EventBus.getDefault().post(new ReliveEvent(false,msg));
            return;
        }

        UserPreference.storeUserInfo(reliveResult.getData().getUser());

        EventBus.getDefault().post(new ReliveEvent(true,msg));

    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
