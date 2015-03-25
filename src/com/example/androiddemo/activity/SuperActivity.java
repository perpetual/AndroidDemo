package com.example.androiddemo.activity;

import com.example.androiddemo.model.IUIInitialization;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;


public abstract class SuperActivity extends BaseActivity implements IUIInitialization{

	protected TextView mTopTextView = null;
	protected TextView mLeftTextView = null;
	protected TextView mRightTextView = null;
	protected Button mTopButton = null;
	protected Button mLeftButton = null;
	protected Button mRightButton = null;
	
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
