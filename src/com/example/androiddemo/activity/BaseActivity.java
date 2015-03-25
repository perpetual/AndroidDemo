package com.example.androiddemo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;

import com.example.androiddemo.model.IUIInitialization;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;

public abstract class BaseActivity extends Activity implements
		OnLayoutChangeListener {

	protected static String TAG = BaseActivity.class.getSimpleName();
	protected ViewGroup mDecorView = null;

	protected String getLogTag() {
		return TAG;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().getDecorView().addOnLayoutChangeListener(this);
		LogUtil.d(TAG, AndroidDemoUtil.getClassName(this.getClass()) + "|onCreate");
	}

	@Override
	protected void onStart() {
		super.onStart();
		LogUtil.d(TAG, AndroidDemoUtil.getClassName(this.getClass()) + "|onStart");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		LogUtil.d(TAG, AndroidDemoUtil.getClassName(this.getClass()) + "|onRestart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		LogUtil.d(TAG, AndroidDemoUtil.getClassName(this.getClass()) + "|onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		LogUtil.d(TAG, AndroidDemoUtil.getClassName(this.getClass()) + "|onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		LogUtil.d(TAG, AndroidDemoUtil.getClassName(this.getClass()) + "|onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogUtil.d(TAG, AndroidDemoUtil.getClassName(this.getClass()) + "|onDestroy");
	}

	@Override
	public boolean onKeyDown(final int keyCode, final KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onLayoutChange(View v, int left, int top, int right,
			int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
		LogUtil.d(TAG,
				"onLayoutChange|v:" + AndroidDemoUtil.getClassName(v.getClass())
						+ "|left:" + left + "|top:" + top + "|right:" + right
						+ "|bottom:" + bottom + "|oldLeft:" + oldLeft
						+ "|oldTop:" + oldTop + "|oldRight:" + oldRight
						+ "|oldBottom:" + oldBottom);
	}
}
