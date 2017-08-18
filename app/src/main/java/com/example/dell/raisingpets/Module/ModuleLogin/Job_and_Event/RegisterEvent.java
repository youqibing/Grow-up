package com.example.dell.raisingpets.Module.ModuleLogin.Job_and_Event;

import android.util.Log;

/**
 * Created by dell on 2016/8/16.
 */

public class RegisterEvent {
    boolean success;

    public RegisterEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

}

