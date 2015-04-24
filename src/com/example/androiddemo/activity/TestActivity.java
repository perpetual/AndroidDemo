package com.example.androiddemo.activity;

import com.example.androiddemo.utils.LogUtil;

import android.content.Intent;


public class TestActivity extends SuperActivity {
	
	@Override
	protected String getTopButtonText() {
		return "start first activity";
	}
	
	@Override
	protected void doTopButtonClick() {
		startActivity(new Intent(this, FirstActivity.class));
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		LogUtil.d(TAG, "onActivityResult");
	}
}
