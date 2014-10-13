package com.example.androiddemo.view;

import com.example.androiddemo.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListItemView extends LinearLayout {

	private TextView mMainTextView = null;
	
	public ListItemView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.main_list_textview, this);
		bindView();
	}

	public void setMainText(String str) {
		mMainTextView.setText(str);
	}
	/**
	 * 私有工具函数
	 */
	private void bindView () {
		mMainTextView = (TextView) findViewById(R.id.main_text);
	}
}
