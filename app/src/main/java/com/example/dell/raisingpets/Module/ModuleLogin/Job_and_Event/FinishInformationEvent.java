package com.example.dell.raisingpets.Module.ModuleLogin.Job_and_Event;

/**
 * Created by dell on 2016/8/23.
 */

public class FinishInformationEvent {
    public boolean isSucessed;

    public FinishInformationEvent(boolean isSucessed){
        this.isSucessed = isSucessed;
    }

    public boolean isSucess(){
        return isSucessed;
    }

    @Override
    public String toString() {
        return "FinishInformationEvent{" +
                "success=" + isSucessed +
                "}";
    }

}
