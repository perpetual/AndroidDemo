package com.example.androiddemo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class AndroidUtils {
	private static final String TAG = AndroidUtils
			.getClassName(AndroidUtils.class);

	public static Context APPLICATION_CONTEXT = null;

	public static boolean copyFile(String srcPath, String destPath) {

		// 参数检查
		FileChannel in = null;
		FileChannel out = null;
		try {
			File file = new File(destPath);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}

			in = new FileInputStream(srcPath).getChannel();
			File outFile = new File(destPath);
			out = new FileOutputStream(outFile).getChannel();
			in.transferTo(0, in.size(), out);
			return true;
		} catch (Throwable e) {
			return false;
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}
	}

	public static void hideSoftInput(final Activity activity) {
		InputMethodManager inputManager = (InputMethodManager) APPLICATION_CONTEXT
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (activity != null && activity.getCurrentFocus() != null
				&& activity.getCurrentFocus().getWindowToken() != null) {
			inputManager.hideSoftInputFromWindow(activity.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	public static void showToast(String str) {
		Toast.makeText(APPLICATION_CONTEXT, str, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(final int stringId) {
		Toast.makeText(APPLICATION_CONTEXT, stringId, Toast.LENGTH_SHORT)
				.show();
	}

	public static void showLongToast(String str) {
		Toast.makeText(APPLICATION_CONTEXT, str, Toast.LENGTH_LONG).show();
	}

	public static void showLongToast(final int stringId) {
		Toast.makeText(APPLICATION_CONTEXT, stringId, Toast.LENGTH_LONG).show();
	}

	public static String getClassName(final Class<?> cls) {
		if (null == cls) {
			return "";
		}
		int index = cls.getName().lastIndexOf('.');
		return cls.getName().substring(index + 1);
	}

	public static long getThreadId() {
		return Thread.currentThread().getId();
	}

	public static String getThreadSignature() {
		Thread thread = Thread.currentThread();
		long threadID = thread.getId();
		String name = thread.getName();
		long priority = thread.getPriority();
		String groupName = thread.getThreadGroup().getName();
		String ret = "ID:" + threadID + " Name:" + name + " Priority:"
				+ priority + " GroupName:" + groupName;
		Log.d(TAG, ret);
		return ret;
	}

	public static String getCurrentActivityStackInfo() {
		ActivityManager actionManager = ((ActivityManager) APPLICATION_CONTEXT
				.getSystemService("activity"));
		List<String> activityNameList = new ArrayList<String>();

		if (actionManager != null) {

			List<RunningTaskInfo> localList = null;
			try {
				localList = actionManager.getRunningTasks(1);
			} catch (SecurityException e) {
				Log.w(TAG, "security e=" + e);
			}

			if (null != localList) {
				for (int i = 0; i < localList.size(); ++i) {
					ActivityManager.RunningTaskInfo runningTaskInfo = ((ActivityManager.RunningTaskInfo) localList
							.get(i));
					activityNameList.add(runningTaskInfo.baseActivity.getClassName() + ":" + runningTaskInfo.topActivity.getClassName() + ":" + runningTaskInfo.numActivities);
				}
			}
		} else {
			Log.w(TAG, " actionManager is null");
		}

		return activityNameList.toString();
	}
	
	public static Field[] getPrivateFields(Class cls) {
		Field fields[] = null;

		try {
			fields = cls.getDeclaredFields();

		} catch (Exception e) {
		}
		return fields;
	}
	
	public static Object getPrivateValue(Class cls, String name, Activity obj) {
		Field[] fields = getPrivateFields(cls);
		if (null != fields) {
			for (int i = 0; i < fields.length; ++i) {
				String str = fields[i].getName();
				if (str.equals(name)) {
					Object object = null;
					try {
						object = fields[i].get(obj);
					} catch (Exception e) {
						// TODO: handle exception
					}
					return object;
				}
			}
		}
		return null;
	}
	
	public static String deviceInfo2String() {
		StringBuffer info = new StringBuffer();
		info.append("VERSION.RELEASE");
		info.append(":[");
		info.append(Build.VERSION.RELEASE);
		info.append("] ");

		info.append("VERSION.CODENAME");
		info.append(":[");
		info.append(Build.VERSION.CODENAME);
		info.append("] ");

		info.append("VERSION.INCREMENTAL");
		info.append(":[");
		info.append(Build.VERSION.INCREMENTAL);
		info.append("] ");

		info.append("BOARD");
		info.append(":[");
		info.append(Build.BOARD);
		info.append("] ");

		info.append("DEVICE");
		info.append(":[");
		info.append(Build.DEVICE);
		info.append("] ");

		info.append("DISPLAY");
		info.append(":[");
		info.append(Build.DISPLAY);
		info.append("] ");

		info.append("FINGERPRINT");
		info.append(":[");
		info.append(Build.FINGERPRINT);
		info.append("] ");

		info.append("HOST");
		info.append(":[");
		info.append(Build.HOST);
		info.append("] ");

		info.append("MANUFACTURER");
		info.append(":[");
		info.append(Build.MANUFACTURER);
		info.append("] ");

		info.append("MODEL");
		info.append(":[");
		info.append(Build.MODEL);
		info.append("] ");

		info.append("PRODUCT");
		info.append(":[");
		info.append(Build.PRODUCT);
		info.append("] ");

		info.append("TAGS");
		info.append(":[");
		info.append(Build.TAGS);
		info.append("] ");

		info.append("TYPE");
		info.append(":[");
		info.append(Build.TYPE);
		info.append("] ");

		info.append("USER");
		info.append(":[");
		info.append(Build.USER);
		info.append("] ");

		info.append("c1");
		info.append(":[");
		info.append(Build.CPU_ABI);
		info.append("] ");

		info.append("c2");
		info.append(":[");
		info.append(0);
		info.append("] ");

		return info.toString();
	}

	public static void logChildView(ViewGroup viewGroup) {
		logChildView(viewGroup, 0);
	}
	
	public static void logChildView(View view) {
		if (view instanceof ViewGroup) {
			logChildView((ViewGroup)view, 0);
		}
	}
	

	/**
	 * 打印View树
	 * @param viewGroup
	 * @param level	层级，根为0
	 *
	 * @author Gary in 2014-10-15
	 */
	private static void logChildView(ViewGroup viewGroup, int level) {
		if (null == viewGroup) {
			return;
		}
			
		Log.w(TAG, viewGroup.getClass().getName() + "|level:" + level);
		for (int i = 0; i < viewGroup.getChildCount(); ++i) {
			View childView = viewGroup.getChildAt(i);
			if (childView instanceof ViewGroup) {
				Log.w(TAG, childView.getClass().getName() + "|level:" + (level + 1));
			}
		}
		Log.w(TAG, "--------------------------");
		for (int i = 0; i < viewGroup.getChildCount(); ++i) {
			View childView = viewGroup.getChildAt(i);
			if (childView instanceof ViewGroup) {
				logChildView((ViewGroup) childView, level + 1);
			}
		}
	}
}
