package com.example.androiddemo.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.SimpleAdapter;

import com.example.androiddemo.model.IUIInitialization;

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
public abstract class SuperListActivity extends ListActivity implements IUIInitialization {

	private SimpleAdapter mListAdapter = null;
	private Context mContext = null;
	
	private static final String COLUMN1 = "column1";

	protected Collection<String> getDataSource() {
		String[] array = new String[]{"1", "2", "3", "4", "5", "6"};
		return Arrays.asList(array);
	}
	
	private void updateData() {
		String [] from = new String[]{COLUMN1};
		int[] to = new int[]{android.R.id.text1};
		mListAdapter = new SimpleAdapter(mContext, transformDataSource(getDataSource()), android.R.layout.simple_list_item_1, from, to);
		setListAdapter(mListAdapter);
	}
	
	private List<Map<String, Object>> transformDataSource(Collection<String> data) {
		List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
		if (null == data) {
			return dataSource;
		}
		for (String str : data) {
			Map<String, Object> tempMap = new HashMap<String, Object>();
			tempMap.put(COLUMN1, str);
			dataSource.add(tempMap);
		}
		return dataSource;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData(this, null);
		initLayout();
		bindView();
		initView();
		refreshView();
	}

	@Override
	public void initData(Context context, AttributeSet attrs) {
		mContext = context;
		updateData();
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
		updateView();
	}
}

