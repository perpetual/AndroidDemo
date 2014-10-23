package com.example.androiddemo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;

import com.example.androiddemo.model.IUIInitialization;
import com.example.androiddemo.utils.AndroidUtils;

public class BaseActivity extends Activity implements IUIInitialization{

	private static final String TAG = AndroidUtils.getClassName(BaseActivity.class);
	protected ViewGroup mDecorView = null;
	private Handler mHandler = null;
	private static final int MSG_REFRESH = 0;
	
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
		mHandler.sendEmptyMessageDelayed(MSG_REFRESH, 2000);
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
		Log.d(TAG, getWindow().getClass().getName());
	}

	@Override
	public void initData(Context context, AttributeSet attrs) {
		mDecorView = (ViewGroup)getWindow().getDecorView();
		mHandler = new Handler(getMainLooper(), new Handler.Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				switch (msg.what) {
				case MSG_REFRESH:
					refreshView();
					break;

				default:
					break;
				}
				return true;
			}
		});
	}

	@Override
	public void refreshView() {
		AndroidUtils.logChildView(mDecorView);
	}
	
	/**
	 * 私有工具函数
	 */
}
