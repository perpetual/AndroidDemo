package com.example.androiddemo.activity;


import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.androiddemo.tools.BluetoothHelper;
import com.example.androiddemo.tools.CommonCallbacks;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.BluetoothUtil;

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
	}
	
	private void stopBluetooth() {
		
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
		return "";
	}
	
	@Override
	protected String getBottomButtonText() {
		return "";
	}
	
	@Override
	protected void doTopButtonClick() {
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
	}
	
	@Override
	public void callback(int opCode, int arg1, int arg2, String str, Object object) {
		String tips = "";
		switch (opCode) {
		case BluetoothHelper.OP_CODE_SCO_AUDIO_STATE_UPDATE:
			switch (arg1) {
			case AudioManager.SCO_AUDIO_STATE_DISCONNECTED:
				tips = "SCO_AUDIO_STATE_DISCONNECTED";
				break;
			case AudioManager.SCO_AUDIO_STATE_CONNECTING:
				tips = "SCO_AUDIO_STATE_CONNECTING";
				break;
			case AudioManager.SCO_AUDIO_STATE_CONNECTED:
				tips = "SCO_AUDIO_STATE_CONNECTED";
				break;
			case AudioManager.SCO_AUDIO_STATE_ERROR:
				tips = "SCO_AUDIO_STATE_ERROR";
				break;
			default:
				break;
			}
			updateTextView(TEXT_VIEW_TOP, str + "|" + tips, true);
			break;
		case BluetoothHelper.OP_CODE_BLUETOOTH_SERVICE_CONNECTION_UPDATE:
			tips = str;
			updateTextView(TEXT_VIEW_BOTTOM, tips, true);
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
