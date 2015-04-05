package com.example.androiddemo.activity;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.MediaController;

import com.example.androiddemo.R;
import com.example.androiddemo.model.OperationCode;
import com.example.androiddemo.tools.BluetoothHelper;
import com.example.androiddemo.tools.CommonCallbacks;
import com.example.androiddemo.tools.MediaManager;
import com.example.androiddemo.utils.AndroidDemoUtil;

/**
 * 
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-3-25		Create		
 * </pre>
 */
public class MediaActivity extends SuperActivity implements CommonCallbacks.ICallback {

	BluetoothHelper mBluetoothHelper = null;
	MediaManager mMediaManager = null;
	MediaController mMediaController = null;
	
	/**
	 * 私有工具函数
	 */	
	private void startBluetooth() {
		boolean isBluetoothEnabled = BluetoothAdapter.getDefaultAdapter().isEnabled();
		int bluetoothState = BluetoothAdapter.getDefaultAdapter().getState();
		boolean isBluetoothScoAvailableOffCall = AndroidDemoUtil.getAudioManager()
				.isBluetoothScoAvailableOffCall();
		updateTextView(TEXT_VIEW_TOP, AndroidDemoUtil.converIndeterminateArgumentsToString(
				"isEnabled", isBluetoothEnabled, "bluetoothState", bluetoothState, "isBluetoothScoAvailableOffCall",
				isBluetoothScoAvailableOffCall), true);
		BluetoothHelper.startBluetoothSCO();
	}
	
	private void stopBluetooth() {
		BluetoothHelper.stopBluetoothSCO();
	}
	
	@Override
	public void initData(Context context, AttributeSet attrs) {
		super.initData(context, attrs);
		mBluetoothHelper = new BluetoothHelper(context);
		mBluetoothHelper.add(this);
		mMediaManager = new MediaManager(this);
		mMediaManager.add(this);
		mMediaController = new MediaController(context);
	}
	
	@Override
	public void initView() {
		super.initView();
		mMediaController.setMediaPlayer(mMediaManager);
//		mMediaController.setAnchorView(getMainView());
		mMediaController.show();
	}
	
	@Override
	protected String getLeftButtonText() {
		return "start bluetooth";
	}
	
	@Override
	protected String getRightButtonText() {
		return "stop bluetooth";
	}
	
	@Override
	protected String getTopButtonText() {
		return "clear";
	}
	
	@Override
	protected String getBottomButtonText() {
		return "clear";
	}

	@Override
	protected String getButton1Text() {
		return "start player";
	}

	@Override
	protected String getButton2Text() {
		return "pause player";
	}

	@Override
	protected String getButton3Text() {
		return "restart player";
	}

	@Override
	protected String getButton4Text() {
		return "stop player";
	}
	
	@Override
	protected void doTopButtonClick() {
		updateTextView(TEXT_VIEW_TOP, "", false);
	}
	
	@Override
	protected void doLeftButtonClick() {
		startBluetooth();
	}
	
	@Override
	protected void doRightButtonClick() {
		stopBluetooth();
	}

	@Override
	protected void doBotttomButtonClick() {
		updateTextView(TEXT_VIEW_BOTTOM, "", false);
	}
	
	@Override
	protected void doButton1Click() {
		mMediaManager.start();
	}
	
	@Override
	protected void doButton2Click() {
		mMediaManager.pause();
	}
	
	@Override
	protected void doButton3Click() {
	}
	
	@Override
	protected void doButton4Click() {
		mMediaManager.stopPlayer();
	}
	
	@Override
	protected boolean canUseOperationLayout() {
		return true;
	}
	
	@Override
	public void callback(int opCode, int arg1, int arg2, String str, Object object) {
		switch (opCode) {
		case OperationCode.OP_CODE_SCO_AUDIO_STATE_UPDATE:
			updateTextView(TEXT_VIEW_LEFT, str + "|" + BluetoothHelper.getScoAudioConnectionState(arg1), true);
			break;
		case OperationCode.OP_CODE_BLUETOOTH_SERVICE_CONNECTION_UPDATE:
			updateTextView(TEXT_VIEW_TOP, str, true);
			break;
		case OperationCode.OP_CODE_ACTION_HEADSET_CONNECTION_STATE_UPDATE:
			updateTextView(TEXT_VIEW_BOTTOM, str + "|" + BluetoothHelper.getHeadsetConnectState(arg1), true);
			break;
		case OperationCode.OP_CODE_ACTION_A2DP_CONNECTION_STATE_UPDATE:
			updateTextView(TEXT_VIEW_RIGHT, str + "|" + BluetoothHelper.getA2DPConnectState(arg1), true);
			break;
		case OperationCode.OP_CODE_ACL_CONNECTION_STATE_UPDATE:
			updateTextView(TEXT_VIEW_BOTTOM, str + "|" + BluetoothHelper.getBluetoothDeviceInfo((BluetoothDevice)object), true);
			break;
		case OperationCode.OP_CODE_AUDIO_BECOMING_NOISY:
			updateTextView(TEXT_VIEW_LEFT, str + "|" + object, true);
			break;
		case OperationCode.OP_CODE_ACTION_HEADSET_PLUG:
			Intent intent = (Intent)object;
			updateTextView(TEXT_VIEW_TOP, str + "|" + AndroidDemoUtil.converIndeterminateArgumentsToString("state",
					intent.getIntExtra("state", -1), "name", intent.getStringExtra("name"), "microphone",
					intent.getIntExtra("microphone", -1)), true);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mBluetoothHelper.release();
		mMediaManager.release();
	}
	
	@Override
	protected String getLogTag() {
		return MediaActivity.class.getSimpleName();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		updateTextView(TEXT_VIEW_BOTTOM, "keyCode:" + keyCode + "|event:" + event.toString(), true);
		return super.onKeyDown(keyCode, event);
	}
}
