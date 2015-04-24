package com.example.androiddemo.activity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import android.hardware.Sensor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.SystemServiceUtil;


/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-4-24		Create		
 * </pre>
 */
public class SensorListActivity extends SuperListActivity implements OnItemClickListener {

	private Collection<String> getSensorList() {
		List<Sensor> sensorList = SystemServiceUtil.getSensorManager().getSensorList(Sensor.TYPE_ALL);
		Collection<String> dataSource = new HashSet<String>();
		String sensorType = "UNKNOW";
		for (Sensor sensor : sensorList) {
			switch (sensor.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				sensorType = "TYPE_ACCELEROMETER";
				break;
			case Sensor.TYPE_AMBIENT_TEMPERATURE:
				sensorType = "TYPE_AMBIENT_TEMPERATURE";
				break;
			case Sensor.TYPE_GRAVITY:
				sensorType = "TYPE_GRAVITY";				
				break;
			case Sensor.TYPE_LIGHT:
				sensorType = "TYPE_LIGHT";
				break;
			case Sensor.TYPE_LINEAR_ACCELERATION:
				sensorType = "TYPE_LINEAR_ACCELERATION";
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				sensorType = "TYPE_MAGNETIC_FIELD";
				break;
			case Sensor.TYPE_ORIENTATION:
				sensorType = "TYPE_ORIENTATION";
				break;
			case Sensor.TYPE_PRESSURE:
				sensorType = "TYPE_PRESSURE";
				break;
			case Sensor.TYPE_PROXIMITY:
				sensorType = "TYPE_PROXIMITY";
				break;
			case Sensor.TYPE_RELATIVE_HUMIDITY:
				sensorType = "TYPE_RELATIVE_HUMIDITY";
				break;
			case Sensor.TYPE_ROTATION_VECTOR:
				sensorType = "TYPE_ROTATION_VECTOR";
				break;
			case Sensor.TYPE_TEMPERATURE:
				sensorType = "TYPE_TEMPERATURE";
				break;
			case Sensor.TYPE_GYROSCOPE:
				sensorType = "TYPE_GYROSCOPE";
				break;
			default:
				break;
			}
			dataSource.add(AndroidDemoUtil.converIndeterminateArgumentsToString(sensor.getName(), sensorType,
					sensor.getVendor(), sensor.getVersion(), sensor.getResolution(),
					sensor.getMaximumRange(), sensor.getPower()));
		}
		return dataSource;
	}
	
	@Override
	protected Collection<String> getDataSource() {
		return getSensorList();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (position) {
		case 0:
			
			break;

		default:
			break;
		}
	}
}

