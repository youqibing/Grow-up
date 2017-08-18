package com.example.dell.raisingpets.Module.ModuleBackgroud.Job_and_Event;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.dell.raisingpets.NetWork.Result.BackgroudResult;
import com.example.dell.raisingpets.NetWork.Result.ApiResult;
import com.example.dell.raisingpets.NetWork.RetrofitApi;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by dell on 2016/10/23.
 */

public class BackgroudJob extends Job {

    private static final int UX = 4;

    public BackgroudJob() {
        super(new Params(UX).requireNetwork());
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {

        ApiResult<List<BackgroudResult.BackgroudList>> backgroudResult = RetrofitApi.backgroudListResult();

        Log.e("backgroud",backgroudResult+"");
        Log.e("backgroudData",backgroudResult.getData()+"");

        Log.e("backgroudPrice1",backgroudResult.getData().get(0).getPrice()+"");
        Log.e("backgroudUnlock1",backgroudResult.getData().get(0).isLocked()+"");
        Log.e("backgroudIdentifier1",backgroudResult.getData().get(0).getIdentifier()+"");

        Log.e("backgroudPrice2",backgroudResult.getData().get(1).getPrice()+"");
        Log.e("backgroudUnlock2",backgroudResult.getData().get(1).isLocked()+"");
        Log.e("backgroudIdentifier2",backgroudResult.getData().get(1).getIdentifier()+"");


        EventBus.getDefault().post(new BackgroudEvent(backgroudResult.getData()));
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {


    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
