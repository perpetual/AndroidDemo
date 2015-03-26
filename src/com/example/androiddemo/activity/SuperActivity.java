package com.example.androiddemo.activity;

import com.example.androiddemo.R;
import com.example.androiddemo.model.IUIInitialization;
import com.example.androiddemo.utils.TextUtil;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public abstract class SuperActivity extends BaseActivity implements IUIInitialization{

	public static final int TEXT_VIEW_TOP = 0;
	public static final int TEXT_VIEW_LEFT = 1;
	public static final int TEXT_VIEW_RIGHT = 2;
	public static final int TEXT_VIEW_BOTTOM = 3;
	public static final int BUTTON_TOP = 10;
	public static final int BUTTON_LEFT = 11;
	public static final int BUTTON_RIGHT = 12;
	public static final int BUTTON_BOTTOM = 13;
	
	protected TextView mTopTextView = null;
	protected TextView mLeftTextView = null;
	protected TextView mRightTextView = null;
	protected TextView mBottomTextView = null;
	protected Button mTopButton = null;
	protected Button mLeftButton = null;
	protected Button mRightButton = null;
	protected Button mBottomButton = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData(null, null);
		initLayout();
		bindView();
		initView();
		refreshView();
	}
	
	@Override
	public void initData(Context context, AttributeSet attrs) {
		
	}
	
	@Override
	public void initLayout() {
		setContentView(R.layout.super_layout);
	}
	
	@Override
	public void bindView() {
		mTopTextView = (TextView)findViewById(R.id.top_text_view);
		mLeftTextView = (TextView)findViewById(R.id.left_text_view);
		mRightTextView = (TextView)findViewById(R.id.right_text_view);
		mBottomTextView = (TextView)findViewById(R.id.bottom_text_view);
		mTopButton = (Button)findViewById(R.id.top_button);
		mLeftButton = (Button)findViewById(R.id.left_button);
		mRightButton = (Button)findViewById(R.id.right_button);
		mBottomButton = (Button)findViewById(R.id.bottom_button);
	}
	
	@Override
	public void initView() {
		updateButton(BUTTON_TOP, getTopButtonText());
		updateButton(BUTTON_LEFT, getLeftButtonText());
		updateButton(BUTTON_RIGHT, getRightButtonText());
		updateButton(BUTTON_BOTTOM, getBottomButtonText());
	}
	
	private void updateViewState(TextView view, String str, boolean append) {
		if (TextUtils.isEmpty(str)) {
			view.setVisibility(View.GONE);
		} else {
			if (view.length() > 0 && append) {
				view.setText(mTopTextView.getText().toString() + str);
			} else {
				view.setText(str);
			}
			view.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public void updateView() {
	}
	
	@Override
	public void refreshView() {
		updateView();
	}

	protected String getTopButtonText() {
		return "top";
	}
	
	protected String getLeftButtonText() {
		return "left";
	}

	protected String getRightButtonText() {
		return "right";
	}

	protected String getBottomButtonText() {
		return "bottom";
	}

	public void updateTextView(final int index, String str, boolean append) {
		switch (index) {
		case TEXT_VIEW_TOP:
			updateViewState(mTopTextView, str, append);
			break;
		case TEXT_VIEW_LEFT:
			updateViewState(mLeftTextView, str, append);
			break;
		case TEXT_VIEW_RIGHT:
			updateViewState(mRightTextView, str, append);
			break;
		case TEXT_VIEW_BOTTOM:
			updateViewState(mBottomTextView, str, append);
			break;
		default:
			break;
		}
	}
	
	public void updateButton(final int index, String str) {
		switch (index) {
		case BUTTON_TOP:
			updateViewState(mTopButton, str, false);
			break;
		case BUTTON_LEFT:
			updateViewState(mLeftButton, str, false);
			break;
		case BUTTON_RIGHT:
			updateViewState(mRightButton, str, false);
			break;
		case BUTTON_BOTTOM:
			updateViewState(mBottomButton, str, false);
			break;
		default:
			break;
		}
	}
}
