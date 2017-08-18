package com.example.dell.raisingpets.Module.ModuleMine.Job_and_Event;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.dell.raisingpets.Module.ModuleMain.Job_and_Event.ProgressEvent;
import com.example.dell.raisingpets.NetWork.Result.ApiResult;
import com.example.dell.raisingpets.NetWork.Result.NickNameResult;
import com.example.dell.raisingpets.NetWork.RetrofitApi;

import de.greenrobot.event.EventBus;

/**
 * Created by root on 16-12-4.
 */

public class NickNameJob extends Job {

    private static final int UX = 4;
    private String nickName;
    private TextView userNickName;

    public NickNameJob(String nickName,TextView userNickName) {
        super(new Params(UX).requireNetwork());
        this.nickName = nickName;
        this.userNickName = userNickName;
    }

    @Override
    public void onAdded() {
        EventBus.getDefault().post(new ProgressEvent());
    }

    @Override
    public void onRun() throws Throwable {
        ApiResult<NickNameResult> NickNameResult = RetrofitApi.nicknamechange(nickName);

        if(NickNameResult.getCode() != 0 ){
            EventBus.getDefault().post(new NickNameEvent(false,null,userNickName));
            return;
        }
        EventBus.getDefault().post(new NickNameEvent(true,nickName,userNickName));
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {

    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
