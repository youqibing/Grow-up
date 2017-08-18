package com.example.dell.raisingpets.Data.SharedPreferences;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.dell.raisingpets.Type.UserInformation;
import com.example.dell.raisingpets.Whole.RaisingPetsApplication;


/**
 * Created by dell on 2016/8/10.
 * 该类为处理保存和读取各种用户信息的类
 */

public class UserPreference {

    private static final String USER_PREFERENCES = "user";

    private static final String TOKEN = "user_token";

    private static final String NICKNAME = "nickname";

    private static final String HEAD_URL = "head_url";

    private static final String HP = "hp";

    private static final String HUNGRY_POINT = "hungry_point";

    private static final String TODAY_PACE = "today_pace_num";

    private static final String ALL_PACE = "all_pace_num";

    private static final String MONEY = "money";

    private static final String STATE = "state";

    private static final String LIVE_DAYS = "live_days";

    private static final String BACKGROUND = "background";

    private static final String CHARACTER = "character";




    /** USER_PREFERENCES储存用户各种信息*/
    public static SharedPreferences.Editor getUserEditor(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(USER_PREFERENCES,0);
        SharedPreferences.Editor editor = preferences.edit();
        return editor;
    }




    public static void storeToken(String token){    //储存用户登录的TOKEN字段
        SharedPreferences.Editor editor = getUserEditor();
        editor.putString(TOKEN,token);
        editor.apply();
    }
    public static String getToken(){    //读取用户登录的TOKEN字段
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(USER_PREFERENCES,0);
        return preferences.getString(TOKEN,"");
    }




    public static void storeNickName(String nickName){
        SharedPreferences.Editor editor = getUserEditor();
        editor.putString(NICKNAME,nickName);
        editor.apply();
    }
    public static String getNickName(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(USER_PREFERENCES,0);
        return preferences.getString(NICKNAME,"超级厉害的孙悟空");
    }




    public static void storeHeadUrl(String head_url){
        SharedPreferences.Editor editor = getUserEditor();
        editor.putString(HEAD_URL,head_url);
        editor.apply();
    }
    public static String getHeadUrl(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(USER_PREFERENCES,0);
        return preferences.getString(HEAD_URL,"https://pets.hustonline.net/default.png");
    }





    public static void storeHP(int hp){
        SharedPreferences.Editor editor = getUserEditor();
        editor.putInt(HP,hp);
        editor.apply();
    }
    public static int getHP(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(USER_PREFERENCES,0);
        return preferences.getInt(HP,0);
    }




    public static void storeHungryPoint(int hungry_point){
        SharedPreferences.Editor editor = getUserEditor();
        editor.putInt(HUNGRY_POINT,hungry_point);
        editor.apply();
    }
    public static int getHungryPoint(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(USER_PREFERENCES,0);
        return preferences.getInt(HUNGRY_POINT,0);
    }



    public static void storeTodayPaceNum(int today_pace_num){
        Log.e("storeTodayPaceNum",today_pace_num + "");
        SharedPreferences.Editor editor = getUserEditor();
        editor.putInt(TODAY_PACE,today_pace_num);
        editor.apply();
    }
    public static int getTodayPaceNum(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(USER_PREFERENCES,Context.MODE_PRIVATE);
        Log.e("getTodayPaceNum",preferences.getInt(TODAY_PACE,0) + "");
        return preferences.getInt(TODAY_PACE,0);
    }



    public static void storeAllPace(int all_pace_num){
        Log.e("storeAllPaceNum",all_pace_num + "");
        SharedPreferences.Editor editor = getUserEditor();
        editor.putInt(ALL_PACE,all_pace_num);
        editor.apply();
    }
    public static int getAllPace(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(USER_PREFERENCES,0);
        Log.e("getAllPaceNum",preferences.getInt(ALL_PACE,0) + "");
        return preferences.getInt(ALL_PACE,0);
    }



    public static void storeMoney(int money){
        SharedPreferences.Editor editor = getUserEditor();
        editor.putInt(MONEY,money);
        editor.apply();
    }
    public static int getMoney(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(USER_PREFERENCES,0);
        return preferences.getInt(MONEY,0);
    }



    public static void storeState(int state){
        SharedPreferences.Editor editor = getUserEditor();
        editor.putInt(STATE,state);
        editor.apply();
    }
    public static int getState(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(USER_PREFERENCES,0);
        return preferences.getInt(STATE,0);
    }



    public static void storeLiveDays(int live_days){
        SharedPreferences.Editor editor = getUserEditor();
        editor.putInt(LIVE_DAYS,live_days);
        editor.apply();
    }
    public static int getLiveDays(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(USER_PREFERENCES,0);
        return preferences.getInt(LIVE_DAYS,0);
    }



    public static void storeBackground(String background){
        Log.e("storeBackground",background + "");
        SharedPreferences.Editor editor = getUserEditor();
        editor.putString(BACKGROUND,background);
        editor.apply();
    }
    public static String getBackground(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(USER_PREFERENCES,0);
        return preferences.getString(BACKGROUND,"forest");
    }




    public static void storeCharacter(String Character){
        SharedPreferences.Editor editor = getUserEditor();
        editor.putString(CHARACTER,Character);
        editor.apply();
    }
    public static String getCharacter(){
        SharedPreferences preferences = RaisingPetsApplication.getContext().getSharedPreferences(USER_PREFERENCES,0);
        return preferences.getString(CHARACTER,"Alixiya");
    }



    public static void storeUserInfo(UserInformation userInformation){

        Log.e("testNickName",userInformation.getName()+"");
        storeNickName(userInformation.getName());

        Log.e("testHeadUrl",userInformation.getHead()+"");
        storeHeadUrl(userInformation.getHead());

        Log.e("testHP",userInformation.getHP()+"");
        storeHP(userInformation.getHP());

        Log.e("testHungryPoint",userInformation.getHungry_point()+"");
        storeHungryPoint(userInformation.getHungry_point());

        Log.e("testUserInfoTodayPace",userInformation.getToday_pace_num()+"");
        storeTodayPaceNum(userInformation.getToday_pace_num());

        Log.e("testAllPace",userInformation.getAll_pace_num()+"");
        storeAllPace(userInformation.getAll_pace_num());

        Log.e("testMoney",userInformation.getMoney()+"");
        storeMoney(userInformation.getMoney());

        Log.e("testState",userInformation.getState()+"");
        storeState(userInformation.getState());

        Log.e("testLiveDays",userInformation.getLive_days()+"");
        storeLiveDays(userInformation.getLive_days());

        Log.e("testBackground",userInformation.getBackground()+"");
        storeBackground(userInformation.getBackground());

        Log.e("testCharacter",userInformation.getCharacter()+"");
        storeCharacter(userInformation.getCharacter());
    }

}
