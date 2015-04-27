package com.example.androiddemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.androiddemo.R;
import com.example.androiddemo.animation.BreatheAniamation;
import com.example.androiddemo.animation.ShakeAnimation;
import com.example.androiddemo.model.OperationCode;
import com.example.androiddemo.tools.AccelerometerManager;
import com.example.androiddemo.tools.CommonCallbacks.ICallback;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * Gary		2015-4-25		Create		
 * </pre>
 */
public class AccelerometerActivity extends SuperActivity implements ICallback, OnSeekBarChangeListener{

	private static final int sViberationMinDuration = 100;
	private static final int sViberationMaxDuration = 3000;
	private static final int sViberationMinInterval = 100;
	private static final int sViberationMaxInterval = 2000;
	private static final int sShakeMaxAmplitude = 60;
	
	private TextView mAmplitudeThresholdTextView = null;
	private SeekBar mAmplitudeThresholdSeekBar = null;
	private TextView mVibrationDurationTextView = null;
	private SeekBar mVibrationDurationSeekBar = null;
	private TextView mVibrationIntervalTextView = null;
	private SeekBar mVibrationIntervalSeekBar = null;
	
	private ShakeAnimation mShakeAnimation = null;
	private BreatheAniamation mFlickerAnimation = null;
	
	@Override
	public void initData(Context context, AttributeSet attrs) {
		super.initData(context, attrs);
		AccelerometerManager.getInstance().add(this);
		mFlickerAnimation = new BreatheAniamation();
	}

	@Override
	public void bindView() {
		super.bindView();
		mAmplitudeThresholdTextView = (TextView) findViewById(R.id.amplitude_threshold_text_view);
		mAmplitudeThresholdSeekBar = (SeekBar) findViewById(R.id.amplitude_threshold_seek_bar);
		mVibrationDurationTextView = (TextView) findViewById(R.id.vibration_duration_text_view);
		mVibrationDurationSeekBar = (SeekBar) findViewById(R.id.vibration_duration_seek_bar);
		mVibrationIntervalTextView = (TextView) findViewById(R.id.vibration_interval_text_view);
		mVibrationIntervalSeekBar = (SeekBar) findViewById(R.id.vibration_interval_seek_bar);
	}

	@Override
	public void initView() {
		super.initView();
		mAmplitudeThresholdSeekBar.setMax(sShakeMaxAmplitude - AccelerometerManager.sMinShakeAmplitudeThreshold);
		mAmplitudeThresholdSeekBar.setProgress(AccelerometerManager.sMaxShakeAmplitudeThreshold - AccelerometerManager.sMinShakeAmplitudeThreshold);
		mAmplitudeThresholdSeekBar.setOnSeekBarChangeListener(this);
		mAmplitudeThresholdSeekBar.setKeyProgressIncrement(1);
		
		mVibrationDurationSeekBar.setMax(sViberationMaxDuration - sViberationMinDuration);
		mVibrationDurationSeekBar.setProgress(AccelerometerManager.sVibrationDuration - sViberationMinDuration);
		mVibrationDurationSeekBar.setOnSeekBarChangeListener(this);
		mVibrationDurationSeekBar.setKeyProgressIncrement(100);
		
		mVibrationIntervalSeekBar.setMax(sViberationMaxInterval - sViberationMinInterval);
		mVibrationIntervalSeekBar.setProgress(AccelerometerManager.sVibrationInterval - sViberationMinInterval);
		mVibrationIntervalSeekBar.setOnSeekBarChangeListener(this);
		mVibrationIntervalSeekBar.setKeyProgressIncrement(100);
		mShakeAnimation = new ShakeAnimation(getCustomView());
		WindowManager.LayoutParams params = getWindow().getAttributes();
		params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
		getWindow().addFlags(params.flags);
	}
	
	@Override
	public void updateView() {
		mAmplitudeThresholdTextView.setText(String.valueOf(AccelerometerManager.sMaxShakeAmplitudeThreshold));
		mVibrationDurationTextView.setText(String.valueOf(AccelerometerManager.sVibrationDuration));
		mVibrationIntervalTextView.setText(String.valueOf(AccelerometerManager.sVibrationInterval));
	}
	
	@Override
	public void refreshView() {
		updateView();
	}
	
	@Override
	public void callback(int opCode, int arg1, int arg2, String str,
			Object object) {
		switch (opCode) {
		case OperationCode.OP_CODE_SENSOR_STATE_CHANGED:
			updateTextView(TEXT_VIEW_TOP, str, false);
			break;
		case OperationCode.OP_CODE_ACCURACY_CHANGED_CHANGED:
			updateTextView(TEXT_VIEW_BOTTOM, str, true);
			break;
		case OperationCode.OP_CODE_SHAKE:
			updateTextView(TEXT_VIEW_LEFT, str, false);
			updateTextView(TEXT_VIEW_RIGHT, String.valueOf(arg2), false);
			updateTextView(TEXT_VIEW_BOTTOM, null == object ? "" : object.toString(), false);
			break;
		default:
			break;
		}
	}
	
	@Override
	protected String getLeftButtonText() {
		return "start";
	}
	
	@Override
	protected void doLeftButtonClick() {
		AccelerometerManager.getInstance().start();
	}
	
	@Override
	protected String getRightButtonText() {
		return "stop";
	}
	
	@Override
	protected void doRightButtonClick() {
		AccelerometerManager.getInstance().stop();
	}
	
	@Override
	protected String getBottomButtonText() {
		return "shake";
	}
	
	@Override
	protected void doBotttomButtonClick() {
		getCustomView().clearAnimation();
		getCustomView().startAnimation(mShakeAnimation);
	}
	
	@Override
	protected int getCustomViewAreaLayoutResource() {
		return R.layout.common_custom_view_layout;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AccelerometerManager.getInstance().start();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		AccelerometerManager.getInstance().stop();
	}
	
	@Override
	protected int getOperationAreaLayoutResource() {
		return R.layout.accelerometer_operation_layout;
	}

	@Override
	protected String getTopButtonText() {
		return "Flicker";
	}
	
	@Override
	protected void doTopButtonClick() {
		getCustomView().clearAnimation();
		getCustomView().startAnimation(mFlickerAnimation);
	}
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		switch (seekBar.getId()) {
		case R.id.amplitude_threshold_seek_bar:
			AccelerometerManager.sMaxShakeAmplitudeThreshold = progress + AccelerometerManager.sMinShakeAmplitudeThreshold;
			break;
		case R.id.vibration_interval_seek_bar:
			AccelerometerManager.sVibrationInterval = progress + sViberationMinInterval;
			break;
		case R.id.vibration_duration_seek_bar:
			AccelerometerManager.sVibrationDuration = progress + sViberationMinDuration;
			break;
		default:
			break;
		}
		refreshView();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		
	}
}

