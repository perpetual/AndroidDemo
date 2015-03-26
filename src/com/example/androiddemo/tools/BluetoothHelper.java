package com.example.androiddemo.tools;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

import com.example.androiddemo.receiver.BaseBroadcastReceiver;
import com.example.androiddemo.utils.AndroidDemoUtil;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-3-26		Create		
 * </pre>
 */
public class BluetoothHelper extends CommonCallbacks implements BaseBroadcastReceiver.IBaseBroadcastReceiver {

	private BaseBroadcastReceiver mSCOAudioReceiver = null;
	private Context mContext = null;
	
	public BluetoothHelper(Context context) {
		mSCOAudioReceiver = new BaseBroadcastReceiver();
		mContext = context;
	}
	
	@Override
	public void onReciveBroadcast(Context context, Intent intent) {
		doCallbacks(0, 0, 0, null, intent);
	}
	
	public void register() {
		mSCOAudioReceiver.register(mContext, AndroidDemoUtil.createIntentFilter(AndroidDemoUtil
				.getSDKVersion() < 14 ? AudioManager.ACTION_SCO_AUDIO_STATE_CHANGED
				: AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED), this);
	}
}

