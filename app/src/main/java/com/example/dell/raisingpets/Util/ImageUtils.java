package com.example.dell.raisingpets.Util;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by dell on 2016/8/20.
 */

public class ImageUtils {

    //压缩图片
    public static Bitmap decodeSampledBitmapFromUri(ContentResolver resolver, Uri uri,
                                                    int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;// 如果设置为true,则不会加载到内存，只能返回它的高，宽与类型
        InputStream is = null;
        try {
            is = resolver.openInputStream(uri);
            BitmapFactory.decodeStream(is, null, options);

            options.inSampleSize = ImageUtils.calculateInSampleSize(options, reqWidth, reqHeight);

            options.inJustDecodeBounds = false;
            is = resolver.openInputStream(uri);//这样重新获取一个新的is输入流就可以解决返回null的问题
            return BitmapFactory.decodeStream(is, null, options);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }


    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                            int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;

        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int widthSampleSize = 0;
            int heightSampleSize = 0;
            if (reqWidth < width) {
                widthSampleSize = Math.round((float) width / (float) reqWidth);
            }
            if (reqHeight < height) {
                heightSampleSize = Math.round((float) height / (float) reqHeight);
            }
            inSampleSize = Math.max(widthSampleSize, heightSampleSize);

        }
        return inSampleSize;
    }
}
