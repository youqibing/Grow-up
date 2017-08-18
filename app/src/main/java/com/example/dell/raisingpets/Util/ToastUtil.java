package com.example.dell.raisingpets.Util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.example.dell.raisingpets.Whole.RaisingPetsApplication;



/**
 * Created by dell on 2016/7/30.
 */

public class ToastUtil {
    private static Toast toast = null;

    private static Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if(toast != null){
                toast.cancel();
            }
            String text = (String)msg.obj;
            int duration = msg.arg2;

            toast = Toast.makeText(RaisingPetsApplication.getContext(),text,duration);
            toast.show();
        }
    };

    public static void showToast(String text,int duration){
        handler.sendMessage(handler.obtainMessage(0,0,duration,text));
    }

    public static void showToast(int textResId, int duration) {
        showToast(RaisingPetsApplication.getContext().getResources().getString(textResId), duration);
    }

    public static void showShortToast(String text){
        showToast(text,Toast.LENGTH_SHORT);
    }

    public static void showShortToast(int textResId) {
        showToast(textResId, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(String text) {
        showToast(text, Toast.LENGTH_LONG);
    }

    public static void showLongToast(int textResId) {
        showToast(textResId, Toast.LENGTH_LONG);
    }



}
