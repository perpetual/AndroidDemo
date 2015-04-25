package com.example.androiddemo.activity;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;

import com.example.androiddemo.model.IUIInitialization;
import com.example.androiddemo.tools.SuperListAdapter;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-4-24		Create		
 * </pre>
 */
public abstract class SuperListActivity<T> extends ListActivity implements IUIInitialization {
	protected static String TAG = BaseActivity.class.getSimpleName();

	protected SuperListAdapter<T> mListAdapter = null;
	
	protected abstract List<T> getDataSource();
	
	private void updateData() {
		mListAdapter.updateData(getDataSource());
		if (null == getListAdapter()) {
			setListAdapter(mListAdapter);
		}
	}	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		TAG = this.getClass().getSimpleName();
		super.onCreate(savedInstanceState);
		initData(this, null);
		initLayout();
		bindView();
		initView();
		refreshView();
	}

	@Override
	public void initData(Context context, AttributeSet attrs) {
		mListAdapter = new SuperListAdapter<T>(context);
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
		updateData();	
	}

	@Override
	public void refreshView() {
		updateView();
	}
}