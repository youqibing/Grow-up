package com.example.dell.raisingpets.Module.ModuleFood.Job_and_Event;

import android.app.Dialog;

/**
 * Created by root on 16-12-19.
 */

public class PurchaseFoodEvent {
    public String msg;
    public Dialog foodDialog;
    public boolean success;


    public PurchaseFoodEvent(boolean success,String msg,Dialog foodDialog) {
        this.foodDialog = foodDialog;
        this.msg = msg;
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMsg(){
        return msg;
    }

    public Dialog getFoodDialog(){
        return foodDialog;
    }
}
