package com.example.dell.raisingpets.Module.ModuleWelcome.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.raisingpets.R;

/**
 * Created by dell on 2016/8/11.
 */

public class WelcomeFragment_Two extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome2,container,false);

        return view;
    }
}
