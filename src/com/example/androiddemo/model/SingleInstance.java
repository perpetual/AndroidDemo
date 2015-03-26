package com.example.androiddemo.model;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-3-26		Create		
 * </pre>
 */
public abstract class SingleInstance<T> {
	private T Instance = null;
		
	public synchronized T getInstance() {
		if (null == Instance) {
			Instance = newInstance();
		}
		return Instance; 
	}

	protected abstract T newInstance();
}

