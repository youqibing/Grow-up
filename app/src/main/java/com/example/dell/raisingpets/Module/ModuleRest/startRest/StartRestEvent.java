package com.example.dell.raisingpets.Module.ModuleRest.startRest;

/**
 * Created by root on 16-11-30.
 */

public class StartRestEvent {

    boolean success;

    public StartRestEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

}
