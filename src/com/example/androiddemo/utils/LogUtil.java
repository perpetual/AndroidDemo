package com.example.androiddemo.utils;

import android.provider.Telephony.Sms.Conversations;
import android.util.Log;

public class LogUtil {

	public static void v(String TAG, String msg) {
		Log.v(TAG, msg);
	}

	public static void v(String TAG, int value) {
		LogUtil.v(TAG, String.valueOf(value));
	}
	
	public static void d(String tag, Object... datas) {

		if (tag == null || datas == null)
			return;

		String data = null;

		if (data == null) {
			data = AndroidDemoUtil.converArrayToString(datas);
		}
		d(tag, data);
	}
	
	public static void d(String TAG, String msg) {
		Log.d(TAG, msg);
	}

	public static void d(String TAG, int value) {
		LogUtil.d(TAG, String.valueOf(value));
	}

	public static void i(String TAG, String msg) {
		Log.i(TAG, msg);
	}

	public static void i(String TAG, int value) {
		LogUtil.i(TAG, String.valueOf(value));
	}

	public static void e(String TAG, String msg) {
		Log.e(TAG, msg);
	}

	public static void w(String TAG, int value) {
		LogUtil.w(TAG, String.valueOf(value));
	}
	public static void w(String TAG, String msg) {
		Log.w(TAG, msg);
	}

	public static void e(String TAG, int value) {
		LogUtil.e(TAG, String.valueOf(value));
	}
}
