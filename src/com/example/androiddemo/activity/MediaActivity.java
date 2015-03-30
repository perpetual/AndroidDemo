package com.example.androiddemo.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;

import com.example.androiddemo.receiver.BaseBroadcastReceiver;
import com.example.androiddemo.tools.BluetoothHelper;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;

/**
 * <pre>
 * Copyright (C) 1998-2014 TENCENT Inc.All Rights Reserved.
 *
 * Description�?
 * 
 * History�?
 * 
 * User				Date			Info		Reason
 * Gary		2014-12-2		Create		
 * </pre>
 */
public class MediaActivity extends SuperActivity implements BaseBroadcastReceiver.IBaseBroadcastReceiver, AudioManager.OnAudioFocusChangeListener{
	private static final String TAG = AndroidDemoUtil.getClassName(MediaActivity.class);
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
		BluetoothHelper.startBluetooth();
		mAudioManager.registerMediaButtonEventReceiver(new ComponentName(this, BaseBroadcastReceiver.class));
	}
	
	@Override
	public void initLayout() {
		setContentView(0);
	}
	
	@Override
	public void onReciveBroadcast(Context context, Intent intent) {
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
		BluetoothHelper.stopBluetooth();
		AndroidDemoUtil.resumeMusic(this);
	}

	@Override
	public void onAudioFocusChange(int focusChange) {
		Log.d(TAG, "onAudioFocusChange:" + focusChange);
	}
}

