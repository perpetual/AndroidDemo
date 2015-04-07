package com.example.androiddemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-4-7		Create		
 * </pre>
 */
public class DemoService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}

