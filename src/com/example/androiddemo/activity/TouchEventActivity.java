package com.example.androiddemo.activity;

import com.example.androiddemo.R;
import com.example.androiddemo.R.layout;

import android.app.Activity;
import android.os.Bundle;

public class TouchEventActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bindUI();
	}
	
	/**
	 * ˽�й��ߺ���
	 */
	private void bindUI() {
		setContentView(R.layout.touch_event_layout);
	}
}
