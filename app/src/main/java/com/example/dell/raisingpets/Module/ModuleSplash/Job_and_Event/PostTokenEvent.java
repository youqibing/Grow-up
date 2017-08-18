package com.example.dell.raisingpets.Module.ModuleSplash.Job_and_Event;

/**
 * Created by root on 16-11-29.
 */

public class PostTokenEvent {

    boolean success;

    public PostTokenEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
