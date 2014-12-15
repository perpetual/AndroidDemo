package com.example.androiddemo.activity;

import com.example.androiddemo.R;
import com.example.androiddemo.R.layout;
import com.example.androiddemo.utils.AndroidDemoUtil;

import android.os.Bundle;
import android.widget.LinearLayout;

public class TestActivity extends BaseActivity {

	private LinearLayout mRootView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidDemoUtil.APPLICATION_CONTEXT = getApplicationContext();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	protected void buttonClickAction() {
	}

	protected String getDisplayString() {
		return this.toString() + "|taskId:" + 252;
	}
	
	public void initLayout() {
		setContentView(R.layout.test_layout);
	}
}
