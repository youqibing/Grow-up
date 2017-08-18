package com.example.dell.raisingpets.Data.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.dell.raisingpets.Whole.RaisingPetsApplication;

/**
 * Created by dell on 2016/8/14.
 * 该类为保存一些工具类信息的类,如时间倒计时相关
 */

public class ToolPerference {
    private static final String TOOL_SHAREDPERFENCE = "tool_sharedPerfence";

    private static final String APP_PREFERENCES = "app";
    private static final String APP_FIRST_USE = "app_first_use";

    private static final String PEDOMETER = "pedometer";
    private static final String PAUSECOUNT = "pauseCount";

    private static final String SHUTDOWNOFFSET = "shutdownoffset";

    private static final String BACKGROUD = "backgroud";
    private static final String CHARACTER = "character";

    private static final String CITYBACKGROUD = "city_backgroud";
    private static final String FROESTBACKGROUD = "froest_backgroud";

    private static final String MONEY = "money";

    private static final String FOOD = "food";

    private static final String ISBACKENABLE = "isback_enable";

    private static final String ISNOTIFICATION = "isNotification";


    public static SharedPreferences getPedometerpreferences(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(PEDOMETER,Context.MODE_PRIVATE);
        return preferences;
    }
    public static void putSteps(int steps){
        Log.e("testPut","PutPutPutPutPutPutPut");
        getPedometerpreferences().edit().putInt(PAUSECOUNT,steps).apply();
    }
    public static int getSteps(int steps){
        return getPedometerpreferences().getInt(PAUSECOUNT,steps);
    }
    public static void removePauseCount(){
        Log.e("testRemove","RemoveRemoveRemoveRemove");
        getPedometerpreferences().edit().remove(PAUSECOUNT).apply();
    }





    public static void storeShutDownOffset(int shutdownOffset){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(SHUTDOWNOFFSET,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("shutdown",shutdownOffset).apply();
    }
    public static int getShutDownOffset(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(SHUTDOWNOFFSET,Context.MODE_PRIVATE);
        return preferences.getInt("shutdown",0);
    }




    public static void putBeginTime(Context context,Long time){
        SharedPreferences preferences = context.getSharedPreferences(TOOL_SHAREDPERFENCE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("beginTime",time).apply();
    }
    public static Long getBeginTime(Context context){
        SharedPreferences preferences = context.getSharedPreferences(TOOL_SHAREDPERFENCE,Context.MODE_PRIVATE);
        return preferences.getLong("beginTime",0);
    }




    /** APP_PREFERENCES储存是否第一次打开APP*/
    public static void storeAppFirstUse(boolean firstUse){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(APP_PREFERENCES,0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(APP_FIRST_USE,firstUse);
        editor.apply();
    }
    public static boolean isAPPFirstUse(){  //读取是否第一次打开APP
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(APP_PREFERENCES,0);
        return preferences.getBoolean(APP_FIRST_USE,true);
    }





    /**是否刚刚换到城市背景**/
    public static void storeCityBackgroudFirstUse(boolean firstUse){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(CITYBACKGROUD,0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("city",firstUse);
        editor.apply();
    }
    public static boolean isCityBackgroudFirstUse(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(CITYBACKGROUD,0);
        return preferences.getBoolean("city",true);
    }





    /**是否刚刚换到森林背景**/
    public static void storeFroestBackgroudFirstUse(boolean firstUse){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(FROESTBACKGROUD,0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("froest",firstUse);
        editor.apply();
    }
    public static boolean isFroestBackgroudFirstUse(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(FROESTBACKGROUD,0);
        return preferences.getBoolean("froest",true);
    }






    public static void storeIsBackEnable(boolean is){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(ISBACKENABLE,0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("Is",is);
        editor.apply();
    }
    public static Boolean getIsBackEnable(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(ISBACKENABLE,0);
        return preferences.getBoolean("Is",false);
    }





    public static void storeIsNotification(boolean isNotification){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(ISNOTIFICATION,0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isNotification",isNotification);
        editor.apply();
    }
    public static Boolean getIsNotification(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(ISNOTIFICATION,0);
        return preferences.getBoolean("isNotification",true);
    }






    public static void storeMoneyOffset(int todayMoney){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(MONEY,0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("moneyOffset",todayMoney);
        editor.apply();
    }
    public static int getMoneyOffset(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(MONEY,0);
        return preferences.getInt("moneyOffset",0);
    }








    public static SharedPreferences.Editor getFoodEditor(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(FOOD,0);
        SharedPreferences.Editor editor = preferences.edit();
        return editor;
    }


    public static void storeFoodIdentifier(String identifier){
        SharedPreferences.Editor editor = getFoodEditor();
        editor.putString("identifier",identifier).apply();
    }
    public static String getFoodIdentifier(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(FOOD,Context.MODE_PRIVATE);
        return preferences.getString("identifier","");
    }


    /*
    public static void storeFoodHP(int HP){
        SharedPreferences.Editor editor = getFoodEditor();
        editor.putInt("HP",HP).apply();
    }
    public static int getFoodHP(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(FOOD,Context.MODE_PRIVATE);
        return preferences.getInt("HP",0);
    }


    public static void storeFoodHungry_point(int hungryPoint){
        SharedPreferences.Editor editor = getFoodEditor();
        editor.putInt("hungryPoint",hungryPoint).apply();
    }
    public static int getFoodHungry_point(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(FOOD,Context.MODE_PRIVATE);
        return preferences.getInt("hungryPoint",0);
    }


    public static void storeFoodPrice(int price){
        SharedPreferences.Editor editor = getFoodEditor();
        editor.putInt("price",price).apply();
    }
    public static int getFoodPrice(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(FOOD,Context.MODE_PRIVATE);
        return preferences.getInt("price",0);
    }
    */

}
