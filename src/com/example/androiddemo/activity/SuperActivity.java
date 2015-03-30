package com.example.androiddemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androiddemo.R;
import com.example.androiddemo.model.IUIInitialization;
import com.winter_maple.utils.JavaDemoUtil;


public abstract class SuperActivity extends BaseActivity implements IUIInitialization, View.OnClickListener{

	public static final int TEXT_VIEW_TOP = R.id.top_text_view;
	public static final int TEXT_VIEW_LEFT = R.id.left_text_view;
	public static final int TEXT_VIEW_RIGHT = R.id.right_text_view;
	public static final int TEXT_VIEW_BOTTOM = R.id.bottom_text_view;
	public static final int BUTTON_TOP = R.id.top_button;
	public static final int BUTTON_LEFT = R.id.left_button;
	public static final int BUTTON_RIGHT = R.id.right_button;
	public static final int BUTTON_BOTTOM = R.id.bottom_button;
	
	protected TextView mTopTextView = null;
	protected TextView mLeftTextView = null;
	protected TextView mRightTextView = null;
	protected TextView mBottomTextView = null;
	protected Button mTopButton = null;
	protected Button mLeftButton = null;
	protected Button mRightButton = null;
	protected Button mBottomButton = null;
	

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
			mTopButton.setOnClickListener(this);
			break;
		case BUTTON_LEFT:
			updateViewState(mLeftButton, str, false);
			mLeftButton.setOnClickListener(this);
			break;
		case BUTTON_RIGHT:
			updateViewState(mRightButton, str, false);
			mRightButton.setOnClickListener(this);
			break;
		case BUTTON_BOTTOM:
			updateViewState(mBottomButton, str, false);
			mBottomButton.setOnClickListener(this);
			break;
		default:
			break;
		}
	}
	

	/**
	 * 这写私有函数
	 */
	private void updateViewState(TextView view, String str, boolean append) {
		if (TextUtils.isEmpty(str)) {
			view.setVisibility(View.GONE);
		} else {
			if (view.length() > 0 && append) {
				view.setText(mTopTextView.getText().toString() + str + JavaDemoUtil.LINE_SEPARATOR);
			} else {
				view.setText(str);
			}
			view.setVisibility(View.VISIBLE);
		}
	}
	

	protected void doTopButtonClick() {}

	protected void doLeftButtonClick() {}

	protected void doRightButtonClick() {}

	protected void doBotttomButtonClick() {}
	
	
	/**
	 * 这里写重载函数
	 */
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
	
	@Override
	public void updateView() {
	}
	
	@Override
	public void refreshView() {
		updateView();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case BUTTON_TOP:
			doTopButtonClick();
			break;
		case BUTTON_LEFT:
			doLeftButtonClick();
			break;
		case BUTTON_RIGHT:
			doRightButtonClick();
			break;
		case BUTTON_BOTTOM:
			doBotttomButtonClick();
			break;
		default:
			break;
		}
	}
}
