package com.example.androiddemo.activity;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.example.androiddemo.tools.CommonCallbacks;
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
	
	private static final int TEST_DATA = 33729902;
	
	private CommonInputView mCommonInputView = null;
	
	IMultiTalkSdkApi mPBSDK = null;

	private Intent getPhoneBookJumpIntent() {
		Intent intent = new Intent();
		intent.setAction("com.tencent.pb.voip.single.invoke");
		intent.putExtra("extra_start_enrty", 0);
		intent.putExtra("extra_key_main_info", "测试");
		intent.putExtra("extra_key_sub_info", "xxxx@qq.com");
		intent.putExtra("extra_key_promtp_info", "正在呼叫");
		intent.putExtra("extra_key_extra_info", "邮箱呼叫电话本");
		String clientUUID = mCommonInputView.getInputView1().getText().toString();
		intent.putExtra("extra_key_test_client_uuid", TextUtils.isEmpty(clientUUID) ? TEST_DATA : Integer.valueOf(clientUUID));
		intent.putExtra("extra_key_mic_mute", false);
		intent.putExtra("extra_key_speaker_on", false);
		intent.putExtra("extra_key_main_id", 333443L);
		intent.putExtra("extra_key_show_title", true);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		return intent;
	}
	
	private Intent getPhoneBookNewJumpIntent() {
		Intent intent = new Intent();
		intent.setAction("com.tencent.pb.voip.single.invoke");
		intent.putExtra("extra_start_enrty", 0);
		intent.putExtra("extra_key_main_info", "测试");
		intent.putExtra("extra_key_sub_info", "xxxx@qq.com");
		intent.putExtra("extra_key_promtp_info", "正在呼叫...");
		intent.putExtra("extra_key_extra_info", "邮箱呼叫电话本");
		intent.putExtra("extra_key_mic_mute", false);
		intent.putExtra("extra_key_speaker_on", false);
		intent.putExtra("extra_key_main_id", 333443L);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		return intent;
	}
	
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
		mPBSDK.init(this, mCommonInputView.getInputView1().getText().toString());
		mPBSDK.setOpenLog(true, false);
		mPBSDK.reqAuth(mCommonInputView.getInputView2().getText().toString());
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
		return "呼出";
	}
	
	@Override
	protected void doBotttomButtonClick() {
		startActivity(getPhoneBookJumpIntent());
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
		mCommonInputView.getInputView1().setText(String.valueOf(TEST_DATA));
		mCommonInputView.getInputView2().setText(CODE3);
	}
}

