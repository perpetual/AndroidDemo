package com.example.androiddemo.tools;

import java.io.FileDescriptor;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.widget.MediaController.MediaPlayerControl;

import com.example.androiddemo.R;
import com.example.androiddemo.model.OperationCode;
import com.example.androiddemo.receiver.BaseBroadcastReceiver;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;
import com.example.androiddemo.utils.SystemServiceUtil;

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
public class MediaManager extends CommonCallbacks implements
		BaseBroadcastReceiver.IBaseBroadcastReceiver, MediaPlayer.OnPreparedListener,
		MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener,
		MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayerControl {
	
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
		
		initPlayer(R.raw.canon, AudioManager.STREAM_MUSIC);
	}
	
	public void release() {
		removeAll();
		mBBR.unregister(mContext);
		mMediaPlayer.release();
	}
	
	public void initPlayer(int mediaResourceID, int streamType) {
		AssetFileDescriptor afd = mContext.getResources().openRawResourceFd(mediaResourceID);
		if (null == afd) {
			return;
		}
		try {
			mMediaPlayer = new MediaPlayer();
			FileDescriptor fd = afd.getFileDescriptor();
			mMediaPlayer.setDataSource(fd, afd.getStartOffset(), afd.getLength());
			mStreamType = streamType;
			mMediaPlayer.setAudioStreamType(mStreamType);
			mMediaPlayer.setOnPreparedListener(this);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setOnErrorListener(this);
			mMediaPlayer.setOnInfoListener(this);
			mMediaPlayer.setOnSeekCompleteListener(this);
			mMediaPlayer.prepare();
		} catch (Exception e) {
			LogUtil.e(TAG, e.toString());
		}
	}
	
	public void stopPlayer() {
		if (mMediaPlayer.isPlaying()) {
			mMediaPlayer.stop();
		}
	}
	
	public int getStreamType() {
		return mStreamType;
	}
	
	public void volumeUp() {
		SystemServiceUtil.getAudioManager()
				.adjustStreamVolume(getStreamType(), AudioManager.ADJUST_RAISE,
						AudioManager.FLAG_SHOW_UI | AudioManager.FLAG_PLAY_SOUND);
	}
	
	public void volumeDown() {
		SystemServiceUtil.getAudioManager()
				.adjustStreamVolume(getStreamType(), AudioManager.ADJUST_LOWER,
						AudioManager.FLAG_SHOW_UI | AudioManager.FLAG_PLAY_SOUND);
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
		doCallbacks(-1, 0, 0, "MediaPlayer", null);
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		doCallbacks(-1, 0, 0, "onCompletion", null);
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		doCallbacks(-1, 0, 0, "onBufferingUpdate", null);
	}

	@Override
	public void onSeekComplete(MediaPlayer mp) {
		doCallbacks(-1, 0, 0, "onSeekComplete", null);
	}

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		doCallbacks(-1, 0, 0, "onInfo", AndroidDemoUtil.converIndeterminateArgumentsToString("what", what, "extra", extra));
		return false;
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		doCallbacks(-1, 0, 0, "onError", AndroidDemoUtil.converIndeterminateArgumentsToString("what", what, "extra", extra));
		return false;
	}

	@Override
	public void start() {
		if (null != mMediaPlayer && !mMediaPlayer.isPlaying()) {
			mMediaPlayer.start();	
		}
	}

	@Override
	public void pause() {
		if (mMediaPlayer.isPlaying()) {
			mMediaPlayer.pause();
		}
	}

	@Override
	public int getDuration() {
		if (null == mMediaPlayer) {
			return 0;
		}
		return mMediaPlayer.getDuration();
	}

	@Override
	public int getCurrentPosition() {
		if (null == mMediaPlayer) {
			return 0;
		}
		int pos = mMediaPlayer.getCurrentPosition();
		LogUtil.d(TAG, "getCurrentPosition", pos);
		return pos;
	}

	@Override
	public void seekTo(int pos) {
		mMediaPlayer.seekTo(pos);
	}

	@Override
	public boolean isPlaying() {
		if (null == mMediaPlayer) {
			return false;
		}
		return mMediaPlayer.isPlaying();
	}

	@Override
	public int getBufferPercentage() {
		return 0;
	}

	@Override
	public boolean canPause() {
		return true;
	}

	@Override
	public boolean canSeekBackward() {
		return true;
	}

	@Override
	public boolean canSeekForward() {
		return true;
	}

	@Override
	public int getAudioSessionId() {
		if (null == mMediaPlayer) {
			return 0;
		}
		return mMediaPlayer.getAudioSessionId();
	}
}

