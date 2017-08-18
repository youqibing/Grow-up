package com.example.dell.raisingpets.Module.ModuleRest.finishRest;

/**
 * Created by root on 16-11-30.
 */

public class FinishRestEvent {
    boolean success;
    int restTime;

    public FinishRestEvent(boolean success,int restTime) {
        this.success = success;
        this.restTime = restTime;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getRestTime(){
        return restTime;
    }
}
