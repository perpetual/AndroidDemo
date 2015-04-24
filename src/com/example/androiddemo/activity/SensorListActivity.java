package com.example.androiddemo.activity;

import java.util.Collection;


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
public class SensorListActivity extends SuperListActivity {

	private Collection<String> getSensorList() {
		return super.getDataSource();
	}
	
	@Override
	protected Collection<String> getDataSource() {
		return getSensorList();
	}
}

