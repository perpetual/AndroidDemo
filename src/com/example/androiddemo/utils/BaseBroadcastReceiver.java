package com.example.androiddemo.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;

/**
 * <pre>
 * Copyright (C) 1998-2014 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * Gary		2014-12-2		Create		
 * </pre>
 */
public class BaseBroadcastReceiver extends BroadcastReceiver {
	private static final String TAG = "BaseBroadcastReceiver";
	
	public interface IBaseBroadcastReceiver {
		public void onRecive(Context context, Intent intent);
	}
	
	public IBaseBroadcastReceiver mBaseBroadcastReceiver = null;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		LogUtil.d(TAG, intent.getAction() + "|EXTRA_SCO_AUDIO_STATE:" + intent.getIntExtra(AudioManager.EXTRA_SCO_AUDIO_STATE, -1));
		if (null != mBaseBroadcastReceiver) {
			mBaseBroadcastReceiver.onRecive(context, intent);
		}
	}
	
	public void register(Context context, IntentFilter intentFileter, IBaseBroadcastReceiver baseBroadcastReceiver) {
		if (null == context) {
			return;
		}
		mBaseBroadcastReceiver = baseBroadcastReceiver;
		context.registerReceiver(this, intentFileter);
	}
	
	public void unregister(Context context) {
		if (null == context) {
			return;
		}
		context.unregisterReceiver(this);
	}
}

