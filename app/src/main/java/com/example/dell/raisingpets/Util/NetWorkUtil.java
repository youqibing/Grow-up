package com.example.dell.raisingpets.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.dell.raisingpets.Whole.RaisingPetsApplication;


/**
 * Created by dell on 2016/7/30.
 */

public class NetWorkUtil {
    public static boolean isNetWorkAvailable(){
        boolean status = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) RaisingPetsApplication.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(0);
        if(networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED){
            status = false;
        }else{
            networkInfo = connectivityManager.getNetworkInfo(1);
            if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED)
            status = true;
        }
        return status;
    }

}
