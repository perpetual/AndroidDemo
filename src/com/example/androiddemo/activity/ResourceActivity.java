package com.example.androiddemo.activity;

import com.example.androiddemo.R;
import com.example.androiddemo.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class ResourceActivity extends Activity {

	private ImageView mImageView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bindUI();
	}
	
	/**
	 * Ë½ÓÐº¯Êý
	 */
	private void bindUI() {
		setContentView(R.layout.resource_layout);
	}
}
