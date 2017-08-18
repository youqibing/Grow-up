package com.example.dell.raisingpets.Module.ModuleFood.Job_and_Event;

import com.example.dell.raisingpets.NetWork.Result.FoodResult;

import java.util.List;

/**
 * Created by root on 16-12-18.
 */

public class FoodEvent {

    public List<FoodResult.FoodList> foodLists;

    public FoodEvent(List<FoodResult.FoodList> foodLists){
        this.foodLists = foodLists;

    }
}
