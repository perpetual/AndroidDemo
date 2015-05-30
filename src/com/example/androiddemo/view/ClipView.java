package com.example.androiddemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

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
	
	public ClipView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setMaskDrawable(Drawable drawable) {
		mMaskDrawable = drawable;
		invalidate();
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
}

