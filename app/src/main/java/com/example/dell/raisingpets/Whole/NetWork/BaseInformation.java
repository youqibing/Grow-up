package com.example.dell.raisingpets.Whole.NetWork;

import com.google.gson.Gson;

/**
 * Created by dell on 2016/8/15.
 */

public class BaseInformation {
    protected static final Gson GSON = new Gson();

    public String toJson(){
        return GSON.toJson(this);
    }
}
