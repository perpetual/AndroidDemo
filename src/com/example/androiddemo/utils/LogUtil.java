package com.example.androiddemo.utils;

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
			data = converArrayToString(tag, datas);
		}
	}
	
	private static String converArrayToString(String tag, Object[] objects) {
		if (objects == null || objects.length == 0) {
			return "";
		} else {
			StringBuffer buffer = new StringBuffer();
			int size = objects.length;

			for (int i = 0; i < size; i++) {
				Object item = objects[i];
				if (item != null) {
					buffer.append("|");
					if (item instanceof Throwable) {
						buffer.append(((Throwable) item).getMessage());
					} else {
						buffer.append(item.toString());
					}
				}
			}
			return buffer.toString();
		}

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
