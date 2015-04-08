package com.example.androiddemo.utils;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;

/**
 * <pre>
 * Copyright (C) 1998-2015 TENCENT Inc.All Rights Reserved.
 *
 * Description：
 * 
 * History：
 * 
 * User				Date			Info		Reason
 * garyzhao		2015-4-7		Create		
 * </pre>
 */
public class SystemServiceUtil {

	public static AudioManager getAudioManager() {
		return (AudioManager) AndroidDemoUtil.APPLICATION_CONTEXT.getSystemService(Context.AUDIO_SERVICE);
	}
	
	public static NotificationManager getNotificationManager() {
		return (NotificationManager) AndroidDemoUtil.APPLICATION_CONTEXT.getSystemService(Context.NOTIFICATION_SERVICE);
	}
	
	public static ActivityManager getActivityManager() {
		return (ActivityManager) AndroidDemoUtil.APPLICATION_CONTEXT
				.getSystemService(Context.ACTIVITY_SERVICE);
	}
}

