package com.example.dell.raisingpets.Module.ModuleFood.Job_and_Event;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.dell.raisingpets.NetWork.Result.ApiResult;
import com.example.dell.raisingpets.NetWork.Result.FoodResult;
import com.example.dell.raisingpets.NetWork.RetrofitApi;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by root on 16-12-18.
 */

public class FoodJob extends Job {

    private static final int UX = 4;

    public FoodJob() {
        super(new Params(UX).requireNetwork());
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {

        ApiResult<List<FoodResult.FoodList>> foodResult = RetrofitApi.foodLists();

        EventBus.getDefault().post(new FoodEvent(foodResult.getData()));
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
