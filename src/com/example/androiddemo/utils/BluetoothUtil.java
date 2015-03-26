package com.example.androiddemo.utils;

import java.util.Iterator;
import java.util.Set;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.media.AudioManager;

public class BluetoothUtil {

	private static final String TAG = "BluetoothUtil";

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

	private static boolean isBluetoothCanUse() {
		if (!isConnectHeadset()) {
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

	@TargetApi(8)
	public static boolean startBluetooth(AudioManager am) {
		if (Integer.valueOf(android.os.Build.VERSION.SDK) >= AndroidDemoUtil.API_LEVEL_8) {
			if (!isBluetoothCanUse() || !am.isBluetoothScoAvailableOffCall() || PhoneStatusWatcher.isCalling()) {
				return false;
			}
			am.startBluetoothSco();
			am.setBluetoothScoOn(true);
			return true;
		}
		return false;
	}

	@TargetApi(8)
	public static void stopBluetooth(AudioManager am) {
		if (Integer.valueOf(android.os.Build.VERSION.SDK) >= AndroidDemoUtil.API_LEVEL_8) {

			if (!PhoneStatusWatcher.isCalling()) {
				am.stopBluetoothSco();
			}
		}
	}
}