package com.example.androiddemo.activity;

import android.content.Intent;

public class SecondActivity extends TestActivity {

	@Override
	protected void buttonClickAction() {
		Intent intent = new Intent(this, ThirdActivity.class);
		startActivity(intent);
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
