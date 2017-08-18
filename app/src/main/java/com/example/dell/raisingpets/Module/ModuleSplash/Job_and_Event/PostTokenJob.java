package com.example.dell.raisingpets.Module.ModuleSplash.Job_and_Event;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.dell.raisingpets.Data.SharedPreferences.UserPreference;
import com.example.dell.raisingpets.NetWork.Result.ApiResult;
import com.example.dell.raisingpets.NetWork.Result.PostTokenResult;
import com.example.dell.raisingpets.NetWork.RetrofitApi;

import de.greenrobot.event.EventBus;

/**
 * Created by root on 16-11-29.
 */

public class PostTokenJob extends Job {
    private String token;

    private static final int UX = 4;

    public PostTokenJob(String token) {
        super(new Params(UX).requireNetwork().persist());

        this.token = token;
    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRun() throws Throwable {

        ApiResult<PostTokenResult> postToken = RetrofitApi.postToken(token);

        if(postToken.getCode() != 0){
            EventBus.getDefault().post(new PostTokenEvent(false));
            return;
        }

        UserPreference.storeToken(postToken.getData().getToken());

        UserPreference.storeNickName(postToken.getData().getUser().getName());
        UserPreference.storeHeadUrl(postToken.getData().getUser().getHead());
        UserPreference.storeHP(postToken.getData().getUser().getHP());
        UserPreference.storeHungryPoint(postToken.getData().getUser().getHungry_point());
        UserPreference.storeState(postToken.getData().getUser().getState());
        UserPreference.storeLiveDays(postToken.getData().getUser().getLive_days());
        UserPreference.storeBackground(postToken.getData().getUser().getBackground());
        UserPreference.storeCharacter(postToken.getData().getUser().getCharacter());
        //UserPreference.storeTodayPaceNum(postToken.getData().getUser().getToday_pace_num());
        //UserPreference.storeAllPace(postToken.getData().getUser().getAll_pace_num());
        //UserPreference.storeMoney(postToken.getData().getUser().getMoney());
        //每日的步数,总步数以及总的金钱数是要在上传Token之后刷新的,所以这个时候传回来的这三个数据不能用,否则要出问题了.

        EventBus.getDefault().post(new PostTokenEvent(true));

        Log.e("testTokenJobResult",postToken+"");
        Log.e("testTokenJobCode",postToken.getCode()+"");
        Log.e("testTokenJobMsg",postToken.getMsg()+"");
        Log.e("testTokenJobData",postToken.getData()+"");
        Log.e("testTokenJobToken",postToken.getData().getToken()+"");
        Log.e("testTokenJobUser",postToken.getData().getUser()+"");
        Log.e("testTokenJobHP",postToken.getData().getUser().getHP()+"");
        Log.e("TokenJobHungry_point",postToken.getData().getUser().getHungry_point()+"");
        Log.e("testTokenJobBackroud",postToken.getData().getUser().getBackground()+"");
        Log.e("testTokenJobTodayPace",postToken.getData().getUser().getToday_pace_num()+"");
        Log.e("testTokenJobAllPace",postToken.getData().getUser().getAll_pace_num()+"");

    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
