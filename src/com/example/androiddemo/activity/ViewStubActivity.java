package com.example.androiddemo.activity;

import com.example.androiddemo.R;
import com.example.androiddemo.R.id;
import com.example.androiddemo.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

public class ViewStubActivity extends Activity {

	ViewStub mViewStub = null;
	TextView mTestTV = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_stub_layout);
		bindView();
		initView();
	}

	/**
	 * 私有工具函数
	 */
	private void bindView() {
		mViewStub = (ViewStub) findViewById(R.id.mystub);
		mViewStub.setVisibility(View.VISIBLE);
		View view = findViewById(R.id.myinflate);
		mTestTV = (TextView)findViewById(R.id.test_tv);
	}

	private void initView() {
//		mViewStub.inflate();
		mViewStub.setVisibility(View.VISIBLE);
		mTestTV.setText("fegteg");
	}
}
