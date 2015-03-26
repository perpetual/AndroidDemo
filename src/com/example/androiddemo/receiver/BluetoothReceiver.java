package com.example.androiddemo.receiver;


import java.util.Set;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.text.TextUtils;

import com.example.androiddemo.utils.LogUtil;
import com.example.androiddemo.utils.TextUtil;


public class BluetoothReceiver extends BroadcastReceiver {
	private static final String TAG = "BluetoothReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (context == null || intent == null || TextUtils.isEmpty(intent.getAction())) {
			return;
		}
		LogUtil.d("xxx", "BluetoothReceiver:onReceive:intent:" + intent);
		BluetoothAdapter adp = BluetoothAdapter.getDefaultAdapter();
		if (adp == null) {
			LogUtil.d(TAG, "getDefaultAdapter == null");
			return;
		}
		Set<BluetoothDevice> setDev = adp.getBondedDevices();
		if (setDev == null || setDev.size() == 0) {
			LogUtil.d(TAG, "getBondedDevices == null");
			return;
		}

		String action = intent.getAction();
		if (TextUtils.isEmpty(action)) {
			LogUtil.d(TAG, "getAction == null");
			return;
		}
		int state = intent.getIntExtra(AudioManager.EXTRA_SCO_AUDIO_STATE, -1);

		if (state == AudioManager.SCO_AUDIO_STATE_CONNECTED) {			
		} else if (state == AudioManager.SCO_AUDIO_STATE_DISCONNECTED ) {
		}
	}
}
