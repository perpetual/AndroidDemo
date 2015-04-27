package com.example.androiddemo.activity;



/**
 * <pre>
 * Copyright (C) 1998-2014 TENCENT Inc.All Rights Reserved.
 * 
 * Description
 * 
 * History��
 * 
 * User				Date			Info		Reason
 * Gary		2014-10-15		Create
 * </pre>
 */
public class ViewDrawActivity extends SuperActivity {

	@Override
	protected String getLeftButtonText() {
		return "透明";
	}
	
	@Override
	protected String getRightButtonText() {
		return "恢复";
	}
	
	@Override
	protected void doLeftButtonClick() {
		getCustomView().setAlpha(0.5f);
	}
	
	@Override
	protected void doRightButtonClick() {
		getCustomView().setAlpha(1.f);
	}
}
