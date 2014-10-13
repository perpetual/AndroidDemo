package com.example.androiddemo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;

import com.example.androiddemo.model.IUIInitialization;
import com.example.androiddemo.utils.AndroidUtils;

public class BaseActivity extends Activity implements IUIInitialization{

	private static final String TAG = AndroidUtils.getClassName(BaseActivity.class);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, this + "onCreate");
		AndroidUtils.APPLICATION_CONTEXT = getApplicationContext();
		initData(null, null);
		initLayout();
		bindView();
		initView();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, this + "onStart");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(TAG, this + "onRestart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, this + "onResume");
		AndroidUtils.showToast("onResume");
		AndroidUtils.hideSoftInput(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, this + "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, this + "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, this + "onDestroy");
	}
	

	@Override
	public boolean onKeyDown(final int keyCode, final KeyEvent event) {
		return super.onKeyDown(keyCode, event);
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
	public void initData(Context context, AttributeSet attrs) {
		
	}

	@Override
	public void refreshView() {
		
	}
}
