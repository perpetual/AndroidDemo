package com.example.androiddemo.tools;

import java.io.FileDescriptor;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;

import com.example.androiddemo.model.OperationCode;
import com.example.androiddemo.receiver.BaseBroadcastReceiver;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-4-4		Create		
 * </pre>
 */
public class MediaManager extends CommonCallbacks implements BaseBroadcastReceiver.IBaseBroadcastReceiver, MediaPlayer.OnPreparedListener{
	
	private static final String TAG = MediaManager.class.getSimpleName();
	
	public static final String ACTION_SCO_AUDIO_STATE_UPDATED = AndroidDemoUtil.getSDKVersion() < AndroidDemoUtil.API_LEVEL_14 ? AudioManager.ACTION_SCO_AUDIO_STATE_CHANGED
			: AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED;
	private Context mContext = null;
	private BaseBroadcastReceiver mBBR = null;
	private MediaPlayer mMediaPlayer = null;
	private int mStreamType = AudioManager.STREAM_MUSIC;
	
	public MediaManager(Context context) {
		mContext = context;
		mBBR = new BaseBroadcastReceiver();
		mBBR.register(mContext, AndroidDemoUtil.createIntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY), this);
		/**
		 * 注册蓝牙SCO音频连接状态广播
		 * 注册上就会先回调一次
		 */
		mBBR.register(mContext,
				AndroidDemoUtil.createIntentFilter(ACTION_SCO_AUDIO_STATE_UPDATED), this);
		/**
		 * 注册耳机插拔广播
		 */
		mBBR.register(mContext, AndroidDemoUtil.createIntentFilter(AudioManager.ACTION_HEADSET_PLUG), this);
		
		mMediaPlayer = new MediaPlayer();
	}
	
	public void release() {
		removeAll();
		mBBR.unregister(mContext);
		mMediaPlayer.release();
	}
	
	public void startPlay(int mediaResourceID, int streamType) {
		AssetFileDescriptor afd = mContext.getResources().openRawResourceFd(mediaResourceID);
		if (null == afd) {
			return;
		}
		try {
			FileDescriptor fd = afd.getFileDescriptor();
			mMediaPlayer.setDataSource(fd, afd.getStartOffset(), afd.getLength());
			mStreamType = streamType;
			mMediaPlayer.setAudioStreamType(mStreamType);
			mMediaPlayer.setOnPreparedListener(this);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
		} catch (Exception e) {
			LogUtil.e(TAG, e.toString());
		}
	}
	
	public void restartPlayer() {
	}
	
	public void stopPlayer() {
		if (mMediaPlayer.isPlaying()) {
			mMediaPlayer.stop();
		}
	}
	
	public void pausePlayer() {
		if (mMediaPlayer.isPlaying()) {
			mMediaPlayer.pause();
		}
	}
	
	public int getStreamType() {
		return mStreamType;
	}
	
	@Override
	public void onReciveBroadcast(Context context, Intent intent) {
		if (TextUtils.equals(ACTION_SCO_AUDIO_STATE_UPDATED, intent.getAction())) {
			doCallbacks(OperationCode.OP_CODE_SCO_AUDIO_STATE_UPDATE,
					intent.getIntExtra(AudioManager.EXTRA_SCO_AUDIO_STATE, -1), 0,
					intent.getAction(), null);
		} else if (TextUtils.equals(AudioManager.ACTION_AUDIO_BECOMING_NOISY, intent.getAction())) {
			doCallbacks(OperationCode.OP_CODE_AUDIO_BECOMING_NOISY, 0, 0, intent.getAction(),
					intent.getExtras());
		} else if (TextUtils.equals(AudioManager.ACTION_HEADSET_PLUG, intent.getAction())) {
			doCallbacks(OperationCode.OP_CODE_ACTION_HEADSET_PLUG, 0, 0, intent.getAction(), intent);
		}
	}

	@Override
	public void onPrepared(MediaPlayer mp) {		
	}
}

