package com.example.androiddemo.activity;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.androiddemo.R;

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
		setContentView(R.layout.view_draw_layout);
	}

	@Override
	public void initView() {
		logChildView((ViewGroup)getWindow().getDecorView(), 0);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	/**
	 * 私有工具函数
	 */
	private void logChildView(ViewGroup viewGroup, int level) {
		if (null == viewGroup) {
			return;
		}
		for (int i = 0; i < viewGroup.getChildCount(); ++i) {
			View childView = viewGroup.getChildAt(i);
			if (childView instanceof ViewGroup) {
				logChildView((ViewGroup) childView, level + 1);
				String className = childView.getClass().getName();
				int viewId = childView.getId();
				Resources resources = getResources();
				
				String resourceName = "";
				if (viewId > 0) {
					resourceName = resources.getResourceName(viewId) + "|" + resources.getResourceEntryName(viewId);
					Log.w("xxx", resourceName + "|level:" + level);
				}
			}
		}
	}
}
