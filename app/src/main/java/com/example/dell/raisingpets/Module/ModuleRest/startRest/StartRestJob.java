package com.example.dell.raisingpets.Module.ModuleRest.startRest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.dell.raisingpets.Module.ModuleMain.Job_and_Event.ProgressEvent;
import com.example.dell.raisingpets.NetWork.Result.ApiResult;
import com.example.dell.raisingpets.NetWork.Result.StartRestResult;
import com.example.dell.raisingpets.NetWork.RetrofitApi;
import com.example.dell.raisingpets.Util.ToastUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by root on 16-11-30.
 */

public class StartRestJob extends Job {

    private int option;

    private static final int UX = 4;

    public StartRestJob(int option) {
        super(new Params(UX).requireNetwork());

        this.option = option;
    }

    @Override
    public void onAdded() {
        EventBus.getDefault().post(new ProgressEvent());
    }

    @Override
    public void onRun() throws Throwable {

        ApiResult<StartRestResult> startResult = RetrofitApi.startRest(option);

        if(startResult.getCode() != 0){
            EventBus.getDefault().post(new StartRestEvent(false));
            ToastUtil.showLongToast(startResult.getMsg() + "");
        }

        EventBus.getDefault().post(new StartRestEvent(true));
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
