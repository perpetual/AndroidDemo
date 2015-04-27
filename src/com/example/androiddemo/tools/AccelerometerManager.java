package com.example.androiddemo.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.androiddemo.model.OperationCode;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;
import com.example.androiddemo.utils.MathUtil;
import com.example.androiddemo.utils.SystemServiceUtil;
import com.example.androiddemo.utils.ThreadUtils;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：管理加速度计
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * Gary		2015-4-25		Create		
 * </pre>
 */
public class AccelerometerManager extends CommonCallbacks implements SensorEventListener{
	private static final String TAG = "AccelerometerManager";
	
	private static final float sAlpha = 0.8f;
	private static final int sCorrectionCount = 10;	//修正次数
	private static final float sCorrectionLimit = 0.05f;	//修正限度
	private static final int sCollectionLimit = 3000;		//采集个数限制
	
	public static int sMaxShakeAmplitudeThreshold = 27;	//摇动幅度限制
	public static int sMinShakeAmplitudeThreshold = 3;	//采集幅度限制
	public static int sVibrationInterval = 500;	//振动间隔
	public static int sVibrationDuration = 500;	//振动时长
	
	private static AccelerometerManager accelerometerManager = null;
	private Sensor mAccelerometerSensor = null;
	private Sensor mLinearAccelerometerSensor = null;
	private Float[] mGravity = new Float[]{0f, 0f, 0f};
	private List<Float> mHistoryGravityArray = new ArrayList<Float>(sCorrectionCount);
	private Float[] mLinearAcceleration = new Float[]{0f, 0f, 0f};
	private boolean mHasCorrected = false;	//是否修正
	private Runnable mRunnable = null;
	private List<Float> mAccelerationCollectionList = new ArrayList<Float>();
	
	public static class ValuePair {
		public ValuePair(float minValue, float maxValue) {
			mMinValue = minValue;
			mMaxValue = minValue;
		}
		public float mMaxValue = 0f;
		public float mMinValue = 0f;
	}
	
	public static synchronized AccelerometerManager getInstance() {
		if (null == accelerometerManager) {
			accelerometerManager = new AccelerometerManager();
		}
		return accelerometerManager;
	}
	
	public void start() {
		reset();
		SystemServiceUtil.getSensorManager().registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);	//大概60~70ms采集一次
	}
	
	public void stop() {
		SystemServiceUtil.getSensorManager().unregisterListener(this, mAccelerometerSensor);
		reset();
	}
	
	private AccelerometerManager() {
		mAccelerometerSensor = SystemServiceUtil.getSensorManager()
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//		mAccelerometerSensor = SystemServiceUtil.getSensorManager().getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		mRunnable = new Runnable() {

			@Override
			public void run() {
				int lastPos = Integer.MIN_VALUE;
				int count = 0;
				for (int i = 0; i < mAccelerationCollectionList.size(); ++i) {
					float sample = mAccelerationCollectionList.get(i);
					if (sample > sMaxShakeAmplitudeThreshold) {
						if (lastPos + 1 != i && lastPos + 2 != i) {
							++count;
						}
						lastPos = i;
					}
				}

				SystemServiceUtil.getVibratorService().cancel();
				if (count > 0) {
					doCallbacks(OperationCode.OP_CODE_SHAKE, count, 0,
							AndroidDemoUtil.argumentsToString(count), null);
					long[] vibratorArray = new long[count * 2];
					if (count < 3) {
						for (int i = 0; i < vibratorArray.length; ++i) {
							vibratorArray[i] = 0 == i % 2 ? sVibrationInterval : sVibrationDuration;
						}						
					} else {
						vibratorArray = new long[]{sVibrationInterval, sVibrationDuration};
					}
					LogUtil.d(TAG, "mAccelerationCollectionList", mAccelerationCollectionList);
					SystemServiceUtil.getVibratorService().vibrate(vibratorArray, -1);
				}
				mAccelerationCollectionList.clear();
			}
		};
	}

	private String getRawValue(SensorEvent se) {
		return String.format("Raw values:X-%8.4f, Y-%8.4f, Z-%8.4f" + AndroidDemoUtil.LINE_SEPARATOR, se.values[0], se.values[1], se.values[2]);
	}
	
	private String getGravity() {
		return String.format("Gravity:X-%8.4f, Y-%8.4f, Z-%8.4f" + AndroidDemoUtil.LINE_SEPARATOR, mGravity[0], mGravity[1], mGravity[2]);
	}
	
	private String getLinearAcceleration() {
		return String.format("Linear acceleration:X-%8.4f, Y-%8.4f, Z-%8.4f" + AndroidDemoUtil.LINE_SEPARATOR, mLinearAcceleration[0], mLinearAcceleration[1], mLinearAcceleration[2]);
	}
	
	private void reset() {
		mHistoryGravityArray.clear();
		mAccelerationCollectionList.clear();
		mHasCorrected = false;
		Arrays.fill(mLinearAcceleration, Float.valueOf(0));
		Arrays.fill(mGravity, Float.valueOf(0));
	}

	
	private boolean updateHistoryGravity(Float[] gravityArray) {
		if (null == gravityArray) {
			return false;
		}
		if (mHasCorrected) {
			return true;
		}
		float newInstantaneousGravity = (float) MathUtil.getScaleFloatValue(false, gravityArray[0], gravityArray[1], gravityArray[2]);
		float gravitySum = 0;
		Float lastAverageGravity = 0f;
		Float currentAverageGravity = 0f;
		for (float gravity : mHistoryGravityArray) {
			gravitySum += gravity;
		}
		if (mHistoryGravityArray.size() > 1) {
			lastAverageGravity = gravitySum / mHistoryGravityArray.size();
		}
		if (mHistoryGravityArray.size() >= sCorrectionCount) {
			mHistoryGravityArray = mHistoryGravityArray.subList(mHistoryGravityArray.size() - sCorrectionCount + 1, mHistoryGravityArray.size());
		}
		mHistoryGravityArray.add(newInstantaneousGravity);
		gravitySum = 0;
		for (float gravity : mHistoryGravityArray) {
			gravitySum += gravity;
		}
		currentAverageGravity = gravitySum / mHistoryGravityArray.size();
		return mHasCorrected = Math.abs(currentAverageGravity - lastAverageGravity) < sCorrectionLimit;
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		mGravity[0] = sAlpha * mGravity[0] + (1 - sAlpha) * event.values[0];
		mGravity[1] = sAlpha * mGravity[1] + (1 - sAlpha) * event.values[1];
		mGravity[2] = sAlpha * mGravity[2] + (1 - sAlpha) * event.values[2];
		if (!updateHistoryGravity(mGravity)) {
			return;
		}
		mLinearAcceleration[0] = event.values[0] - mGravity[0];
		mLinearAcceleration[1] = event.values[1] - mGravity[1];
		mLinearAcceleration[2] = event.values[2] - mGravity[2];
		doCallbacks(OperationCode.OP_CODE_SENSOR_STATE_CHANGED,
				mAccelerometerSensor.getType(), 0,
				AndroidDemoUtil.argumentsToString(getRawValue(event),
						getGravity(), getLinearAcceleration()), null);
		float scalAcceleration = MathUtil.getScaleFloatValue(false, mLinearAcceleration[0], mLinearAcceleration[1], mLinearAcceleration[2]);
		LogUtil.d(TAG, "onSensorChanged", scalAcceleration);
		if (scalAcceleration > sMinShakeAmplitudeThreshold) {
			LogUtil.d(TAG, "onSensorChanged", scalAcceleration);
			if (mAccelerationCollectionList.size() >= sCollectionLimit) {
				mAccelerationCollectionList.remove(0);
			}
			mAccelerationCollectionList.add(scalAcceleration);
			if (scalAcceleration > sMaxShakeAmplitudeThreshold) {
				ThreadUtils.runOnMainThread(mRunnable, 1500);
			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		doCallbacks(OperationCode.OP_CODE_ACCURACY_CHANGED_CHANGED, mAccelerometerSensor.getType(),
				0, AndroidDemoUtil.argumentsToString(sensor.toString(), accuracy), null);
	}	
}

