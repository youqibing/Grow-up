package com.example.dell.raisingpets.Module.ModuleFood.Job_and_Event;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.dell.raisingpets.Data.SharedPreferences.UserPreference;
import com.example.dell.raisingpets.Module.ModuleMain.Job_and_Event.ProgressEvent;
import com.example.dell.raisingpets.NetWork.Result.ApiResult;
import com.example.dell.raisingpets.NetWork.Result.PurchaseFoodResult;
import com.example.dell.raisingpets.NetWork.RetrofitApi;

import de.greenrobot.event.EventBus;

/**
 * Created by root on 16-12-19.
 */

public class PurchaseFoodJob extends Job {

    private String identifier;
    private Dialog foodDialog;
    private static final int UX = 4;

    public PurchaseFoodJob(String identifier,Dialog foodDialog) {
        super(new Params(UX).requireNetwork().addTags("purchaseFoodJob"));
        this.identifier = identifier;
        this.foodDialog = foodDialog;
    }

    @Override
    public void onAdded() {
        EventBus.getDefault().post(new ProgressEvent());
    }

    @Override
    public void onRun() throws Throwable {
        ApiResult<PurchaseFoodResult> purchaseFoodResult = RetrofitApi.purchaseFood(identifier);

        UserPreference.storeHP(purchaseFoodResult.getData().getHP());
        UserPreference.storeHungryPoint(purchaseFoodResult.getData().getHungry_point());
        UserPreference.storeMoney(purchaseFoodResult.getData().getMoney());

        String msg = purchaseFoodResult.getMsg();
        if (purchaseFoodResult.getCode() != 0){
            EventBus.getDefault().post(new PurchaseFoodEvent(false,msg,foodDialog));
        }else {
            EventBus.getDefault().post(new PurchaseFoodEvent(true,msg,foodDialog));
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
