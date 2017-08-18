package com.example.dell.raisingpets.Module.ModuleBackgroud.Job_and_Event;

import com.example.dell.raisingpets.NetWork.Result.BackgroudResult;

import java.util.List;

/**
 * Created by root on 16-11-17.
 */

public class BackgroudEvent {

    public List<BackgroudResult.BackgroudList> backgroudLists;

    public BackgroudEvent(List<BackgroudResult.BackgroudList> backgroudLists){
        this.backgroudLists = backgroudLists;
    }
}
