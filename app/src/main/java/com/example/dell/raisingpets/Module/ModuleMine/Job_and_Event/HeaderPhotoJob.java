package com.example.dell.raisingpets.Module.ModuleMine.Job_and_Event;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.dell.raisingpets.Data.SharedPreferences.UserPreference;
import com.example.dell.raisingpets.Module.ModuleMain.Job_and_Event.ProgressEvent;
import com.example.dell.raisingpets.NetWork.Result.ApiResult;
import com.example.dell.raisingpets.NetWork.Result.HeaderResult;
import com.example.dell.raisingpets.NetWork.RetrofitApi;

import de.greenrobot.event.EventBus;

/**
 * Created by dell on 2016/8/21.
 */

public class HeaderPhotoJob extends Job {

    private String uri;

    private static final int UX = 4;

    public HeaderPhotoJob(String uri) {
        super(new Params(UX).requireNetwork().persist());

        this.uri = uri;
    }

    @Override
    public void onAdded() {
        EventBus.getDefault().post(new ProgressEvent());
    }

    @Override
    public void onRun() throws Throwable {
        ApiResult<HeaderResult> headerResult = RetrofitApi.headerchange(uri);

        if(headerResult.getCode() != 0 ){
            EventBus.getDefault().post(new HeaderPhotoEvent(false));
            return;
        }

        UserPreference.storeHeadUrl(headerResult.getData().getUrl());
        EventBus.getDefault().post(new HeaderPhotoEvent(true));
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
