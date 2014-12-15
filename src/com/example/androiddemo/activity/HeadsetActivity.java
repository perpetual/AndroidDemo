package com.example.androiddemo.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;

import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.BaseBroadcastReceiver;
import com.example.androiddemo.utils.BluetoothUtil;
import com.example.androiddemo.utils.LogUtil;

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
public class HeadsetActivity extends BaseActivity implements BaseBroadcastReceiver.IBaseBroadcastReceiver, AudioManager.OnAudioFocusChangeListener{
	private static final String TAG = "HeadsetActivity";
	BaseBroadcastReceiver mBaseBroadcastReceiver = null;
	AudioManager mAudioManager = null;
	
	
	@Override
	public void initData(Context context, AttributeSet attrs) {
		mBaseBroadcastReceiver = new BaseBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_MEDIA_BUTTON);
		intentFilter.setPriority(100);
		mBaseBroadcastReceiver.register(context, intentFilter, this);
		mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		AndroidDemoUtil.pauseMusic(this);
		BluetoothUtil.startBluetooth(mAudioManager);
		mAudioManager.registerMediaButtonEventReceiver(new ComponentName(this, BaseBroadcastReceiver.class));
	}
	
	@Override
	public void onRecive(Context context, Intent intent) {
		LogUtil.d(TAG, intent.toString());
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		LogUtil.d(TAG, "onKeyUp:" + keyCode  + "|event:" + event);
		return super.onKeyUp(keyCode, event);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		LogUtil.d(TAG, "onKeyDown:" + keyCode  + "|event:" + event);
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		BluetoothUtil.stopBluetooth(mAudioManager);
		AndroidDemoUtil.resumeMusic(this);
	}

	@Override
	public void onAudioFocusChange(int focusChange) {
		Log.d("xxx", "onAudioFocusChange:" + focusChange);
	}
}

