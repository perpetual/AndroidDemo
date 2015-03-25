package com.example.androiddemo.activity;

import com.example.androiddemo.R;


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
	protected String getLogTag() {
		return getClass().getSimpleName();
	}
	
	@Override
	public void initLayout() {
		setContentView(R.layout.view_draw_layout);
	}

	@Override
	public void initView() {
		super.initView();
	}
}
