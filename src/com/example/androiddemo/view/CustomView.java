package com.example.androiddemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnLayoutChangeListener;

import com.example.androiddemo.utils.AndroidUtils;
import com.example.androiddemo.utils.LogUtil;

public class CustomView extends View implements OnLayoutChangeListener {

	private static final String TAG = "xxx";

	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		addOnLayoutChangeListener(this);
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
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		LogUtil.d(TAG, AndroidUtils.getClassName(this) + "|onLayout|changed:"
				+ changed + "|left:" + left + "|top:" + top + "|right:" + right
				+ "|bottom:" + bottom + "|getHeight:" + getHeight());
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		LogUtil.d(TAG, AndroidUtils.getClassName(this) + "|onDraw|getHeight:"
				+ getHeight());
		super.onDraw(canvas);
	}

	@Override
	protected void onFinishInflate() {
		LogUtil.d(TAG, AndroidUtils.getClassName(this)
				+ "|onFinishInflate|getHeight:" + getHeight());
		super.onFinishInflate();
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
