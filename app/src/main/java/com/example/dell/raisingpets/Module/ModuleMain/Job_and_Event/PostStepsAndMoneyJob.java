package com.example.dell.raisingpets.Module.ModuleMain.Job_and_Event;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.dell.raisingpets.Data.SharedPreferences.UserPreference;
import com.example.dell.raisingpets.NetWork.Result.ApiResult;
import com.example.dell.raisingpets.NetWork.Result.PostStepsAndMoneyResult;
import com.example.dell.raisingpets.NetWork.RetrofitApi;

import de.greenrobot.event.EventBus;

/**
 * Created by root on 16-11-30.
 */

public class PostStepsAndMoneyJob extends Job{

    private int steps;
    private int money;

    private static final int UX = 4;

    public PostStepsAndMoneyJob(int steps,int money) {
        //This job requires network connectivity,
        // and should be persisted in case the application exits before job is completed.
        super(new Params(UX).requireNetwork().persist());

        this.steps = steps;
        this.money = money;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {

        ApiResult<PostStepsAndMoneyResult> postSteps = RetrofitApi.postSteps(steps,money);

        if(postSteps.getCode() != 0){
            EventBus.getDefault().post(new PostStepsAndMoneyEvent(false));
            return;
        }

        Log.e("PostALLSteps",postSteps.getData().getAll_pace_num()+"");
        UserPreference.storeAllPace(postSteps.getData().getAll_pace_num());

        EventBus.getDefault().post(new PostStepsAndMoneyEvent(true));

        Log.e("Result",postSteps+"");
        Log.e("testCode",postSteps.getCode()+"");
        Log.e("testMsg",postSteps.getMsg()+"");
        Log.e("testMoney",postSteps.getData().getMoney()+"");
        Log.e("testTodayPaceNum",postSteps.getData().getToday_pace_num()+"");
        Log.e("testgetAll_pace_num",postSteps.getData().getAll_pace_num()+"");

    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
