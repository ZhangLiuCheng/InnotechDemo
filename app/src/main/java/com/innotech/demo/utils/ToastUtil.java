package com.innotech.demo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by sunyuqin on 16/8/22.
 */

public class ToastUtil {

    private static Toast sToast;

    public static synchronized void show(Context context, String msg) {
        if (null == sToast) {
            sToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
        sToast.setText(msg);
        sToast.show();
    }
}
