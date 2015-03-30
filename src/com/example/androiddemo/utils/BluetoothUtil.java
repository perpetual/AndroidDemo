package com.example.androiddemo.utils;

import java.util.Iterator;
import java.util.Set;

import android.annotation.TargetApi;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.media.AudioManager;

import com.example.androiddemo.tools.BluetoothHelper;
import com.example.androiddemo.tools.PhoneStatusWatcher;

public class BluetoothUtil {

	private static final String TAG = "BluetoothUtil";

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

	@TargetApi(8)
	public static boolean startBluetooth(AudioManager am) {
		if (null == am) {
			am = (AudioManager)AndroidDemoUtil.APPLICATION_CONTEXT.getSystemService(Context.AUDIO_SERVICE);
		}
		if (Integer.valueOf(android.os.Build.VERSION.SDK) >= AndroidDemoUtil.API_LEVEL_8) {
			boolean isBluetoothCanUse = isBluetoothCanUse();
			boolean isBluetoothScoAvailableOffCall = am.isBluetoothScoAvailableOffCall();
			boolean isCalling = PhoneStatusWatcher.isCalling();
			if (!isBluetoothCanUse || !isBluetoothCanUse || isCalling) {
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

			if (null == am) {
				am = (AudioManager)AndroidDemoUtil.APPLICATION_CONTEXT.getSystemService(Context.AUDIO_SERVICE);
			}
			if (!PhoneStatusWatcher.isCalling()) {
				am.stopBluetoothSco();
			}
		}
	}
}
