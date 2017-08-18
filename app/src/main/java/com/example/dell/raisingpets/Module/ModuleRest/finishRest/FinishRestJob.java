package com.example.dell.raisingpets.Module.ModuleRest.finishRest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.dell.raisingpets.Module.ModuleMain.Job_and_Event.ProgressEvent;
import com.example.dell.raisingpets.NetWork.Result.ApiResult;
import com.example.dell.raisingpets.NetWork.Result.FinishRestResult;
import com.example.dell.raisingpets.NetWork.RetrofitApi;
import com.example.dell.raisingpets.Util.ToastUtil;

import de.greenrobot.event.EventBus;

/**
 * Created by root on 16-11-30.
 */

public class FinishRestJob extends Job {
    private int option;

    private static final int UX = 4;

    public FinishRestJob(int option) {
        super(new Params(UX).requireNetwork());

        this.option = option;
    }

    @Override
    public void onAdded() {
        EventBus.getDefault().post(new ProgressEvent());
    }

    @Override
    public void onRun() throws Throwable {

        ApiResult<FinishRestResult> finishRestResult = RetrofitApi.finishRest(option);

        if(finishRestResult.getCode() != 0){
            EventBus.getDefault().post(new FinishRestEvent(false,0));
            ToastUtil.showLongToast(finishRestResult.getMsg() + "");
        }

        int restTime = finishRestResult.getData().getRestTime();
        Log.e("testRestTime",restTime+"");

        EventBus.getDefault().post(new FinishRestEvent(true,restTime));
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
