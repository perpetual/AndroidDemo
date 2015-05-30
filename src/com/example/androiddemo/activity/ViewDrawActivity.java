package com.example.androiddemo.activity;

import com.example.androiddemo.R;
import com.example.androiddemo.view.ClipView;

import android.graphics.drawable.Drawable;



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
public class ViewDrawActivity extends DemoSuperActivity {

	private ClipView mClipView = null;
	
	
	@Override
	public void initView() {
		super.initView();
	}
	
	@Override
	public void bindView() {
		super.bindView();
		mClipView = (ClipView) findViewById(R.id.clip_view);
	}
	
	@Override
	protected String getLeftButtonText() {
		return "透明";
	}
	
	@Override
	protected String getRightButtonText() {
		return "恢复";
	}
	
	@Override
	protected String getTopButtonText() {
		return "mask";
	}
	
	@Override
	protected String getBottomButtonText() {
		return "unmask";
	}
	
	@Override
	protected void doLeftButtonClick() {
		getCustomView().setAlpha(0.5f);
	}
	
	@Override
	protected void doRightButtonClick() {
		getCustomView().setAlpha(1.f);
	}
	
	@Override
	protected void doTopButtonClick() {
		mClipView.setMaskDrawable(getResources().getDrawable(R.drawable.circle_mask_shap));
	}
	
	@Override
	protected void doBotttomButtonClick() {
		mClipView.setMaskDrawable(null);
	}
	
	@Override
	protected int getCustomViewAreaLayoutResource() {
		return R.layout.common_clip_view_layout;
	}
}
