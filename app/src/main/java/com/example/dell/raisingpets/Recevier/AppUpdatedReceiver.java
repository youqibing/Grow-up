package com.example.dell.raisingpets.Recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.dell.raisingpets.Module.ModuleStep.SensorService;

/**
 * Created by root on 16-12-21.
 */

public class AppUpdatedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, SensorService.class));
    }
}
