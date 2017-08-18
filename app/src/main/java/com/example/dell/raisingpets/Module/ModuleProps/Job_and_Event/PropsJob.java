package com.example.dell.raisingpets.Module.ModuleProps.Job_and_Event;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.dell.raisingpets.NetWork.Result.PropsResult;
import com.example.dell.raisingpets.NetWork.Result.ApiResult;
import com.example.dell.raisingpets.NetWork.RetrofitApi;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by root on 16-11-20.
 */

public class PropsJob extends Job {

    private static final int UX = 4;

    public PropsJob() {
        super(new Params(UX).requireNetwork());
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {

        ApiResult<List<PropsResult.PropsList>> propsListResult = RetrofitApi.propsListResult();

        EventBus.getDefault().post(new PropsEvent(propsListResult.getData()));
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
