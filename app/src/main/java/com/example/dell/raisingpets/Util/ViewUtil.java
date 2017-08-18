package com.example.dell.raisingpets.Util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.dell.raisingpets.Whole.RaisingPetsApplication;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by dell on 2016/8/11.
 */

public class ViewUtil {
    public static View findActionBarContainer(Activity activity) {
        int id = activity.getResources().getIdentifier("action_bar_container", "id", "android");
        return activity.findViewById(id);
    }

    public static View findSplitActionBar(Activity activity) {
        int id = activity.getResources().getIdentifier("split_action_bar", "id", "android");
        return activity.findViewById(id);
    }

    public static float getStatusBarHeight() {
        Resources resources = RaisingPetsApplication.getContext().getResources();
        int status_bar_height_id = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimension(status_bar_height_id);
    }

    /**
     * 滚动列表到顶端
     *
     * @param listView
     */
    public static void smoothScrollListViewToTop(final ListView listView) {
        if (null == listView) {
            return;
        }
        smoothScrollListView(listView, 0);
        listView.postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.setSelection(0);
            }
        }, 200);
    }

    /**
     * 滚动列表到position
     *
     * @param listView
     * @param position
     */
    public static void smoothScrollListView(ListView listView, int position) {
        if (Build.VERSION.SDK_INT >= 11) {
            listView.smoothScrollToPositionFromTop(0, 0);
        } else {
            listView.setSelection(position);
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = RaisingPetsApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = RaisingPetsApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) RaisingPetsApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width;
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            Point size = new Point();
            display.getSize(size);
            width = size.x;
        } else {
            width = display.getWidth();
        }
        return width;
    }

    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) RaisingPetsApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int height;
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            Point size = new Point();
            display.getSize(size);
            height = size.y;
        } else {
            height = display.getHeight();
        }
        return height;
    }

    public static int getDp2Px(int id) {
        return RaisingPetsApplication.getContext().getResources().getDimensionPixelSize(id);
    }

    public static void setX(View view, int x) {
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            view.setTranslationX(x);
        } else {
            if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                params.leftMargin = x;
                view.setLayoutParams(params);
            } else if (view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
                params.leftMargin = x;
                view.setLayoutParams(params);
            }
        }
    }


    public static void setY(View view, int y) {
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            view.setTranslationY(y);
        } else {
            ViewHelper.setY(view, y);

//            ObjectAnimator animator = ObjectAnimator.ofFloat(
//                    view, "translationY", ViewUtils.getDp2Px(R.dimen.main_tab_height));
//            animator.setDuration(0);
//            animator.start();

//            if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
//                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)view.getLayoutParams();
//                params.topMargin = y;
//                view.setLayoutParams(params);
//            } else if(view.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
//                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();
//                params.topMargin = y;
//                view.setLayoutParams(params);
//            }
        }
    }

    public static int getColor(int id) {
        return RaisingPetsApplication.getContext().getResources().getColor(id);
    }

    public static Drawable getDrawable(int id) {
        return RaisingPetsApplication.getContext().getResources().getDrawable(id);
    }

    public static float getDensity() {
        return RaisingPetsApplication.getContext().getResources().getDisplayMetrics().density;
    }

    public static int getByteCount(Bitmap bitmap) {
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        } else {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }
    }

    public static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }
}
