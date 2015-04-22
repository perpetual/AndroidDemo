package com.example.androiddemo.service;

import java.io.FileDescriptor;

import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

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
public class RemoteDemoService1 extends BaseService implements IBinder.DeathRecipient {

	private final String TAG = RemoteDemoService1.class.getSimpleName();
	private IBinder mMyBinder = null;
	private IBinder mRemoteBinder = null;
	
	public class RemoteDemoServiceBinder extends IRemoteDemoService1.Stub {

		@Override
		public double getQuote(String ticker) throws RemoteException {
			LogUtil.d(TAG, "getQuote", ticker, RemoteDemoService1.this);
			return 3.14;
		}
		
		@Override
		public boolean unlinkToDeath(DeathRecipient recipient, int flags) {
			LogUtil.d(TAG, "unlinkToDeath", "recipient", recipient, "flags", flags);
			return false;
		}
		
		@Override
		public IInterface queryLocalInterface(String descriptor) {
			LogUtil.d(TAG, "queryLocalInterface", "descriptor", descriptor);
			return null;
		}
		
		@Override
		public boolean pingBinder() {
			LogUtil.d(TAG, "pingBinder");
			return false;
		}
		
		@Override
		public void linkToDeath(DeathRecipient recipient, int flags) {		
			LogUtil.d(TAG, "linkToDeath", "recipient", recipient, "flags", flags);
		}
		
		@Override
		public boolean isBinderAlive() {
			LogUtil.d(TAG, "isBinderAlive");
			return false;
		}
		
		@Override
		public String getInterfaceDescriptor() {
			LogUtil.d(TAG, "getInterfaceDescriptor");
			return null;
		}
		
		@Override
		public void dumpAsync(FileDescriptor fd, String[] args) {
			LogUtil.d(TAG, "dumpAsync", "fd", fd, "args", args);
		}
		
		@Override
		public void dump(FileDescriptor fd, String[] args) {
			LogUtil.d(TAG, "dump", "fd", fd, "args", args);
		}

		@Override
		public void setRemoteBinder(IBinder binder) throws RemoteException {
			LogUtil.d(TAG, "setRemoteBinder", "binder", binder);
			mRemoteBinder = binder;
			mRemoteBinder.linkToDeath(RemoteDemoService1.this, 0);
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		LogUtil.d(TAG, "RemoteDemoService1", "onBind", "intent", intent, AndroidDemoUtil.getCurrentProcessName());
		if (null == mMyBinder) {
			mMyBinder = new RemoteDemoServiceBinder();
		}
		return mMyBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		LogUtil.d(TAG, "RemoteDemoService1", "onUnbind", "intent", intent, AndroidDemoUtil.getCurrentProcessName());
		AndroidDemoUtil.showDemoNotification(null);
		boolean ret = super.onUnbind(intent);
		mMyBinder = null;
		if (null != mRemoteBinder) {
			mRemoteBinder.unlinkToDeath(this, 0);
		}
		return ret;
	}

	@Override
	public void binderDied() {
		LogUtil.d(TAG, "binderDied");
	}
}