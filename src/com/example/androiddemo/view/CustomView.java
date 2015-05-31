package com.example.androiddemo.view;

import java.util.Arrays;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.widget.ImageView;

import com.example.androiddemo.model.IViewInitialization;
import com.example.androiddemo.utils.LogUtil;

public class CustomView extends ImageView implements OnLayoutChangeListener, IViewInitialization {

	private static final String TAG = CustomView.class.getSimpleName();

	public CustomView(Context context) {
		super(context);
		initData(context, null);
		initView();
		updateView();
		addOnLayoutChangeListener(this);
	}

	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initData(context, attrs);
		initView();
		updateView();
		addOnLayoutChangeListener(this);
	}

	@Override
	public void draw(Canvas canvas) {
		LogUtil.d(TAG, getClass().getSimpleName(), "draw");
		super.draw(canvas);
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

	@Override
	public void initData(Context context, AttributeSet attrs) {
		
	}

	@Override
	public void initView() {
		
	}

	@Override
	public void updateView() {
		
	}

	@Override
	public void refreshView() {
		
	}
	
	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		LogUtil.d(TAG, getClass().getSimpleName(), "drawableStateChanged", Arrays.asList(getDrawableState()));
	}
}
