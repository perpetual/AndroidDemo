package com.example.androiddemo.activity;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AndroidException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.androiddemo.R;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;

public class DemoMainListActivity extends SuperListActivity<String> {	
	private String[] mTitleArray = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		LogUtil.d(TAG, AndroidDemoUtil.deviceInfo2String());
		super.onCreate(savedInstanceState);
		directJumpTo();
	}
	
	private void directJumpTo() {
		Intent intent = new Intent(this, ViewDrawActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Class<?> cls = null;
		try {
			String packageName = getPackageName();
			String classString = packageName + ".activity." + mTitleArray[position];
			cls = Class.forName(classString);
			Intent intent = new Intent(DemoMainListActivity.this, cls);
			startActivity(intent);
		} catch (Exception e) {
			AndroidDemoUtil.showLongToast(e.toString());
		}
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu){
		menu.add("");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		return false;
	}


	@Override
	public void initData(Context context, AttributeSet attrs) {
		super.initData(context, attrs);
		mTitleArray = getResources().getStringArray(R.array.demo_list);
	}

	@Override
	public void initView() {
		super.initView();
		setSelection(getListAdapter().getCount() - 1);
	}

	@Override
	protected List<String> getDataSource() {
		Arrays.sort(mTitleArray);
		return Arrays.asList(mTitleArray);
	}
}
