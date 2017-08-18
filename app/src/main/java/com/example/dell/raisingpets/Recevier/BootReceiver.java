package com.example.dell.raisingpets.Recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.dell.raisingpets.Data.Database.DataBaseOpenHelper;
import com.example.dell.raisingpets.Data.SharedPreferences.ToolPerference;
import com.example.dell.raisingpets.Module.ModuleStep.SensorService;
import com.example.dell.raisingpets.Util.TimeUtils;
import com.example.dell.raisingpets.Whole.RaisingPetsApplication;

/**
 * Created by root on 16-11-24.
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        context.startService(new Intent(context, SensorService.class));

        ToolPerference.storeShutDownOffset(0);
        //SensorService.todayOffset = 0;
        DataBaseOpenHelper dataBaseOpenHelper = DataBaseOpenHelper.getInstance(RaisingPetsApplication.getContext());
        if(dataBaseOpenHelper.getSteps(TimeUtils.getToday())!= Integer.MIN_VALUE){
            dataBaseOpenHelper.insertNewDaysteps(TimeUtils.getToday(), 0);
        }
        dataBaseOpenHelper.close();
    }
}
