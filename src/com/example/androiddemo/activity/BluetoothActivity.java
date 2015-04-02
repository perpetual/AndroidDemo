package com.example.androiddemo.activity;


import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.util.AttributeSet;

import com.example.androiddemo.tools.BluetoothHelper;
import com.example.androiddemo.tools.CommonCallbacks;
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
public class BluetoothActivity extends SuperActivity implements CommonCallbacks.ICallback {

	BluetoothHelper mBluetoothHelper = null;
	
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
	}
	
	@Override
	public void initView() {
		super.initView();
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
	public void callback(int opCode, int arg1, int arg2, String str, Object object) {
		switch (opCode) {
		case BluetoothHelper.OP_CODE_SCO_AUDIO_STATE_UPDATE:
			updateTextView(TEXT_VIEW_LEFT, str + "|" + BluetoothHelper.getScoAudioState(arg1), true);
			break;
		case BluetoothHelper.OP_CODE_BLUETOOTH_SERVICE_CONNECTION_UPDATE:
			updateTextView(TEXT_VIEW_TOP, str, true);
			break;
		case BluetoothHelper.OP_CODE_ACTION_CONNECTION_STATE_CHANGED:
			updateTextView(TEXT_VIEW_BOTTOM, str + "|" + BluetoothHelper.getConnectState(), true);
			break;
		case BluetoothHelper.OP_CODE_ACTION_AUDIO_STATE_CHANGED:
			updateTextView(TEXT_VIEW_RIGHT, str + "|" + BluetoothHelper.getAudioConnectState(arg1), true);
			break;
		case BluetoothHelper.OP_CODE_ACL_CONNECTION_UPDATE:
			updateTextView(TEXT_VIEW_BOTTOM, str + "|" + object, true);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mBluetoothHelper.release();
	}
}
