package com.example.dell.raisingpets.Module.ModuleClock.UI.Acivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.example.dell.raisingpets.Module.ModuleClock.UI.View.SelectRepeatPopupView;
import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.Util.AlarmManagerUtil;
import com.example.dell.raisingpets.Whole.UI.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dell on 2016/8/30.
 */

public class ClockActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout repeat_relative;
    private RelativeLayout ring_relative;
    private LinearLayout whole_layout;

    private TextView time_tx;
    private TextView repeat_tx;
    private TextView ring_tx;

    private Button confirm_btn;
    private TimePickerView pickerView;

    private String time;

    private int cycleDays;
    private int ring;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clock_activity);

        initView();
    }

    public void initView() {
        whole_layout = (LinearLayout) findViewById(R.id.whole_layout);

        confirm_btn = (Button) findViewById(R.id.confirm_btn);
        confirm_btn.setOnClickListener(this);

        repeat_relative = (RelativeLayout) findViewById(R.id.repeat_rl);
        repeat_relative.setOnClickListener(this);

        ring_relative = (RelativeLayout) findViewById(R.id.ring_rl);
        ring_relative.setOnClickListener(this);

        repeat_tx = (TextView) findViewById(R.id.tv_repeat_value);
        ring_tx = (TextView) findViewById(R.id.tv_ring_value);

        time_tx = (TextView) findViewById(R.id.time_tx);
        time_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickerView.show();
            }
        });

        pickerView = new TimePickerView(this, TimePickerView.Type.HOURS_MINS);
        pickerView.setTime(new Date());
        pickerView.setCyclic(true);
        pickerView.setCancelable(true);
        //选择时间后回掉显示
        pickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                time = format.format(date);
                time_tx.setText(time);
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.repeat_rl:
                selectRepeat();
                break;
            case R.id.ring_rl:

                break;
            case R.id.confirm_btn:
                confirmClock();
                break;
        }

    }

    private void confirmClock(){
        if(time != null && time.length() > 0){
            String[] times = time.split(":");
            if(cycleDays == 0){//每天的闹钟
                AlarmManagerUtil.setAlarm(this,0,Integer.parseInt(times[0]),Integer.parseInt(times[1]),0,0);
            }else if(cycleDays == -1){//只响一次的闹钟
                AlarmManagerUtil.setAlarm(this,1,Integer.parseInt(times[0]),Integer.parseInt(times[1]),0,0);
            }else {//多选，周几的闹钟，此时的cycleDays是返回的是我们选择的周几的数值之和。
                String weeksStr = parseRepeat(cycleDays,1);//再解析一遍，只不过这里返回的就是数字的字符串了.
                String[] weeks = weeksStr.split(",");
                for(int i = 0; i < weeks.length; i++){//注册i遍闹钟,就是周几重复,每一个重复的周日实际上都是一个独立的闹钟.
                    AlarmManagerUtil.setAlarm(this,2,Integer.parseInt(times[0]),Integer.parseInt(times[1]),i, Integer.parseInt(weeks[i]));
                }
            }
        }
    }

    public void selectRepeat(){
        final SelectRepeatPopupView selectRepeatPopupView = new SelectRepeatPopupView(this);
        selectRepeatPopupView.showPopupWindow(whole_layout);
        selectRepeatPopupView.setOnSelectRepeatPopupViewListener(new SelectRepeatPopupView.SelectRepeatPopupViewListener() {
            @Override
            public void obtainMessage(int flag, String repeat) {
                switch (flag){

                    case 0://星期一
                        break;
                    case 1://星期二
                        break;
                    case 2://星期三
                        break;
                    case 3://星期四
                        break;
                    case 4://星期五
                        break;
                    case 5://星期六
                        break;
                    case 6://星期日
                        break;

                    case 7://每天
                        repeat_tx.setText("每天");
                        cycleDays = 0;
                        selectRepeatPopupView.popupDismiss();
                        break;
                    case 8://只响一次
                        repeat_tx.setText("只响一次");
                        selectRepeatPopupView.popupDismiss();
                        cycleDays = -1;
                        break;

                    case 9://确定,此时对所有的选择进行一个处理
                        int repeatDays = Integer.valueOf(repeat);
                        repeat_tx.setText(parseRepeat(repeatDays,0));
                        cycleDays = repeatDays;
                        selectRepeatPopupView.popupDismiss();
                        break;
                    default:
                        break;

                }
            }
        });
    }


    /**
     * @param repeatDays 解析转化为数字之和的闹钟周期
     * @param flag   flag=0返回带有汉字的周一，周二等; flag=1,返回weeks(1,2,3)
     * @return
     */
    public String parseRepeat(int repeatDays , int flag){   //写了这么一大堆就是为了返回选择的文字,这里设计的非常巧妙
        String cycleDays = "";
        String weeks = "";

        if(repeatDays == 0){
            repeatDays = 127;
        }
        if(repeatDays % 2 == 1){
            cycleDays = "周一";
            weeks = "1";
        }
        if(repeatDays % 4 >= 2){
            if("".equals(cycleDays)){  //等于空表示只选择了这一个,此时repeatDays % 4 == 2
                cycleDays = "周二";
                weeks = "2";
            }else {             //否则就是还选了别的，把别的加上
                cycleDays = cycleDays + "," + "周二";
                weeks = weeks + "2";
            }
        }
        if(repeatDays % 8 >= 4){
            if("".equals(cycleDays)){
                cycleDays = "周三";
                weeks = "3";
            }else {
                cycleDays = cycleDays + "," + "周三";
                weeks = weeks + "，" + "3";
            }
        }
        if(repeatDays % 16 >= 8){
            if("".equals(cycleDays)){
                cycleDays = "周四";
                weeks = "4";
            }else {
                cycleDays = cycleDays + "," + "周四";
                weeks = weeks + "," + "4";
            }
        }
        if(repeatDays % 32 >= 16){
            if("".equals(cycleDays)){
                cycleDays = "周五";
                weeks = "5";
            }else {
                cycleDays = cycleDays + "," + "周五";
                weeks = weeks + "," + "5";
            }
        }
        if(repeatDays % 64 >= 32){
            if("".equals(cycleDays)){
                cycleDays = "周六";
                weeks = "6";
            }else {
                cycleDays = cycleDays + "," + "周六";
                weeks = weeks + "6";
            }
        }
        if (repeatDays / 64 == 1) {
            if ("".equals(cycleDays)) {
                cycleDays = "周日";
                weeks = "7";
            } else {
                cycleDays = cycleDays + "," + "周日";
                weeks = weeks + "," + "7";
            }
        }

        return flag == 0 ? cycleDays : weeks; //根据flag的值选择返回文字还是数字
    }


}