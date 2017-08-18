package com.example.dell.raisingpets.Module.ModuleProps.Job_and_Event;

import com.example.dell.raisingpets.NetWork.Result.PropsResult;

import java.util.List;

/**
 * Created by root on 16-11-20.
 */

public class PropsEvent {

    public List<PropsResult.PropsList> propsLists;

    public PropsEvent(List<PropsResult.PropsList> propsLists){
        this.propsLists = propsLists;
    }
}
