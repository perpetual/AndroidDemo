package com.example.androiddemo.activity;


import android.content.Context;
import android.content.Intent;
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
		mBluetoothHelper.register();
	}
	
	private void stopBluetooth() {
		
	}
	
	private void clear() {
		updateTextView(TEXT_VIEW_TOP, "", false);
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
		return "";
	}
	
	@Override
	protected void doTopButtonClick() {
		clear();
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
	public void callback(int opCode, int arg1, int arg2, String str, Object object) {
		if (object instanceof Intent) {
			Intent intent = (Intent)object;
			updateTextView(TEXT_VIEW_TOP, object.toString() + intent.getExtras().toString(), false);
		}
	}
}
