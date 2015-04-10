package com.example.androiddemo.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androiddemo.R;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.view.CustomView;


public abstract class SuperActivity extends BaseActivity implements View.OnClickListener {

	public static final int TEXT_VIEW_TOP = R.id.top_text_view;
	public static final int TEXT_VIEW_LEFT = R.id.left_text_view;
	public static final int TEXT_VIEW_RIGHT = R.id.right_text_view;
	public static final int TEXT_VIEW_BOTTOM = R.id.bottom_text_view;
	public static final int BUTTON_TOP = R.id.top_button;
	public static final int BUTTON_LEFT = R.id.left_button;
	public static final int BUTTON_RIGHT = R.id.right_button;
	public static final int BUTTON_BOTTOM = R.id.bottom_button;
	public static final int BUTTON1 = R.id.button1;
	public static final int BUTTON2 = R.id.button2;
	public static final int BUTTON3 = R.id.button3;
	public static final int BUTTON4 = R.id.button4;
	
	private TextView mTopTextView = null;
	private TextView mLeftTextView = null;
	private TextView mRightTextView = null;
	private TextView mBottomTextView = null;
	private Button mTopButton = null;
	private Button mLeftButton = null;
	private Button mRightButton = null;
	private Button mBottomButton = null;
	private View mOperationArea = null;
	private View mImageArea = null;
	private Button mButton1 = null;
	private Button mButton2 = null;
	private Button mButton3 = null;
	private Button mButton4 = null;
	private CustomView mCustionView = null;
	private LinearLayout mRootView = null;
	

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

	protected String getButton1Text() {
		return "button1";
	}
	
	protected String getButton2Text() {
		return "button2";
	}
	
	protected String getButton3Text() {
		return "button3";
	}
	
	protected String getButton4Text() {
		return "button4s";
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
		case BUTTON1:
			updateViewState(mButton1, str, false);
			mButton1.setOnClickListener(this);
			break;
		case BUTTON2:
			updateViewState(mButton2, str, false);
			mButton2.setOnClickListener(this);
			break;
		case BUTTON3:
			updateViewState(mButton3, str, false);
			mButton3.setOnClickListener(this);
			break;
		case BUTTON4:
			updateViewState(mButton4, str, false);
			mButton4.setOnClickListener(this);
			break;
		default:
			break;
		}
	}
	

	/**
	 * 这写私有函数
	 */
	private void updateViewState(TextView view, String str, boolean append) {
		if (null == view) {
			return;
		}
		
		if (TextUtils.isEmpty(str)) {
			view.setText("");
			view.setVisibility(View.GONE);
		} else {
			if (view.length() > 0 && append) {
				view.setText(view.getText().toString() + AndroidDemoUtil.LINE_SEPARATOR + str);
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

	protected void doButton1Click() {}
	protected void doButton2Click() {}
	protected void doButton3Click() {}
	protected void doButton4Click() {}

	protected boolean canUseOperationLayout() { return false; }
	
	protected boolean canUseImageLayout() { return false; }
	
	protected LinearLayout getMainView() {
		return mRootView;
	}
	
	protected CustomView getCustomView() {
		return mCustionView;
	}
	
	/**
	 * 这里写重载函数
	 */	
	@Override
	public void initLayout() {
		setContentView(R.layout.super_layout);
	}
	
	@Override
	public void bindView() {
		mRootView = (LinearLayout)findViewById(R.id.main_view);
		mTopTextView = (TextView)findViewById(R.id.top_text_view);
		mLeftTextView = (TextView)findViewById(R.id.left_text_view);
		mRightTextView = (TextView)findViewById(R.id.right_text_view);
		mBottomTextView = (TextView)findViewById(R.id.bottom_text_view);
		mTopButton = (Button)findViewById(R.id.top_button);
		mLeftButton = (Button)findViewById(R.id.left_button);
		mRightButton = (Button)findViewById(R.id.right_button);
		mBottomButton = (Button)findViewById(R.id.bottom_button);
		if (canUseOperationLayout()) {
			mOperationArea = findViewById(R.id.operation_area);
			mOperationArea.setVisibility(View.VISIBLE);
			if (null == mOperationArea.getParent()) {
				mOperationArea = findViewById(R.id.operation_area);
				mButton1 = (Button)findViewById(R.id.button1);
				mButton2 = (Button)findViewById(R.id.button2);
				mButton3 = (Button)findViewById(R.id.button3);
				mButton4 = (Button)findViewById(R.id.button4);
			}
		}
		if (canUseImageLayout()) {
			mImageArea = findViewById(R.id.image_area);
			mImageArea.setVisibility(View.VISIBLE);
			if (null == mImageArea.getParent()) {
				mImageArea = findViewById(R.id.image_area);
				mCustionView = (CustomView)findViewById(R.id.custom_view);
			}
		}
	}
	
	@Override
	public void initView() {
		updateButton(BUTTON_TOP, getTopButtonText());
		updateButton(BUTTON_LEFT, getLeftButtonText());
		updateButton(BUTTON_RIGHT, getRightButtonText());
		updateButton(BUTTON_BOTTOM, getBottomButtonText());
		if (canUseOperationLayout()) {
			updateButton(BUTTON1, getButton1Text());
			updateButton(BUTTON2, getButton2Text());
			updateButton(BUTTON3, getButton3Text());
			updateButton(BUTTON4, getButton4Text());
		}
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
		case BUTTON1:
			doButton1Click();
			break;
		case BUTTON2:
			doButton2Click();
			break;
		case BUTTON3:
			doButton3Click();
			break;
		case BUTTON4:
			doButton4Click();
			break;
		default:
			break;
		}
	}
}
