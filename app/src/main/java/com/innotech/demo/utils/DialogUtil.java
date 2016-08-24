package com.innotech.demo.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.innotech.demo.R;

/**
 * 自定义Dialog
 */
public final class DialogUtil {
	
	private DialogUtil() {
		
	}

	public static void showDialog(Dialog dialog) {
		if (null != dialog && !dialog.isShowing()) {
			dialog.show();
		}
	}
	
	public static void dismissDialog(Dialog dialog) {
		if (null != dialog && dialog.isShowing()) {
			dialog.dismiss();
		}
	}
	
	public static Dialog getProgressDialog(Context context, String message) {
		return DialogUtil.getProgressDialog(context, message, false);
	}
	
	public static Dialog getProgressDialog(Context context, String message, boolean cancel) {
		LayoutInflater inflater = LayoutInflater.from(context);  
		// 得到加载view
        View v = inflater.inflate(R.layout.loading_dialog, null);
        TextView dialogName = (TextView) v.findViewById(R.id.tipTextView);
        dialogName.setText(message);
        final Dialog dialog = new Dialog(context, R.style.progressDialog);
        dialog.setContentView(v, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		dialog.setCancelable(cancel);
		dialog.setCanceledOnTouchOutside(cancel);
		return dialog;
	}
}
