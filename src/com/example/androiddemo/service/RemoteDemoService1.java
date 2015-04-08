package com.example.androiddemo.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.androiddemo.activity.ServiceActivity;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;

/**
 * 
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
public class RemoteDemoService1 extends BaseService {

	private final String TAG = RemoteDemoService1.class.getSimpleName();
	private IBinder mRemoteDemoServiceBinder = null;
	
	public class RemoteDemoServiceBinder extends IRemoteDemoService.Stub {

		@Override
		public double getQuote(String ticker) throws RemoteException {
			LogUtil.d(TAG, "getQuote", ticker);
			return 3.14;
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		if (null == mRemoteDemoServiceBinder) {
			mRemoteDemoServiceBinder = new RemoteDemoServiceBinder();
		}
		AndroidDemoUtil.showDemoNotification(ServiceActivity.class);
		return mRemoteDemoServiceBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		AndroidDemoUtil.showDemoNotification(null);
		return super.onUnbind(intent);
	}
}