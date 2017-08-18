package com.example.dell.raisingpets.Recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.dell.raisingpets.Module.ModuleStep.SensorService;

/**
 * Created by root on 16-12-21.
 */

public class UserpresentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())) {
            Log.e("TAG", "竟然可以解锁");
            context.startService(new Intent(context, SensorService.class));
        }
    }
}
