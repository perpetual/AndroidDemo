package com.example.androiddemo.utils;

import android.annotation.TargetApi;
import android.media.AudioManager;


public class BluetoothUtil {
	
	
	private static final int ANDROID_API_LEVEL_8 = 8;
	
	@TargetApi(8)
	public static boolean startBluetooth(AudioManager am){
		if (Integer.valueOf(android.os.Build.VERSION.SDK) >= ANDROID_API_LEVEL_8) {
			if(!am.isBluetoothScoAvailableOffCall() || PhoneStatusWatcher.isCalling()){
				return false;
			}
			am.startBluetoothSco();
			am.setBluetoothScoOn(true);
			return true;
		}
		return false;
	}
	
	@TargetApi(8)
	public static void stopBluetooth(AudioManager am){
		if (Integer.valueOf(android.os.Build.VERSION.SDK) >= ANDROID_API_LEVEL_8) {
			
			if(!PhoneStatusWatcher.isCalling()){
				am.stopBluetoothSco();
			}
		}
	}
}
