package com.example.androiddemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.widget.LinearLayout;

import com.example.androiddemo.utils.AndroidUtils;
import com.example.androiddemo.utils.LogUtil;

/**
 * <pre>
 * Copyright (C) 1998-2014 TENCENT Inc.All Rights Reserved.
 * 
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * Gary		2014-10-28		Create
 * </pre>
 */
public class CustomViewGroup extends LinearLayout implements OnLayoutChangeListener {

	private static final String TAG = "xxx";

	public CustomViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		addOnLayoutChangeListener(this);
	}

	@Override
	protected void onFinishInflate() {
		LogUtil.d(TAG, AndroidUtils.getClassName(this)
				+ "|onFinishInflate|getHeight:" + getHeight());
		super.onFinishInflate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		LogUtil.d(TAG, AndroidUtils.getClassName(this)
				+ "|onMeasure|widthMeasureSpec:" + widthMeasureSpec
				+ "|heightMeasureSpec:" + heightMeasureSpec + "|getHeight:"
				+ getHeight());
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void measureChildWithMargins(View child,
			int parentWidthMeasureSpec, int widthUsed,
			int parentHeightMeasureSpec, int heightUsed) {
		LogUtil.d(
				TAG,
				AndroidUtils.getClassName(this)
						+ "|measureChildWithMargins|child:"
						+ AndroidUtils.getClassName(child.getClass())
						+ "|parentWidthMeasureSpec:" + parentWidthMeasureSpec
						+ "|widthUsed:" + widthUsed
						+ "|parentHeightMeasureSpec:" + parentHeightMeasureSpec
						+ "|heightUsed:" + heightUsed + "|getHeight:"
						+ getHeight());
		super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed,
				parentHeightMeasureSpec, heightUsed);
	}

	@Override
	protected void measureChild(View child, int parentWidthMeasureSpec,
			int parentHeightMeasureSpec) {
		LogUtil.d(TAG, AndroidUtils.getClassName(this) + "|measureChild|child:"
				+ AndroidUtils.getClassName(child.getClass())
				+ "|parentWidthMeasureSpec:" + parentWidthMeasureSpec
				+ "|parentHeightMeasureSpec:" + parentHeightMeasureSpec
				+ getHeight());
		super.measureChild(child, parentWidthMeasureSpec,
				parentHeightMeasureSpec);
	}

	@Override
	protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
		LogUtil.d(TAG, AndroidUtils.getClassName(this)
				+ "|measureChildren|widthMeasureSpec:" + widthMeasureSpec
				+ "|heightMeasureSpec:" + heightMeasureSpec + "|getHeight:"
				+ getHeight());
		super.measureChildren(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		LogUtil.d(TAG, AndroidUtils.getClassName(this) + "|onLayout|changed:"
				+ changed + "|l:" + l + "|t:" + t + "|r:" + r + "|b:" + b
				+ "|getHeight:" + getHeight());
		super.onLayout(changed, l, t, r, b);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		LogUtil.d(TAG, AndroidUtils.getClassName(this) + "|onDraw|getHeight:"
				+ getHeight());
		super.onDraw(canvas);
	}

	@Override
	public void onLayoutChange(View v, int left, int top, int right,
			int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
		LogUtil.d(TAG, AndroidUtils.getClassName(this) + "|onLayoutChange:v:"
				+ AndroidUtils.getClassName(v) + "|left:" + left + "|top:"
				+ top + "|right:" + right + "|bottom:" + bottom + "|getHeight:"
				+ getHeight());
		
	}
}
