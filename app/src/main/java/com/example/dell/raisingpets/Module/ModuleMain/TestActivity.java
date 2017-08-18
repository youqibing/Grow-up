package com.example.dell.raisingpets.Module.ModuleMain;

import android.os.Bundle;

import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.Whole.UI.BaseActivity;
import com.example.floatingaction.WeatherView.DynamicWeatherView;

/**
 * Created by root on 16-12-6.
 */

public class TestActivity extends BaseActivity {
    private DynamicWeatherView weatherView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //weatherView = (DynamicWeatherView)findViewById(R.id.main_dynamicweatherview);
        //Log.e("testMain","1111111111111111111111");
        //weatherView.setDrawerType(WIND_D);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //weatherView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //weatherView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //weatherView.onDestroy();
    }
}
