package com.example.dell.raisingpets.View;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.dell.raisingpets.Data.SharedPreferences.ToolPerference;

/**
 * Created by dell on 2016/10/13.
 */

public class VerticalAnimator {
    private RelativeLayout.LayoutParams params;

    private View animatorView = null;
    private View functionView = null;
    private Context context;

    private boolean isFunctionViewShow = true;
    private int functionViewHeight = 0;

    private int halfHeight;
    private int height;

    public VerticalAnimator(View functionView, View animatorview, Context context) {
        this.functionView = functionView;
        this.animatorView = animatorview;
        this.context = context;

        params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        //params.setMargins(0,10,0,10);
        animatorview.setLayoutParams(params);

        halfHeight = context.getResources().getDisplayMetrics().heightPixels/2 - 50 ;
        height = context.getResources().getDisplayMetrics().heightPixels - 10;
    }

    /**
     * 展开功能页并向上平移动画页
     * */
    public void extendFunctionView() {
        initUpViewHeight();

        if(isFunctionViewShow) {
            isFunctionViewShow = false;
            params.setMargins(1,10,1,10);
            animatorView.postDelayed(new UpAnimatorTask(animatorView),150);

            ObjectAnimator animatorView = ObjectAnimator.ofFloat(functionView, "translationY",0, -functionViewHeight).setDuration(600);
            animatorView.setInterpolator(new AccelerateDecelerateInterpolator());
            animatorView.start();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ToolPerference.storeIsBackEnable(true);//1秒后认为这个上滑页划上去了
                }
            },1000);

        }
    }
    /**
     * 收缩功能页并向下平移动画页
     * */
    public void shrinkFunctionView() {
        initUpViewHeight();

        if(!isFunctionViewShow) {
            isFunctionViewShow = true;

            animatorView.postDelayed(new DownAnimatorTask(animatorView),0);

            ObjectAnimator animatorView = ObjectAnimator.ofFloat(functionView, "translationY", -functionViewHeight ,0).setDuration(700);
            animatorView.setInterpolator(new AccelerateDecelerateInterpolator());
            animatorView.setStartDelay(150);
            animatorView.start();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ToolPerference.storeIsBackEnable(false);
                }
            },1000);

        }
    }

    private void initUpViewHeight() {
        if(functionViewHeight == 0) {
            functionViewHeight = functionView.getHeight();
        }
    }


    private class UpAnimatorTask implements Runnable{

        private View layout;
        private final int DISTANCE  = 20;

        private UpAnimatorTask(View layout){
            this.layout = layout;
        }

        @Override
        public void run() {

            //DISTANCE = DISTANCE + 25;
            //DISTANCE = 20;
            int bottom = layout.getBottom();
            if(bottom > halfHeight){
                layout.offsetTopAndBottom(-DISTANCE);
                layout.postDelayed(this, 0);
            }else {
                params.setMargins(0, 0-halfHeight-110, 0, halfHeight+110);
                layout.setLayoutParams(params);     //这里LayoutGameActivity不论移动到上面还是下面,都一定要"定住"
                //测了半天之后,这个offsetTopAndBottom()方法是有问题的,即便视觉上移动了上去,调用onstart()或者onstop()刷新之后仍然
                //会在原来的位置,但是调用setLayoutParams之后就会确实的改变View的位置,因此调用这个方法定住这个上一的动画页.
            }
        }
    }

    private class DownAnimatorTask implements Runnable{

        private View layout;
        private int DISTANCE  = 0;

        private DownAnimatorTask(View layout){
            this.layout = layout;
        }

        @Override
        public void run() {

            DISTANCE = 25;
            int bottom = layout.getBottom();
            if(bottom < height){
                layout.offsetTopAndBottom(DISTANCE);
                /*
                params.setMargins(0, DISTANCE, 0, 0-DISTANCE);
                layout.setLayoutParams(params);
                */
                layout.postDelayed(this, 0);
            }else {
                params.setMargins(1,1,1,1);
                layout.setLayoutParams(params);
            }
        }

    }
}

