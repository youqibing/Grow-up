package com.example.dell.raisingpets.Recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.raisingpets.R;

/**
 * Created by root on 16-11-29.
 */

public class DialogShowRecevier extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast dialogToast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.text_dialog,null);
        view.getBackground().setAlpha(140);
        ((TextView) view.findViewById(R.id.dialog_tx)).setText("闹铃响了, 可以做点事情了~~");
        dialogToast.setView(view);
        dialogToast.setGravity( Gravity.CENTER, -50, -150);
        dialogToast.setDuration(Toast.LENGTH_LONG);
        dialogToast.show();

    }
}
