package com.example.androiddemo.tools;

import java.util.Arrays;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.androiddemo.model.OperationCode;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.SystemServiceUtil;

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

	private static AccelerometerManager accelerometerManager = null;
	private Sensor mAccelerometerSensor = null;
	
	public static synchronized AccelerometerManager getInstance() {
		if (null == accelerometerManager) {
			accelerometerManager = new AccelerometerManager();
		}
		return accelerometerManager;
	}
	
	public void start() {
		SystemServiceUtil.getSensorManager().registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
	}
	
	public void stop() {
		SystemServiceUtil.getSensorManager().unregisterListener(this, mAccelerometerSensor);
	}
	
	private AccelerometerManager() {
		mAccelerometerSensor = SystemServiceUtil.getSensorManager().getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		doCallbacks(OperationCode.OP_CODE_SENSOR_STATE_CHANGED,
				mAccelerometerSensor.getType(), 0,
				AndroidDemoUtil.argumentsToString(Arrays.asList(event.values)), null);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		doCallbacks(OperationCode.OP_CODE_ACCURACY_CHANGED_CHANGED,
				mAccelerometerSensor.getType(), 0,
				AndroidDemoUtil.argumentsToString(sensor.toString(), sensor),
				null);
	}	
}

