package com.example.dell.raisingpets.Module.ModuleBackgroud.Job_and_Event;

import android.app.Dialog;

/**
 * Created by root on 16-12-8.
 */

public class ChangeBackgroudEvent {
    public Boolean isFinished;
    public String msg;
    public String identifier;
    public Dialog backgroudDialog;

    public ChangeBackgroudEvent(Boolean isFinished,String msg,String identifier,Dialog backgroudDialog){
        this.isFinished = isFinished;
        this.msg = msg;
        this.identifier = identifier;
        this.backgroudDialog = backgroudDialog;
    }

    public boolean isSuccess(){
        return isFinished;
    }

    public String getMsg(){
        return msg;
    }

    public String getIdentifier(){
        return identifier;
    }

    public Dialog getBackgroudDialog(){
        return backgroudDialog;
    }
}
