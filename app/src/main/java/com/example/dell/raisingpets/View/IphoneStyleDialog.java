package com.example.dell.raisingpets.View;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.Util.TextUtils;

/**
 * Created by root on 16-11-29.
 *
 * fuck the iphone style AiYaXiYaDialog
 */

public class IphoneStyleDialog {

    public static Dialog BackgroudDialog(Context context ) {

        final Dialog backgroudDialog = new Dialog(context, R.style.populayout_style);
        backgroudDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        backgroudDialog.setContentView(R.layout.backgroud_dialog);
        //获取到当前Activity的Window
        Window dialog_window = backgroudDialog.getWindow();
        //设置对话框的位置
        dialog_window.setGravity(Gravity.CENTER);
        //获取到LayoutParams
        WindowManager.LayoutParams dialog_window_attributes = dialog_window.getAttributes();
        //设置对话框位置的偏移量
        dialog_window_attributes.x = 0;
        dialog_window_attributes.y = 0;
        dialog_window.setAttributes(dialog_window_attributes);
        dialog_window.setWindowAnimations(R.style.dialogWindowAnim);

        return backgroudDialog;
    }


    public static Dialog CharacterDialog(Context context ) {

        final Dialog characterDialog = new Dialog(context, R.style.populayout_style);
        characterDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        characterDialog.setContentView(R.layout.character_dialog);
        //获取到当前Activity的Window
        Window dialog_window = characterDialog.getWindow();
        //设置对话框的位置
        dialog_window.setGravity(Gravity.CENTER);
        //获取到LayoutParams
        WindowManager.LayoutParams dialog_window_attributes = dialog_window.getAttributes();
        //设置对话框位置的偏移量
        dialog_window_attributes.x = 0;
        dialog_window_attributes.y = 0;
        dialog_window.setAttributes(dialog_window_attributes);
        dialog_window.setWindowAnimations(R.style.dialogWindowAnim);

        return characterDialog;
    }



    public static Dialog foodDialog(Context context){

        final Dialog foodDialog = new Dialog(context,R.style.populayout_style);
        foodDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        foodDialog.setContentView(R.layout.food_dialog);
        //获取到当前Activity的Window
        Window dialog_window = foodDialog.getWindow();
        //设置对话框的位置
        dialog_window.setGravity(Gravity.CENTER);
        //获取到LayoutParams
        WindowManager.LayoutParams dialog_window_attributes = dialog_window.getAttributes();
        //设置对话框位置的偏移量
        dialog_window_attributes.x = 0;
        dialog_window_attributes.y = 0;
        dialog_window.setAttributes(dialog_window_attributes);
        dialog_window.setWindowAnimations(R.style.dialogWindowAnim);

        return foodDialog;
    }

    public static Dialog reliveDialog(Context context){

        final Dialog reliveDialog = new Dialog(context,R.style.relivelayout_style);
        reliveDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        reliveDialog.setContentView(R.layout.relive_dialog);
        //获取到当前Activity的Window
        Window dialog_window = reliveDialog.getWindow();
        //设置对话框的位置
        dialog_window.setGravity(Gravity.CENTER);
        //获取到LayoutParams
        WindowManager.LayoutParams dialog_window_attributes = dialog_window.getAttributes();
        //设置对话框位置的偏移量
        dialog_window_attributes.x = 0;
        dialog_window_attributes.y = -150;

        return reliveDialog;
    }

    public static Dialog textDialog(Context context){

        final Dialog textDialog = new Dialog(context,R.style.textDialog_style);
        textDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        textDialog.setContentView(R.layout.text_dialog);
        //获取到当前Activity的Window
        Window dialog_window = textDialog.getWindow();
        //设置对话框的位置
        dialog_window.setGravity(Gravity.CENTER);
        //获取到LayoutParams
        WindowManager.LayoutParams dialog_window_attributes = dialog_window.getAttributes();
        //设置对话框位置的偏移量
        dialog_window_attributes.x = -100;
        dialog_window_attributes.y = 100;
        dialog_window.setAttributes(dialog_window_attributes);
        dialog_window.setWindowAnimations(R.style.txDialogAnim);
        ((RelativeLayout) textDialog.findViewById(R.id.dialog_relative)).getBackground().setAlpha(140);
        ((TextView) textDialog.findViewById(R.id.dialog_tx)).setText(TextUtils.AiYaXiYaDialog());
        return textDialog;
    }


    public static Dialog loadingDialog(Context context){

        final Dialog loadingDialog = new Dialog(context,R.style.populayout_style);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingDialog.setContentView(R.layout.loading_dialog);
        //获取到当前Activity的Window
        Window dialog_window = loadingDialog.getWindow();
        //设置对话框的位置
        dialog_window.setGravity(Gravity.CENTER);
        //获取到LayoutParams
        WindowManager.LayoutParams dialog_window_attributes = dialog_window.getAttributes();
        //设置对话框位置的偏移量
        dialog_window_attributes.x = 0;
        dialog_window_attributes.y = 0;

        return loadingDialog;
    }




}

