package com.example.androiddemo.activity;

import android.content.Context;
import android.util.AttributeSet;

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
public class BluetoothActivity extends SuperActivity {

	/**
	 * 私有工具函数
	 */
	private void startBluetooth() {
	}
	
	private void stopBluetooth() {
		
	}
	
	private void showBluetooth() {
		
	}
	
	@Override
	public void initData(Context context, AttributeSet attrs) {
		super.initData(context, attrs);
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
		return "bluetooth device";
	}
	
	@Override
	protected String getBottomButtonText() {
		return "";
	}
	
	@Override
	protected void doTopButtonClick() {
		showBluetooth();
	}
	
	@Override
	protected void doLeftButtonClick() {
		startBluetooth();
	}
	
	@Override
	protected void doRightButtonClick() {
		stopBluetooth();
	}
}
