package com.example.dell.raisingpets.Module.ModuleMain.Job_and_Event;

/**
 * Created by root on 16-11-18.
 */

public class FinishChangeEvent {
    public Boolean isFinished;
    public String identifier;

    public FinishChangeEvent(Boolean isFinished, String identifier){
        this.isFinished = isFinished;
        this.identifier = identifier;
    }

    public boolean getIsFinished(){
        return isFinished;
    }

    public String getIdentifier() {
        return identifier;
    }
}
