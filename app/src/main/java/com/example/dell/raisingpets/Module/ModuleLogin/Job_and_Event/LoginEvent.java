package com.example.dell.raisingpets.Module.ModuleLogin.Job_and_Event;

/**
 * Created by root on 16-11-29.
 */

public class LoginEvent {

    boolean success;
    String msg;

    public LoginEvent(boolean success,String msg) {
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
