package com.example.androiddemo.service;

import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.androiddemo.IRemoteService;

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

	public class RemoteServiceImpl extends IRemoteService.Stub {

		@Override
		public double getQuote(String ticker) throws RemoteException {
			return 3.14;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new RemoteServiceImpl();
	}

}