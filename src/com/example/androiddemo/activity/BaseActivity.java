package com.example.androiddemo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;

import com.example.androiddemo.model.IUIInitialization;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;

public abstract class BaseActivity extends Activity implements IUIInitialization {

	protected static String TAG = BaseActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		TAG = this.getClass().getSimpleName();
		super.onCreate(savedInstanceState);
		LogUtil.d(TAG, AndroidDemoUtil.getClassName(this.getClass()) + "|onCreate");
		initData(this, null);
		initLayout();
		bindView();
		initView();
		refreshView();
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
	public void initData(Context context, AttributeSet attrs) {		
	}

	@Override
	public void initLayout() {		
	}

	@Override
	public void bindView() {		
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
}
