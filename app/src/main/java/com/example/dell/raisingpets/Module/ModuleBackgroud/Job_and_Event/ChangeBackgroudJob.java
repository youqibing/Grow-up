package com.example.dell.raisingpets.Module.ModuleBackgroud.Job_and_Event;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.dell.raisingpets.Module.ModuleMain.Job_and_Event.ProgressEvent;
import com.example.dell.raisingpets.NetWork.Result.ApiResult;
import com.example.dell.raisingpets.NetWork.Result.ChangeBackgroudResult;
import com.example.dell.raisingpets.NetWork.RetrofitApi;

import de.greenrobot.event.EventBus;


/**
 * Created by root on 16-12-8.
 */

public class ChangeBackgroudJob extends Job {

    private String identifier;
    private Dialog backgroudDialog;
    private static final int UX = 4;

    public ChangeBackgroudJob(String identifier,Dialog backgroudDialog) {
        super(new Params(UX).requireNetwork());
        this.identifier = identifier;
        this.backgroudDialog = backgroudDialog;
    }

    @Override
    public void onAdded() {
        EventBus.getDefault().post(new ProgressEvent());
    }

    @Override
    public void onRun() throws Throwable {
        ApiResult<ChangeBackgroudResult> changeBackgroudResult = RetrofitApi.ChangeBackgroud(identifier);
        String msg = changeBackgroudResult.getMsg();

        Log.e("BackgroudResult",changeBackgroudResult+"");
        Log.e("BackgroudResultMsg",changeBackgroudResult.getMsg()+"");

        if(changeBackgroudResult.getCode() != 0){
            EventBus.getDefault().post(new ChangeBackgroudEvent(false,msg,identifier,backgroudDialog));
        }

        EventBus.getDefault().post(new ChangeBackgroudEvent(true,msg,identifier,backgroudDialog));
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
