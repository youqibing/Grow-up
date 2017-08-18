package com.example.dell.raisingpets.Module.ModuleClock.UI.View;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.dell.raisingpets.R;

/**
 * Created by dell on 2016/8/30.
 */

public class SelectRepeatPopupView implements View.OnClickListener {
    private TextView tv_mon;
    private TextView tv_tue;
    private TextView tv_wed;
    private TextView tv_thu;
    private TextView tv_fri;
    private TextView tv_sat;
    private TextView tv_sun;
    private TextView tv_sure;
    private TextView every_day;
    private TextView tv_cycle_once;
    private PopupWindow popupWindow;

    private Context context;
    private SelectRepeatPopupViewListener selectRepeatPopupViewListener;

    public SelectRepeatPopupView(Context context){
        this.context = context;
        popupWindow = new PopupWindow(context);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.AnimBottom);
        popupWindow.setContentView(initViews());
        popupWindow.getContentView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popupWindow.setFocusable(false);
                return true;
            }
        });
    }

    private View initViews(){
        View view = LayoutInflater.from(context).inflate(R.layout.seleccycle_pop_window,null);

        tv_cycle_once = (TextView) view.findViewById(R.id.tv_drugcycle_once);
        every_day = (TextView) view.findViewById(R.id.tv_drugcycle_0);
        tv_mon = (TextView) view.findViewById(R.id.tv_drugcycle_1);
        tv_tue = (TextView) view.findViewById(R.id.tv_drugcycle_2);
        tv_wed = (TextView) view.findViewById(R.id.tv_drugcycle_3);
        tv_thu = (TextView) view.findViewById(R.id.tv_drugcycle_4);
        tv_fri = (TextView) view.findViewById(R.id.tv_drugcycle_5);
        tv_sat = (TextView) view.findViewById(R.id.tv_drugcycle_6);
        tv_sun = (TextView) view.findViewById(R.id.tv_drugcycle_7);
        tv_sure = (TextView) view.findViewById(R.id.tv_drugcycle_sure);

        tv_cycle_once.setOnClickListener(this);
        tv_mon.setOnClickListener(this);
        tv_tue.setOnClickListener(this);
        tv_wed.setOnClickListener(this);
        tv_thu.setOnClickListener(this);
        tv_fri.setOnClickListener(this);
        tv_sat.setOnClickListener(this);
        tv_sun.setOnClickListener(this);
        every_day.setOnClickListener(this);
        tv_sure.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tv_drugcycle_1:
                setDrawable(tv_mon,0);//星期一
                break;
            case R.id.tv_drugcycle_2:
                setDrawable(tv_tue, 1);//星期二
                break;
            case R.id.tv_drugcycle_3:
                setDrawable(tv_wed,2);//星期三
                break;
            case R.id.tv_drugcycle_4:
                setDrawable(tv_thu,3);//星期四
                break;
            case R.id.tv_drugcycle_5:
                setDrawable(tv_fri,4);//星期五
                break;
            case R.id.tv_drugcycle_6:
                setDrawable(tv_sat,5);//星期六
                break;
            case R.id.tv_drugcycle_7:
                setDrawable(tv_sun,6);//星期日
                break;

            case R.id.tv_drugcycle_0:
                setDrawable(null,7);//每天
                break;
            case R.id.tv_drugcycle_once:
                setDrawable(null,8);//只响一次
                break;

            case R.id.tv_drugcycle_sure://确定
                int repeatDay = ((tv_mon.getCompoundDrawables()[2] == null) ? 0 : 1) * 1
                        +((tv_tue.getCompoundDrawables()[2] == null) ? 0 : 1) * 2
                        +((tv_wed.getCompoundDrawables()[2] == null) ? 0 : 1) * 4
                        +((tv_thu.getCompoundDrawables()[2] == null) ? 0 : 1) * 8
                        +((tv_fri.getCompoundDrawables()[2] == null) ? 0 : 1) * 16
                        +((tv_sat.getCompoundDrawables()[2] == null) ? 0 : 1) * 32
                        +((tv_sun.getCompoundDrawables()[2] == null) ? 0 : 1) * 64;
                selectRepeatPopupViewListener.obtainMessage(9,String.valueOf(repeatDay));
                popupDismiss();
                break;
            default:
                break;
        }
    }

    private void setDrawable(TextView view , int flag){  //设置已选星期数的对勾标记,并通过接口传递标志及消息
        Drawable right = context.getResources().getDrawable(R.drawable.cycle_check);
        right.setBounds(0,0,right.getMinimumWidth(),right.getMinimumHeight());

        //textView.setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom)
        if(view != null){
            if(view.getCompoundDrawables()[2] == null){
                view.setCompoundDrawables(null,null,right,null);
            }else {
                view.setCompoundDrawables(null,null,null,null);
            }
            selectRepeatPopupViewListener.obtainMessage(flag,"");
        }else {
            selectRepeatPopupViewListener.obtainMessage(flag,"");
        }

    }

    public void popupDismiss(){
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    public interface SelectRepeatPopupViewListener{
        void obtainMessage(int flag,String ret);
    }

    public void setOnSelectRepeatPopupViewListener(SelectRepeatPopupViewListener selectRepeatPopupViewListener){
        this.selectRepeatPopupViewListener = selectRepeatPopupViewListener;
    }

    public void showPopupWindow(View rootView) {
        // 第一个参数是要将PopupWindow放到的View，第二个参数是位置，第三第四是偏移值
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }

}
