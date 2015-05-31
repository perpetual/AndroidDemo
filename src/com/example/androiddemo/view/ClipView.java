package com.example.androiddemo.view;

import java.util.Arrays;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.View;

import com.example.androiddemo.R;
import com.example.androiddemo.utils.LogUtil;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-5-30		Create		
 * </pre>
 */
public class ClipView extends CustomView {

	private Drawable mMaskDrawable = null;
	private Paint mPaint = null;
	
	public ClipView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setMaskDrawable(Drawable drawable) {
		mMaskDrawable = drawable;
		invalidate();
	}
	
	@Override
	public void initData(Context context, AttributeSet attrs) {
		super.initData(context, attrs);
		mPaint = new Paint();
	}
	
	@Override
	public void initView() {
		super.initView();
		setImageResource(R.drawable.resource);
//		setBackgroundResource(R.drawable.ic_launcher);
		setClickable(true);
	}
	
	@Override
	public void setImageResource(int resId) {
		super.setImageResource(resId);
	}
	
	@Override
	public void setBackgroundResource(int resid) {
		super.setBackgroundResource(resid);
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (null != mMaskDrawable) {
			mMaskDrawable.setBounds(0, 0, getWidth(), getHeight());
			mMaskDrawable.setState(getDrawableState());
			mMaskDrawable.draw(canvas);
		}
	}
	
	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		LogUtil.d("xxx", Arrays.toString(getDrawableState()), Arrays.toString(View.PRESSED_STATE_SET));
		if (StateSet.stateSetMatches(View.PRESSED_STATE_SET, getDrawableState())) {
			setBackgroundResource(R.color.blue);
		} else {
			setBackgroundResource(R.color.green);
		}
		invalidate();
	}
}

