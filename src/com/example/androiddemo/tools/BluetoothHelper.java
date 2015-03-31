package com.example.androiddemo.tools;

import java.util.Iterator;
import java.util.Set;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.text.TextUtils;

import com.example.androiddemo.BluetoothReceiver;
import com.example.androiddemo.receiver.BaseBroadcastReceiver;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 * 
 * Description：外部使用蓝牙sco状态还是优先使用{@link AudioManager#ACTION_SCO_AUDIO_STATE_UPDATED}中相关的状态
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-3-26		Create
 * </pre>
 */
public class BluetoothHelper extends CommonCallbacks implements
		BaseBroadcastReceiver.IBaseBroadcastReceiver, BluetoothProfile.ServiceListener {

	private static final String TAG = BluetoothHelper.class.getSimpleName();
	
	public static final String ACTION_SCO_AUDIO_STATE_UPDATED = AndroidDemoUtil.getSDKVersion() < AndroidDemoUtil.API_LEVEL_14 ? AudioManager.ACTION_SCO_AUDIO_STATE_CHANGED
			: AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED;
	public static final int OP_CODE_SCO_AUDIO_STATE_UPDATE = 0;	
	public static final int OP_CODE_BLUETOOTH_SERVICE_CONNECTION_UPDATE = 1;	
	public static final int OP_CODE_ACTION_CONNECTION_STATE_CHANGED = 2;
	public static final int OP_CODE_ACTION_AUDIO_STATE_CHANGED = 3;
	
	private static final ComponentName sComponentName = new ComponentName(BluetoothReceiver.class.getPackage().getName(), BluetoothReceiver.class.getName());
	
	private BluetoothReceiver mSCOAudioReceiver = null;
	private Context mContext = null;
	private AudioManager mAudioManager = null;

	public static boolean isConnectHeadset() {
		try {
			if (android.os.Build.VERSION.SDK_INT >= AndroidDemoUtil.API_LEVEL_14) {
				int connectionState = BluetoothAdapter.getDefaultAdapter()
						.getProfileConnectionState(BluetoothProfile.HEADSET);
				return BluetoothProfile.STATE_CONNECTED == connectionState;
			} else {
				return BluetoothAdapter.getDefaultAdapter().isEnabled();
			}
		} catch (Exception exc) {
		}
		return false;
	}

	public BluetoothHelper(Context context) {
		mSCOAudioReceiver = new BluetoothReceiver();
		mContext = context;
		mAudioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
		mSCOAudioReceiver.register(mContext,
				AndroidDemoUtil.createIntentFilter(ACTION_SCO_AUDIO_STATE_UPDATED), this);
		BluetoothAdapter.getDefaultAdapter().getProfileProxy(mContext, this, BluetoothProfile.HEADSET);
		mSCOAudioReceiver.register(mContext, AndroidDemoUtil.createIntentFilter(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED), this);
		mSCOAudioReceiver.register(mContext, AndroidDemoUtil.createIntentFilter(BluetoothHeadset.ACTION_AUDIO_STATE_CHANGED), this);
	}

	public static String getConnectState() {
		String state = "";
		final int stateCode = BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(BluetoothProfile.HEADSET);
		switch (BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(BluetoothProfile.HEADSET)) {
		case BluetoothHeadset.STATE_CONNECTED:
			state = "STATE_CONNECTED";
			break;
		case BluetoothHeadset.STATE_CONNECTING:
			state = "STATE_CONNECTING";
			break;
		case BluetoothHeadset.STATE_DISCONNECTED:
			state = "STATE_DISCONNECTED";
			break;
		case BluetoothHeadset.STATE_DISCONNECTING:
			state = "STATE_DISCONNECTING";
			break;
		default:
			state = String.valueOf(stateCode);
			break;
		}
		return state;
	}
	
	public static String getAudioConnectState(int audioState) {
		String state = "";
		switch (audioState) {
		case BluetoothHeadset.STATE_AUDIO_CONNECTED:
			state = "STATE_AUDIO_CONNECTED";
			break;
		case BluetoothHeadset.STATE_AUDIO_CONNECTING:
			state = "STATE_AUDIO_CONNECTING";
			break;
		case BluetoothHeadset.STATE_AUDIO_DISCONNECTED:
			state = "STATE_AUDIO_DISCONNECTED";
			break;
		default:
			state = String.valueOf(audioState);
			break;
		}
		return state;
	}
	
	public static String getScoAudioState(int scoAudioState) {
		String state = "";
		switch (scoAudioState) {
		case AudioManager.SCO_AUDIO_STATE_DISCONNECTED:
			state = "SCO_AUDIO_STATE_DISCONNECTED";
			break;
		case AudioManager.SCO_AUDIO_STATE_CONNECTING:
			state = "SCO_AUDIO_STATE_CONNECTING";
			break;
		case AudioManager.SCO_AUDIO_STATE_CONNECTED:
			state = "SCO_AUDIO_STATE_CONNECTED";
			break;
		case AudioManager.SCO_AUDIO_STATE_ERROR:
			state = "SCO_AUDIO_STATE_ERROR";
			break;
		default:
			state = String.valueOf(scoAudioState);
			break;
		}
		return state;
	}
	
	private static boolean isBluetoothCanUse() {
		if (!BluetoothHelper.isConnectHeadset()) {
			return false;
		}

		BluetoothAdapter adp = BluetoothAdapter.getDefaultAdapter();
		if (adp == null) {
			LogUtil.d(TAG, "dkbt BluetoothAdapter.getDefaultAdapter() == null");
			return false;
		}
		if (!adp.isEnabled()) {
			LogUtil.d(TAG, "dkbt !adp.isEnabled()");
			return false;
		}
		Set<BluetoothDevice> setDev = adp.getBondedDevices();
		if (setDev == null || setDev.size() == 0) {
			LogUtil.d(TAG, "dkbt setDev == null || setDev.size() == 0");
			return false;
		}
		boolean hasBond = false;
		Iterator<BluetoothDevice> devs = setDev.iterator();
		for (Iterator<BluetoothDevice> it = devs; it.hasNext();) {
			BluetoothDevice dev = it.next();
			if (dev.getBondState() == BluetoothDevice.BOND_BONDED) {
				hasBond = true;
				break;
			}
		}
		if (hasBond == false) {
			LogUtil.d(TAG, "dkbt hasBond == false");
			return false;
		}
		return true;
	}

	/**
	 * 蓝牙的打开底层有计数器
	 * @return
	 *
	 * @author garyzhao in 2015-3-30
	 */
	@TargetApi(8)
	public static boolean startBluetooth() {

		AudioManager am = AndroidDemoUtil.getAudioManager();

		if (Integer.valueOf(android.os.Build.VERSION.SDK) >= AndroidDemoUtil.API_LEVEL_8) {
			boolean isBluetoothCanUse = isBluetoothCanUse();
			boolean isBluetoothScoAvailableOffCall = am.isBluetoothScoAvailableOffCall();
			boolean isCalling = PhoneStatusWatcher.isCalling();
			if (!isBluetoothCanUse || !isBluetoothCanUse || isCalling) {
				return false;
			}
			am.startBluetoothSco();
			am.setBluetoothScoOn(true);
			am.registerMediaButtonEventReceiver(sComponentName);
			return true;
		}
		return false;
	}

	/**
	 * 蓝牙的关闭底层有计数器
	 * 
	 *
	 * @author garyzhao in 2015-3-30
	 */
	@TargetApi(8)
	public static void stopBluetooth() {
		if (Integer.valueOf(android.os.Build.VERSION.SDK) >= AndroidDemoUtil.API_LEVEL_8) {

			AudioManager am = AndroidDemoUtil.getAudioManager();
			if (!PhoneStatusWatcher.isCalling()) {
				am.unregisterMediaButtonEventReceiver(sComponentName);
				am.stopBluetoothSco();
			}
		}
	}
	
	@Override
	public void onReciveBroadcast(Context context, Intent intent) {
		if (TextUtils.equals(ACTION_SCO_AUDIO_STATE_UPDATED, intent.getAction())) {
			doCallbacks(OP_CODE_SCO_AUDIO_STATE_UPDATE, intent.getIntExtra(AudioManager.EXTRA_SCO_AUDIO_STATE, -1), 0,
					intent.getAction(), intent);
		} else if (TextUtils.equals(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED, intent.getAction())) {
			doCallbacks(OP_CODE_ACTION_CONNECTION_STATE_CHANGED, intent.getIntExtra(BluetoothHeadset.EXTRA_STATE, -1), 0, intent.getAction(), null);
		} else if (TextUtils.equals(BluetoothHeadset.ACTION_AUDIO_STATE_CHANGED, intent.getAction())) {
			doCallbacks(OP_CODE_ACTION_AUDIO_STATE_CHANGED, intent.getIntExtra(BluetoothHeadset.EXTRA_STATE, -1), 0, intent.getAction(), null);
		}
	}

	public void release() {
		mSCOAudioReceiver.unregister(mContext);
		mAudioManager.unregisterMediaButtonEventReceiver(sComponentName);
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
