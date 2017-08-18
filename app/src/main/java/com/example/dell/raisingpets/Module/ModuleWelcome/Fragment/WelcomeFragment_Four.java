package com.example.dell.raisingpets.Module.ModuleWelcome.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dell.raisingpets.Data.SharedPreferences.ToolPerference;
import com.example.dell.raisingpets.Module.ModuleLogin.UI.LoginActivity;
import com.example.dell.raisingpets.R;

/**
 * Created by dell on 2016/8/11.
 */

public class WelcomeFragment_Four extends Fragment {
    private Button goto_app;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome4,container,false);
        goto_app = (Button)view.findViewById(R.id.goto_app);
        goto_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                if(ToolPerference.isAPPFirstUse()){
                    ToolPerference.storeAppFirstUse(false);
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().overridePendingTransition(R.anim.hide_rendering_delay, R.anim.fade_out);
                }
            }
        });

        return view;
    }

}
