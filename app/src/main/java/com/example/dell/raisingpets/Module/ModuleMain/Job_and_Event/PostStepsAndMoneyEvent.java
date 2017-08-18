package com.example.dell.raisingpets.Module.ModuleMain.Job_and_Event;

/**
 * Created by root on 16-11-30.
 */

public class PostStepsAndMoneyEvent {

    boolean success;

    public PostStepsAndMoneyEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

}
