package com.example.androiddemo.view;

import java.util.Arrays;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
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
	private static final String TAG = ClipView.class.getSimpleName();
	private Drawable mMaskDrawable = null;
	private final Paint mBitmapPaint = new Paint();
	private final Paint mMaskPaint = new Paint();
	private Bitmap mNormalMaskBitmap = null;
	private Bitmap mPressMaskBitmap = null;
	private Bitmap mBitmap = null;
	private Canvas mTempCanvas = null;
    private BitmapShader mBitmapShader = null;
    private BitmapShader mNormalBitmapShader = null;
    private BitmapShader mPressBitmapShader = null;
	private final int[] PRESSED_STATE = View.PRESSED_ENABLED_STATE_SET;
	
	public ClipView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setMaskDrawable(Drawable drawable) {
		if (drawable == mMaskDrawable) {
			return;
		}
		mMaskDrawable = drawable;
		LogUtil.d(TAG, "setMaskDrawable", drawable);
		mNormalMaskBitmap = getBitmapFromDrawable(drawable);
		drawable.setState(PRESSED_STATE);
		mPressMaskBitmap = getBitmapFromDrawable(drawable);
		if (null != mNormalMaskBitmap) {
			mNormalBitmapShader = new BitmapShader(mNormalMaskBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		}
		if (null != mPressMaskBitmap) {
			mPressBitmapShader = new BitmapShader(mPressMaskBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		}
		invalidate();
	}
	
	private Canvas getCanvas(Bitmap bitmap) {
		if (null == mTempCanvas) {
			mTempCanvas = new Canvas();
		}
		if (null != bitmap) {
			mTempCanvas.setBitmap(bitmap);
		}
		return mTempCanvas;
	}
	
	private int minValue(int srcValue, final int minValue) {
		if (srcValue < minValue) {
			if (getWidth() > 0) {
				srcValue = getWidth();
			} else {
				srcValue = 1;
			}
		}
		return srcValue;
	}
	
	private Bitmap getBitmapFromDrawable(Drawable drawable) {
		if (null == drawable) {
			return null;
		}
		Bitmap bitmap = null;

		int width = drawable.getIntrinsicWidth();
		if (width < 1) {
			if (getWidth() > 0) {
				width = getWidth();
			} else {
				width = 1;
			}
		}
		int height = drawable.getIntrinsicHeight();
		if (height < 1) {
			if (getHeight() > 0) {
				height = getHeight();
			} else {
				height = 1;
			}
		}

		LogUtil.d(TAG, "getBitmapFromDrawable", width, height);
		drawable.setBounds(0, 0, width, height);
		bitmap = Bitmap.createBitmap(width, height,
				drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
						: Bitmap.Config.RGB_565);
		drawable.draw(getCanvas(bitmap));
		return bitmap;
	}

	
	@Override
	public void initData(Context context, AttributeSet attrs) {
		super.initData(context, attrs);
		LogUtil.d(TAG, "initData", mBitmapPaint);
	}
	
	@Override
	public void initView() {
		super.initView();
		setClickable(true);
	}
	
	@Override
	public void updateView() {
		super.updateView();
		setImageResource(R.drawable.red);
	}
	
	@Override
	public void setImageResource(int resId) {
		mBitmap = getBitmapFromDrawable(getResources().getDrawable(resId));
		if (null != mBitmap) {
			mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		}
		invalidate();
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
		if (null != mBitmapShader) {
			mBitmapPaint.setShader(mBitmapShader);
			canvas.drawRect(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), mBitmapPaint);
		}
		if (!StateSet.stateSetMatches(PRESSED_STATE, getDrawableState()) && null != mNormalBitmapShader) {
			mMaskPaint.setShader(mNormalBitmapShader);
			canvas.drawRect(0, 0, mNormalMaskBitmap.getWidth(), mNormalMaskBitmap.getHeight(), mMaskPaint);
		} else if (StateSet.stateSetMatches(PRESSED_STATE, getDrawableState()) && null != mPressBitmapShader) {
			mMaskPaint.setShader(mPressBitmapShader);
			canvas.drawRect(0, 0, mPressMaskBitmap.getWidth(), mPressMaskBitmap.getHeight(), mMaskPaint);
		}				
	}
	
	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		LogUtil.d(TAG, Arrays.toString(getDrawableState()), Arrays.toString(PRESSED_STATE));
		invalidate();
	}
}

