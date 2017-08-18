package com.example.dell.raisingpets.Module.ModuleDeath.Job_and_Event;

/**
 * Created by root on 16-12-29.
 */

public class ReliveEvent {
    boolean success;
    String msg;

    public ReliveEvent(boolean success,String msg) {
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMsg(){
        return msg;
    }
}
