package com.example.androiddemo.tools;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.text.TextUtils;

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
public class BluetoothHelper extends CommonCallbacks implements
		BaseBroadcastReceiver.IBaseBroadcastReceiver, BluetoothProfile.ServiceListener {

	public static final String ACTION_SCO_AUDIO_STATE_UPDATED = AndroidDemoUtil.getSDKVersion() < AndroidDemoUtil.API_LEVEL_14 ? AudioManager.ACTION_SCO_AUDIO_STATE_CHANGED
			: AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED;

	public static final int OP_CODE_SCO_AUDIO_STATE_UPDATE = 0;	
	public static final int OP_CODE_BLUETOOTH_SERVICE_CONNECTION_UPDATE = 1;	
	
	private BaseBroadcastReceiver mSCOAudioReceiver = null;
	private Context mContext = null;

	public static boolean isConnectHeadset() {
		try {
			if (android.os.Build.VERSION.SDK_INT >= AndroidDemoUtil.API_LEVEL_14) {
				boolean b = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(
						android.bluetooth.BluetoothProfile.HEADSET) == android.bluetooth.BluetoothProfile.STATE_CONNECTED;
				return b;
			} else {
				return BluetoothAdapter.getDefaultAdapter().isEnabled();
			}
		} catch (Exception exc) {
		}
		return false;
	}

	public BluetoothHelper(Context context) {
		mSCOAudioReceiver = new BaseBroadcastReceiver();
		mContext = context;
		mSCOAudioReceiver.register(mContext,
				AndroidDemoUtil.createIntentFilter(ACTION_SCO_AUDIO_STATE_UPDATED), this);
		BluetoothAdapter.getDefaultAdapter().getProfileProxy(mContext, this, BluetoothProfile.HEADSET);
	}

	@Override
	public void onReciveBroadcast(Context context, Intent intent) {
		if (TextUtils.equals(AudioManager.ACTION_SCO_AUDIO_STATE_CHANGED, intent.getAction())
				|| TextUtils
						.equals(AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED, intent.getAction())) {
			doCallbacks(OP_CODE_SCO_AUDIO_STATE_UPDATE, intent.getIntExtra(AudioManager.EXTRA_SCO_AUDIO_STATE, -1), 0,
					intent.getAction(), intent);
		}
	}

	public void release() {
		mSCOAudioReceiver.register(mContext,
				AndroidDemoUtil.createIntentFilter(ACTION_SCO_AUDIO_STATE_UPDATED), this);
		mSCOAudioReceiver.unregister(mContext);
	}

	@Override
	public void onServiceConnected(int profile, BluetoothProfile proxy) {
		doCallbacks(OP_CODE_BLUETOOTH_SERVICE_CONNECTION_UPDATE, profile, 0, "onServiceConnected", null);
	}

	@Override
	public void onServiceDisconnected(int profile) {
		doCallbacks(OP_CODE_BLUETOOTH_SERVICE_CONNECTION_UPDATE, profile, 0, "onServiceDisconnected", null);
	}
}
