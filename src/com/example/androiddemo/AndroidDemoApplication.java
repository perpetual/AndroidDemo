package com.example.androiddemo;

import com.example.androiddemo.utils.AndroidDemoUtil;

import android.app.Application;
import android.util.Log;

/**
 * <pre>
 * Copyright (C) 1998-2014 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * Gary		2014-10-22		Create		
 * </pre>
 */
public class AndroidDemoApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("AndroidDemoApplication", "AndroidDemoApplication:onCreate");
		AndroidDemoUtil.APPLICATION_CONTEXT = getApplicationContext();
		AndroidDemoUtil.RESOURCES = getResources();
	}
}

