package com.example.dell.raisingpets.Module.ModuleLogin.Job_and_Event;

import android.content.Context;

import cn.smssdk.SMSSDK;

/**
 * Created by dell on 2016/7/30.
 */

public class SMSVerificationJob {
    private static String[] country;
    private static String countryCode;

    private static String APPKEY = "15629af28775c";
    private static String APPSECRET = "0bd643fdcb62bb03ec58f419f4953672";

    private static final String DEFAULT_COUNTRY_ID = "42";  //默认使用大中华区编号

    public static void initSDK(Context context){
        SMSSDK.initSDK(context, APPKEY, APPSECRET);//初始化SDK

        country=SMSSDK.getCountry(DEFAULT_COUNTRY_ID);//默认使用大中华区编号
        countryCode = country[1];
    }


    public static void getVerificationCode(String phone){
        SMSSDK.getVerificationCode("+" +countryCode , phone.trim());
    }

    public static void submitVerificationCode(String phoneNum,String VerificationCode){
        SMSSDK.submitVerificationCode("+" + countryCode, phoneNum.trim(),VerificationCode);
    }

}
