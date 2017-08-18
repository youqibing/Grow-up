package com.example.dell.raisingpets.View;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.raisingpets.R;
import com.example.dell.raisingpets.Util.TextUtils;

/**
 * Created by root on 16-12-21.
 */

public class IphoneStyleToast {

    public static Toast dialogToast(Context context ){
        Toast dialogToast;

        dialogToast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.text_dialog,null);
        view.getBackground().setAlpha(140);
        ((TextView) view.findViewById(R.id.dialog_tx)).setText(TextUtils.AiYaXiYaDialog());
        dialogToast.setView(view);
        dialogToast.setGravity( Gravity.CENTER, -50, -80);
        dialogToast.setDuration(Toast.LENGTH_LONG);

        return dialogToast;
    }
}
