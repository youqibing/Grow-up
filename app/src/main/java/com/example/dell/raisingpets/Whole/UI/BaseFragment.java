package com.example.dell.raisingpets.Whole.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dell on 2016/8/11.
 */

public abstract class BaseFragment extends Fragment {

    public abstract void init();
    public abstract int getResouceId();
    public abstract void initView(View view);

    public abstract void onFragmentFristVisiable();

    private boolean isFristVisible = true;
    private boolean isPrepated = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onPrepareStates();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            onVisible();
        }else {
            onInvisible();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getResouceId(),container,false);
        initView(view);
        Log.e("testView","1");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onVisible() {
        if (isFristVisible) {
            isFristVisible = false;
            onPrepareStates();
        }
    }

    public void onInvisible() {

    }

    private synchronized void onPrepareStates(){
        if(isPrepated){
            onFragmentFristVisiable();
        }else{
            isPrepated = true;
        }
    }


}
