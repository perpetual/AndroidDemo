package com.example.androiddemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.widget.ImageView;

import com.example.androiddemo.utils.LogUtil;

public class CustomView extends ImageView implements OnLayoutChangeListener {

	private static final String TAG = CustomView.class.getSimpleName();

	
	public CustomView(Context context) {
		super(context);
		addOnLayoutChangeListener(this);
	}

	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		addOnLayoutChangeListener(this);
	}


	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		LogUtil.d(TAG, getClass().getSimpleName(), "onMeasure", "widthMeasureSpec",
				widthMeasureSpec, "heightMeasureSpec", heightMeasureSpec, "getHeight", getHeight());
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		LogUtil.d(TAG, getClass().getSimpleName(), "onLayout|changed", changed, "left", left,
				"top", top + "right", right, "bottom", bottom + "getHeight", getHeight());
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		LogUtil.d(TAG, getClass().getSimpleName(), "onDraw|getHeight", getHeight());
		super.onDraw(canvas);		
	}

	@Override
	protected void onFinishInflate() {
		LogUtil.d(TAG, getClass().getSimpleName(), "onFinishInflate", "getHeight", getHeight());
		super.onFinishInflate();
	}

	@Override
	public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft,
			int oldTop, int oldRight, int oldBottom) {
		LogUtil.d(TAG, getClass().getSimpleName(), "onLayoutChange", "v", v.getClass()
				.getSimpleName() + "left" + left + "top", top + "right" + right + "bottom" + bottom
				+ "getHeight", getHeight());
	}

	@Override
	protected boolean onSetAlpha(int alpha) {
		boolean b = super.onSetAlpha(alpha);
		LogUtil.d(TAG, getClass().getSimpleName(), "onSetAlpha", alpha, b);
		return b;
	}
}
