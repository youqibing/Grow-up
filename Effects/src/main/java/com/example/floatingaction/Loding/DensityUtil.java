package com.example.floatingaction.Loding;

import android.content.Context;

/**
 * Created by root on 16-12-30.
 */

public class DensityUtil {

    public static float dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale;
    }
}
