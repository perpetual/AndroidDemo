package com.example.androiddemo.activity;

import android.content.Context;
import android.util.AttributeSet;

import com.example.androiddemo.model.OperationCode;
import com.example.androiddemo.tools.AccelerometerManager;
import com.example.androiddemo.tools.CommonCallbacks.ICallback;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * Gary		2015-4-25		Create		
 * </pre>
 */
public class AccelerometerActivity extends SuperActivity implements ICallback{

	@Override
	public void initData(Context context, AttributeSet attrs) {
		super.initData(context, attrs);
		AccelerometerManager.getInstance().add(this);
	}

	@Override
	public void callback(int opCode, int arg1, int arg2, String str,
			Object object) {
		switch (opCode) {
		case OperationCode.OP_CODE_SENSOR_STATE_CHANGED:
			updateTextView(TEXT_VIEW_TOP, str, false);
			break;
		case OperationCode.OP_CODE_ACCURACY_CHANGED_CHANGED:
			updateTextView(TEXT_VIEW_BOTTOM, str, true);
			break;
		case OperationCode.OP_CODE_SHAKE:
			updateTextView(TEXT_VIEW_BOTTOM, str, true);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected String getLeftButtonText() {
		return "start";
	}
	
	@Override
	protected void doLeftButtonClick() {
		AccelerometerManager.getInstance().start();
	}
	
	@Override
	protected String getRightButtonText() {
		return "stop";
	}
	
	@Override
	protected void doRightButtonClick() {
		AccelerometerManager.getInstance().stop();
	}
	
	@Override
	protected String getBottomButtonText() {
		return "clear";
	}
	
	@Override
	protected void doBotttomButtonClick() {
		updateTextView(TEXT_VIEW_BOTTOM, "", false);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		AccelerometerManager.getInstance().start();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		AccelerometerManager.getInstance().stop();
	}
}

