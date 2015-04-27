package com.example.androiddemo.activity;

import java.util.Set;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androiddemo.R;
import com.example.androiddemo.utils.AndroidDemoUtil;

public class MainListActivity extends ListActivity {
	private static final String TAG = MainListActivity.class.getSimpleName();
	
	private ListAdapter mListAdapter = null;
	private String[] mTitleArray = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Intent intent = getIntent();
		Set<String> set = intent.getCategories();
		super.onCreate(savedInstanceState);
		AndroidDemoUtil.getPrivateValue(Activity.class, "mActivityInfo", this);
		Log.d(TAG, TAG + ":process id:" + android.os.Process.myPid());
		initData();
		initView();
//		directJumpTo();
	}
	
	private void directJumpTo() {
		Intent intent = new Intent(this, AccelerometerActivity.class);
		startActivity(intent);
		finish();
	}
	
	private int parse(String str) {
		Integer p1 = null;
		Integer p2 = null;
		for (int i = 0; i < str.length(); ++i) {
			int numberStartIndex = -1;
			int numberEndIndex = -1;
			if (Character.isDigit(str.charAt(i)) && numberStartIndex < 0) {
				numberStartIndex = i;
			}
			if (!Character.isDigit(str.charAt(i)) && numberStartIndex >= 0) {
				numberEndIndex = i;
				int temp = Integer.valueOf(str.substring(numberStartIndex, numberEndIndex));
				if (null == p1) {
					p1 = temp;
				} else if (null == p2) {
					p2 = temp;
				}
				numberStartIndex = -1;
				numberEndIndex = -1;
			}			
		}
		return p1 + p2;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Class<?> cls = null;
		try {
			String packageName = getPackageName();
			String classString = packageName + ".activity." + mTitleArray[position];
			cls = Class.forName(classString);
			Intent intent = new Intent(MainListActivity.this, cls);
			startActivity(intent);
		} catch (Exception e) {
			AndroidDemoUtil.showLongToast(e.toString());
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, this + "onStart");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(TAG, this + "onRestart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, this + "onResume");
		AndroidDemoUtil.showLongToast("taskID:" + getTaskId());
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, this + "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, this + "onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, this + "onDestroy");
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
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

	/**
	 * ˽�й��ߺ���
	 */
	private void initData() {
		AndroidDemoUtil.APPLICATION_CONTEXT = getApplicationContext();
		mTitleArray = getResources().getStringArray(R.array.demo_list);
		initAdapter();
	}

	private void initView() {
		setSelection(mListAdapter.getCount() - 1);
	}
	
	private void initAdapter() {
		mListAdapter = new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = convertView;
				if (null == view) {
					view = LayoutInflater.from(MainListActivity.this).inflate(
							R.layout.main_list_item, null);
				}
				TextView tv = (TextView) view.findViewById(R.id.title1);
				tv.setText(mTitleArray[position]);
				return view;
			}
			
			@Override
			public long getItemId(int position) {
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				return null;
			}
			
			@Override
			public int getCount() {
				return mTitleArray.length;
			}
		};

		mListAdapter = new ArrayAdapter<String>(this, R.layout.main_list_textview, mTitleArray);
		setListAdapter(mListAdapter);
	}
}
