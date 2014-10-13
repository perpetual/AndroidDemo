package com.example.androiddemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CustomView extends View {

	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		long start = System.currentTimeMillis();
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Log.w("xxx", String.valueOf(System.currentTimeMillis() - start));
	}
}
