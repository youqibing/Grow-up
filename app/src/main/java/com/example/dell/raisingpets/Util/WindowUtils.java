package com.example.dell.raisingpets.Util;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.raisingpets.R;

/**
 * Created by root on 16-11-4.
 */

public class WindowUtils {
    private static final String TAG = "WindowUtils";
    private static View view ;
    private static WindowManager windowManager ;
    private static Context mcontext;
    private static Boolean isShow ;

    private String content_img;
    private String content_title;
    private String content_tx;
    private boolean isUsed;

    public WindowUtils(String content_title, String content_tx, String content_img){
        this.content_img = content_img;
        this.content_title =content_title;
        this.content_tx = content_tx;
    }

    /**
     * 显示弹出框
     */
    public void showPopupWindow(final Context context){

        Log.e("test","showWindow 1");
        isShow = true;
        mcontext = context.getApplicationContext();
        windowManager = (WindowManager)mcontext.getSystemService(Context.WINDOW_SERVICE);
        view = setUpView(context);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        int flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
        params.flags = flags;
        params.format = PixelFormat.TRANSLUCENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        windowManager.addView(view,params);
    }

    public View setUpView(final Context context){
        Log.e("test","showWindow 2");
        View view = LayoutInflater.from(context).inflate(R.layout.backgroud_dialog,null);

        ImageView content_img = (ImageView)view.findViewById(R.id.content_img);
        TextView content_Title = (TextView)view.findViewById(R.id.content_title);
        TextView content_Tx = (TextView)view.findViewById(R.id.content_tx);
        Button isUse_vtn = (Button)view.findViewById(R.id.is_use_button);
        Log.e("test",content_title);

        content_Title.setText(content_title);
        content_Tx.setText(content_tx);

        final View popupWindowView = view.findViewById(R.id.popup_window);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                Rect rect = new Rect();
                popupWindowView.getGlobalVisibleRect(rect);
                if (!rect.contains(x, y)) {
                    hidePopupWindow();
                }

                return false;
            }
        });

        // 点击back键可消除
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        hidePopupWindow();
                        return true;
                    default:
                        return false;
                }
            }
        });

        return view;
    }

    public void hidePopupWindow(){
        if( isShow && null != view){
            windowManager.removeView(view);
            isShow = false;
        }
    }



}
