package com.example.dell.raisingpets.Module.ModuleSetting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.birbit.android.jobqueue.JobManager;
import com.example.dell.raisingpets.Data.Database.DataBaseOpenHelper;
import com.example.dell.raisingpets.Data.SharedPreferences.ToolPerference;
import com.example.dell.raisingpets.Data.SharedPreferences.UserPreference;
import com.example.dell.raisingpets.Module.ModuleLogin.UI.LoginActivity;
import com.example.dell.raisingpets.Module.ModuleSetting.Job_and_Event.PassWordEvent;
import com.example.dell.raisingpets.Module.ModuleSetting.Job_and_Event.PassWordJob;
import com.example.dell.raisingpets.Module.ModuleSetting.Job_and_Event.SendWordEvent;
import com.example.dell.raisingpets.Module.ModuleStep.SensorService;
import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.Util.TimeUtils;
import com.example.dell.raisingpets.Util.ToastUtil;
import com.example.dell.raisingpets.Whole.RaisingPetsApplication;

import de.greenrobot.event.EventBus;

/**
 * Created by root on 16-11-22.
 */

public class PasswordFragment extends Fragment {

    private RelativeLayout password_main_relative;
    private Button logoout_btn;
    private Button edit_password;
    private TextView now_account;

    private RelativeLayout edit_passWord_ralative;
    private Button edit_btn;
    private Button cancel_btn;
    private EditText old_password_edt;
    private EditText new_password_edt;
    private EditText repeat_new_password_edt;

    private JobManager jobManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
        jobManager = RaisingPetsApplication.getInstance().getJobManager();
        RaisingPetsApplication.getInstance().addActivity(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.password_fragment,container,false);

        password_main_relative = (RelativeLayout)view.findViewById(R.id.password_main_relative);
        logoout_btn = (Button)view.findViewById(R.id.logoout);
        edit_password = (Button)view.findViewById(R.id.edit_password);
        now_account = (TextView)view.findViewById(R.id.now_account);

        edit_passWord_ralative = (RelativeLayout)view.findViewById(R.id.edit_passWord_ralative);
        edit_btn = (Button)view.findViewById(R.id.edit_btn);
        cancel_btn = (Button)view.findViewById(R.id.cancel_btn);
        old_password_edt = (EditText)view.findViewById(R.id.old_password);
        new_password_edt = (EditText)view.findViewById(R.id.new_password);
        repeat_new_password_edt = (EditText)view.findViewById(R.id.repeat_new_password);

        edit_passWord_ralative.setVisibility(View.GONE);
        password_main_relative.setVisibility(View.VISIBLE);

        logoout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataBaseOpenHelper dataBaseOpenHelper = DataBaseOpenHelper.getInstance(RaisingPetsApplication.getContext());
                dataBaseOpenHelper.insertNewDaysteps(TimeUtils.getToday(), 0);
                dataBaseOpenHelper.close();

                UserPreference.getUserEditor().clear().apply();
                ToolPerference.getPedometerpreferences().edit().clear().apply();
                SharedPreferences isrestpreferences = RaisingPetsApplication.getContext().getSharedPreferences("isrest", Context.MODE_PRIVATE);
                SharedPreferences shutdownpreferences = RaisingPetsApplication.getContext().getSharedPreferences("shutdownoffset",Context.MODE_PRIVATE);
                SharedPreferences citypreferences = RaisingPetsApplication.getContext().getSharedPreferences("city_backgroud",0);
                SharedPreferences froestpreferences = RaisingPetsApplication.getContext().getSharedPreferences("froest_backgroud",0);
                SharedPreferences moneypreferences = RaisingPetsApplication.getContext().getSharedPreferences("money",0);

                SharedPreferences.Editor moneyeditor = moneypreferences.edit();
                SharedPreferences.Editor froesteditor = froestpreferences.edit();
                SharedPreferences.Editor cityeditor = citypreferences.edit();
                SharedPreferences.Editor isresteditor = isrestpreferences.edit();
                SharedPreferences.Editor ishutdowneditor = shutdownpreferences.edit();

                moneyeditor.clear().apply();
                froesteditor.clear().apply();
                isresteditor.clear().apply();
                ishutdowneditor.clear().apply();
                cityeditor.clear().apply();


                startActivity(new Intent(getActivity(), LoginActivity.class));
                RaisingPetsApplication.getInstance().exit();
            }
        });

        edit_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_passWord_ralative.setVisibility(View.VISIBLE);
                password_main_relative.setVisibility(View.GONE);
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_passWord_ralative.setVisibility(View.GONE);
                password_main_relative.setVisibility(View.VISIBLE);
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(new_password_edt.getText().toString().length() >= 6 && new_password_edt.getText().toString().length() <= 12)){
                    ToastUtil.showLongToast("密码格式有误,请检查密码长度是否为6-12位");
                }else if(!((new_password_edt.getText().toString()).equals(repeat_new_password_edt.getText().toString()))){
                    ToastUtil.showLongToast("两次输入密码不一致!!!");
                }else {
                    jobManager.addJobInBackground(new PassWordJob(old_password_edt.getText().toString(),new_password_edt.getText().toString()));
                }
            }
        });

        return view;
    }

    public void onEventMainThread(PassWordEvent event){
        boolean isSuccess = event.isSuccess();
        if(isSuccess) {
            old_password_edt.getText().clear();
            new_password_edt.getText().clear();

            edit_passWord_ralative.setVisibility(View.GONE);
            password_main_relative.setVisibility(View.VISIBLE);
            ToastUtil.showLongToast("密码修改成功!!!");
        }else {
            ToastUtil.showLongToast(event.getMsg()+"");
        }
    }

}
