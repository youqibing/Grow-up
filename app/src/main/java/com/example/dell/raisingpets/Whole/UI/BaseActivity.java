package com.example.dell.raisingpets.Whole.UI;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.example.dell.raisingpets.Module.ModuleSplash.UI.SplashActivity;
import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.Util.ScreenUtil;
import com.example.dell.raisingpets.Util.ToastUtil;
import com.example.dell.raisingpets.Whole.RaisingPetsApplication;

/**
 * Created by dell on 2016/8/10.
 */

public class BaseActivity extends AppCompatActivity {
    private ConnectionChangeReceiver connectionChangeReceiver;
    private boolean isShowing = false;//判断Activity是否已经执行.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        connectionChangeReceiver = new ConnectionChangeReceiver();
        registerReceiver(connectionChangeReceiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(connectionChangeReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // MobclickAgent.onResume(this);
        isShowing = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);
        isShowing = false;
    }

    protected void setImmerseLayout(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //SystemStatusManager tintManager = new SystemStatusManager(this);
            //tintManager.setStatusBarTintEnabled(true);
            // 设置状态栏的颜色
            //tintManager.setStatusBarTintResource(R.color.colorPrimary);

            int statusBarHeight = ScreenUtil.getStatusBarHeight(this.getBaseContext());
            view.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    public boolean isShowing() {
        return isShowing;
    }

    private class ConnectionChangeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = false;//是否连接网络
            //获得网络连接服务
            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager == null){
                return;
            }

            //获取WIFI网络链接状态
            NetworkInfo.State state = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            //判断是否正在使用WIFI网络
            if(NetworkInfo.State.CONNECTED == state){
                isSuccess = true;
                RaisingPetsApplication.useMobile = false;//是否使用移动网络,显然不是.
                ToastUtil.showShortToast(R.string.network_WIFI);
            }

            //获取移动网络状态
            NetworkInfo info = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if(info == null){
                return;
            }
            state = info.getState();
            //判断是否在使用移动网络
            if(!RaisingPetsApplication.useMobile && !(context instanceof SplashActivity)){
                if(NetworkInfo.State.CONNECTED == state){
                    isSuccess = true;
                    RaisingPetsApplication.useMobile = true;
                    ToastUtil.showShortToast(R.string.network_mobile);
                }
                if (!isSuccess) {
                    ToastUtil.showShortToast(R.string.network_disconnected);
                }
            }

        }
    }
}
