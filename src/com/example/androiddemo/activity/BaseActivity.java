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
import com.example.androiddemo.utils.AndroidUtils;
import com.example.androiddemo.utils.LogUtil;

public class BaseActivity extends Activity implements IUIInitialization,
		OnLayoutChangeListener {

	protected static String TAG = "BaseActivity";
	protected ViewGroup mDecorView = null;
	private Handler mHandler = null;
	private static final int MSG_REFRESH = 0;

	protected String getLogTag() {
		return TAG = "BaseActivity";
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		TAG = getLogTag();
		super.onCreate(savedInstanceState);
		LogUtil.d(TAG, AndroidUtils.getClassName(this.getClass()) + "|onCreate");
		initData(null, null);
		initLayout();
		bindView();
		initView();
	}

	@Override
	protected void onStart() {
		super.onStart();
		LogUtil.d(TAG, AndroidUtils.getClassName(this.getClass()) + "|onStart");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		LogUtil.d(TAG, AndroidUtils.getClassName(this.getClass()) + "|onRestart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		LogUtil.d(TAG, AndroidUtils.getClassName(this.getClass()) + "|onResume");
		AndroidUtils.showToast("onResume");
		// mHandler.sendEmptyMessageDelayed(MSG_REFRESH, 2000);
	}

	@Override
	protected void onPause() {
		super.onPause();
		LogUtil.d(TAG, AndroidUtils.getClassName(this.getClass()) + "|onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		LogUtil.d(TAG, AndroidUtils.getClassName(this.getClass()) + "|onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogUtil.d(TAG, AndroidUtils.getClassName(this.getClass()) + "|onDestroy");
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
		getWindow().getDecorView().addOnLayoutChangeListener(this);
		LogUtil.d("xxx", getWindowManager().toString());
	}

	@Override
	public void initData(Context context, AttributeSet attrs) {
		mDecorView = (ViewGroup) getWindow().getDecorView();
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
		LogUtil.d(
				TAG,
				"refreshView:"
						+ String.valueOf(getWindow().getDecorView().getHeight()));
	}

	@Override
	public void onLayoutChange(View v, int left, int top, int right,
			int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
		LogUtil.d(TAG,
				"onLayoutChange|v:" + AndroidUtils.getClassName(v.getClass())
						+ "|left:" + left + "|top:" + top + "|right:" + right
						+ "|bottom:" + bottom + "|oldLeft:" + oldLeft
						+ "|oldTop:" + oldTop + "|oldRight:" + oldRight
						+ "|oldBottom:" + oldBottom);
	}

	/**
	 * 私有工具函数
	 */
}
