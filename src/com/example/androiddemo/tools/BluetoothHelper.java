package com.example.androiddemo.tools;

import com.example.androiddemo.model.SingleInstance;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-3-26		Create		
 * </pre>
 */
public class BluetoothHelper extends SingleInstance<BluetoothHelper> {

	private BluetoothHelper() {}
	
	@Override
	protected BluetoothHelper newInstance() {
		return new BluetoothHelper();
	}
}

