package com.innotech.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.File;

/**
 * 系统功能函数.
 */
public final class CommonUtil {

	/**
	 * 获取当前版本信息.
	 */
	public static String getVersionName(Context context) {
		try {
			final PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			String versionName = packageInfo.versionName;
			String [] newStr = versionName.split("-");
			return newStr[0];
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取metaData
	 * @return
	 */
	public static String getMetaData(Context context) {
		try {
			ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
					PackageManager.GET_META_DATA);
			return appInfo.metaData.getString("TD_CHANNEL_ID");
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 拨打电话.
	 * @param context
	 * @param phone
	 */
	public static void callPhone(Context context, String phone) {
		if (null == phone || "".equals(phone)) {
			Toast.makeText(context, "号码不能为空...", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + phone));
		context.startActivity(intent);
	}
	
	/**
	 * 安装apk.
	 * @param apkfile
	 */
	public static void installApk(Context context, File apkfile) {
		// 通过Intent安装APK文件
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * 设置statusbar颜色
	 * @param activity
	 * @param colorResId
	 */
	public static void setWindowStatusBarColor(Activity activity, int colorResId) {
		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				Window window = activity.getWindow();
				window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
				window.setStatusBarColor(activity.getResources().getColor(colorResId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 隐藏软键盘
 	 */
    public static void hideKeyboard(Activity context) {
        if (context.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (context.getCurrentFocus() != null) {
                InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

	/**
	 * 显示软键盘
 	 */
	public static void showKeyboard(Activity context) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
