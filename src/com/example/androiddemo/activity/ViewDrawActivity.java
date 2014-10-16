package com.example.androiddemo.activity;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.androiddemo.R;
import com.example.androiddemo.utils.AndroidUtils;

/**
 * <pre>
 * Copyright (C) 1998-2014 TENCENT Inc.All Rights Reserved.
 * 
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * Gary		2014-10-15		Create
 * </pre>
 */
public class ViewDrawActivity extends BaseActivity {

	@Override
	public void initLayout() {
//		setContentView(R.layout.view_draw_layout);
		ViewGroup decorView = (ViewGroup)getWindow().getDecorView();
		LayoutInflater.from(this).inflate(R.layout.view_draw_layout, decorView);
	}

	@Override
	public void initView() {
		AndroidUtils.logChildView((ViewGroup)getWindow().getDecorView());
//		AndroidUtils.logChildView(LayoutInflater.from(this).inflate(R.layout.view_draw_layout, null));
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	/**
	 * 私有工具函数
	 */
}
