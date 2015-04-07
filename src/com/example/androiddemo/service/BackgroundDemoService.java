package com.example.androiddemo.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.example.androiddemo.R;
import com.example.androiddemo.activity.FirstActivity;

/**
 * 
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
public class BackgroundDemoService extends BaseService {
	private static final String TAG = BackgroundDemoService.class.getSimpleName();

	private class ServiceRunnable implements Runnable {

		private int mCounter = -1;

		public ServiceRunnable(int counter) {
			mCounter = counter;
		}

		@Override
		public void run() {

			try {
				Log.d(TAG, "" + Thread.currentThread().getId()
						+ "Sleeping for 2 seconds. counter = " + mCounter);
				Thread.sleep(2000);
				Log.d(TAG, "Waking up");
			} catch (Exception e) {
			}
		}
	}

	private NotificationManager mNotificationMananger = null;
	public static final String EXTRAS_KEY = "counter";
	private ThreadGroup mMyThreads = new ThreadGroup("ServiceWorker");

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, TAG + ":Current process id:" + android.os.Process.myPid());
	}

	private void displayNotificationMessage(String message) {
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				FirstActivity.class), 0);
		Notification notification = new Notification(R.drawable.ic_launcher, message,
				System.currentTimeMillis());
		notification.setLatestEventInfo(this, "Background Service", message, pendingIntent);
		// Notification.Builder notificationBuilder = new
		// Notification.Builder(this);
		// notificationBuilder.setWhen(System.currentTimeMillis());
		// notificationBuilder.setSmallIcon(R.drawable.ic_launcher);
		// notificationBuilder.setContentText("Background  service is running");
		// notificationBuilder.setContentTitle("Background Service");
		// notificationBuilder.setContentText(message);
		// notificationBuilder.setContentIntent(pendingIntent);
		// notification = notificationBuilder.build();
		notification.flags = Notification.FLAG_NO_CLEAR;
		mNotificationMananger.notify(0, notification);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		int counter = intent.getExtras().getInt(EXTRAS_KEY);
		new Thread(mMyThreads, new ServiceRunnable(counter)).start();
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		mMyThreads.interrupt();
		mNotificationMananger.cancelAll();
		super.onDestroy();
	}
}