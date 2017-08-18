package com.example.dell.raisingpets.Module.ModuleSetting.Job_and_Event;

/**
 * Created by root on 16-12-19.
 */

public class SendWordEvent {
    public boolean success = false;
    public String msg;

    public SendWordEvent(boolean success,String msg){
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess(){
        return success;
    }

    public String getMsg(){
        return msg;
    }

}
