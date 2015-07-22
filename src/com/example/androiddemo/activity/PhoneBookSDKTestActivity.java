package com.example.androiddemo.activity;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;
import com.example.androiddemo.view.CommonInputView;
import com.tencent.pb.multitalk.sdk.IMultiTalkCallBack;
import com.tencent.pb.multitalk.sdk.IMultiTalkSdkApi;
import com.tencent.pb.multitalk.sdk.MultiTalkSdkApiFactory;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-7-20		Create		
 * </pre>
 */
public class PhoneBookSDKTestActivity extends DemoSuperActivity implements IMultiTalkCallBack {

	private static String TAG = "PhoneBookSDKTestActivity";

	public static final String CLIENTID3 = "1000004";
	public static final String CODE3 = "SDKConstCode";
	public static final String UNIQUEID = "3333333333";
	
	private CommonInputView mCommonInputView = null;
	
	IMultiTalkSdkApi mPBSDK = null;
	
	@Override
	protected int getCustomViewAreaLayoutResource() {
		return COMMON_INPUT_VIEW_LAYOUT;
	}
	
	@Override
	public void initData(Context context, AttributeSet attrs) {
		super.initData(context, attrs);
		mPBSDK = MultiTalkSdkApiFactory.createMultiTalkSdkApi(this, "qcsdkappid_test");
	}
	
	@Override
	protected String getTopButtonText() {
		return "启动";
	}
	
	@Override
	protected void doTopButtonClick() {
		mPBSDK.init(this, CLIENTID3);
		mPBSDK.setOpenLog(true, false);
		mPBSDK.reqAuth(CODE3);
	}
	
	@Override
	protected String getLeftButtonText() {
		return "进入";
	}
	
	@Override
	protected void doLeftButtonClick() {
		mPBSDK.enterMultiTalk(UNIQUEID);
	}
	
	@Override
	protected String getRightButtonText() {
		return "退出";
	}
	
	@Override
	protected String getBottomButtonText() {
		return "设置";
	}
	
	@Override
	protected void doBotttomButtonClick() {
	}
	
	@Override
	protected void doRightButtonClick() {
		mPBSDK.exitMultiTalk();
		updateTextView(TEXT_VIEW_BOTTOM, "", false);
	}

	@Override
	public void onAuthSucceed() {
		LogUtil.d(TAG, "onAuthSucceed");
		updateTextView(TEXT_VIEW_BOTTOM, "onAuthSucceed", true);
	}

	@Override
	public void onEnterMultiTalk() {
		LogUtil.d(TAG, "onEnterMultiTalk");
		updateTextView(TEXT_VIEW_BOTTOM, "onEnterMultiTalk", true);
	}

	@Override
	public void onMemberChange(List<String> memberList) {
		LogUtil.d(TAG, "onMemberChange");
		updateTextView(TEXT_VIEW_BOTTOM, "onMemberChange", true);
	}

	@Override
	public void onMultiTalkReady() {
		LogUtil.d(TAG, "onMultiTalkReady");
		updateTextView(TEXT_VIEW_BOTTOM, "onMultiTalkReady", true);
	}

	@Override
	public void onMuteStateChange(boolean isMute) {
		LogUtil.d(TAG, "onMuteStateChange");
		updateTextView(TEXT_VIEW_BOTTOM, "onMuteStateChange", true);
	}

	@Override
	public void onSpeakerStateChange(boolean isSpeadker) {
		LogUtil.d(TAG, "onSpeakerStateChange");
		updateTextView(TEXT_VIEW_BOTTOM, "onSpeakerStateChange", true);
	}

	@Override
	public void onErr(int errCode) {
		LogUtil.d(TAG, "onErr");
		updateTextView(TEXT_VIEW_BOTTOM, AndroidDemoUtil.argumentsToString("onErr", errCode), true);
		
	}

	@Override
	public void onTalkingMember(List<String> memberList) {
		LogUtil.d(TAG, "onTalkingMember");	
		updateTextView(TEXT_VIEW_BOTTOM, "onTalkingMember", true);	
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mPBSDK.exitMultiTalk();
	}
	
	@Override
	public void bindView() {
		super.bindView();
		mCommonInputView = (CommonInputView)getCustomView();
	}
	
	@Override
	public void initView() {
		super.initView();
		mCommonInputView.getInputView1().setText(CLIENTID3);
		mCommonInputView.getInputView2().setText(CODE3);
	}
}

