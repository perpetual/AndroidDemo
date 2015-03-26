package com.example.androiddemo.activity;

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
}
