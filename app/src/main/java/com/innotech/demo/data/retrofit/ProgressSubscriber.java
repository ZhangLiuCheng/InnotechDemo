package com.innotech.demo.data.retrofit;

import android.app.Dialog;
import android.content.Context;

import com.innotech.demo.utils.DialogUtil;
import com.innotech.demo.utils.ToastUtil;

import rx.Subscriber;

/**
 * Created by sunyuqin on 16/8/22.
 */
public abstract class ProgressSubscriber<T> extends Subscriber<T> {

    private Context context;
    private Dialog dialog;

    public ProgressSubscriber(Context context, String messsage) {
        this.context = context;
        dialog = DialogUtil.getProgressDialog(context, messsage);
    }

    public ProgressSubscriber(Context context) {
        this(context, "加载中");
    }

    @Override
    public void onStart() {
        super.onStart();
        DialogUtil.showDialog(dialog);
    }

    @Override
    public void onCompleted() {
        DialogUtil.dismissDialog(dialog);
    }

    @Override
    public void onError(Throwable e) {
        DialogUtil.dismissDialog(dialog);
        ToastUtil.show(context, e.getMessage());
    }
}
