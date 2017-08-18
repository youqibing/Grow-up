package com.example.dell.raisingpets.Module.ModuleSetting;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.dell.raisingpets.Data.SharedPreferences.ToolPerference;
import com.example.dell.raisingpets.R;

/**
 * Created by root on 16-11-22.
 */

public class AttentionFragment extends Fragment implements View.OnClickListener{

    private ImageView notification;
    private View view;
    //private CardView cav;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.attention_fragment,container,false);
        notification = (ImageView)view.findViewById(R.id.notification_img);
        notification.setImageResource(R.mipmap.toggle_open);
        notification.setOnClickListener(this);
        ToolPerference.storeIsNotification(true);

        /*
        cav = (CardView)view.findViewById(R.id.cv_add);
        cav.getBackground().setAlpha(120);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.addOnLayoutChangeListener(new FragmentLayout.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(FragmentLayout v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                    //进行自己的动画操作
                    ShowEnterAnimation(view);
                }
            });
        }
        */

        return view;
    }

    public void ShowEnterAnimation(final View view){
        Animator mAnimator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mAnimator = ViewAnimationUtils.createCircularReveal(view, view.getWidth()/2, view.getHeight(), 50, view.getHeight());
        }
        mAnimator.setDuration(800);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

            }

            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.notification_img:
                if(ToolPerference.getIsNotification()){
                    notification.setImageResource(R.mipmap.toggle_unopen);
                    ToolPerference.storeIsNotification(false);
                }else {
                    notification.setImageResource(R.mipmap.toggle_open);
                    ToolPerference.storeIsNotification(true);
                }

            break;
        }
    }
}
