package com.example.androiddemo.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ToggleButton;

import com.example.androiddemo.IRemoteService;
import com.example.androiddemo.IRemoteService2;
import com.example.androiddemo.R;
import com.example.androiddemo.model.Person;
import com.example.androiddemo.service.BaseService;
import com.example.androiddemo.utils.AndroidUtils;

public class ServiceActivity extends Activity {
	public static class BackgroundService extends BaseService {
		private static final String TAG = AndroidUtils
				.getClassName(BackgroundService.class);

		private NotificationManager mNotificationMananger = null;
		public static final String EXTRAS_KEY = "counter";
		private ThreadGroup mMyThreads = new ThreadGroup("ServiceWorker");

		@Override
		public void onCreate() {
			super.onCreate();
			Log.d(TAG,
					TAG + ":Current process id:" + android.os.Process.myPid());
		}

		private void displayNotificationMessage(String message) {
			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
					new Intent(this, FirstActivity.class), 0);
			Notification notification = new Notification(
					R.drawable.ic_launcher, message, System.currentTimeMillis());
			notification.setLatestEventInfo(this, "Background Service",
					message, pendingIntent);
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
		public boolean stopService(Intent name) {
			return super.stopService(name);
		}

		@Override
		public IBinder onBind(Intent intent) {
			return null;
		}

		@Override
		public void onDestroy() {
			mMyThreads.interrupt();
			mNotificationMananger.cancelAll();
			super.onDestroy();
		}

	}

	public static class RemoteService extends Service {

		private final String TAG = AndroidUtils
				.getClassName(RemoteService.class);

		public class RemoteServiceImpl extends IRemoteService.Stub {

			@Override
			public double getQuote(String ticker) throws RemoteException {
				return 3.14;
			}
		}

		@Override
		public IBinder onBind(Intent intent) {
			return new RemoteServiceImpl();
		}

	}

	public static class RemoteService2 extends Service {

		private NotificationManager mNotificationMananger = null;

		private void displayNotificationMessage(String message) {
			Intent intent = new Intent(this, FirstActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			intent.addCategory(Intent.CATEGORY_LAUNCHER);
//			intent.setAction(Intent.ACTION_MAIN);
			PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);
			Notification notification = new Notification(
					R.drawable.ic_launcher, message, System.currentTimeMillis());
			notification.setLatestEventInfo(this, "Background Service",
					message, pendingIntent);
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

		private class RemoteService2Impl extends IRemoteService2.Stub {

			@Override
			public String getQuote(String ticker, Person requester)
					throws RemoteException {
				return ticker + ":" + requester.mName;
			}
		}

		@Override
		public void onCreate() {
			super.onCreate();

			mNotificationMananger = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			displayNotificationMessage("RemoteService2 is running");
		}

		@Override
		public IBinder onBind(Intent intent) {
			return new RemoteService2Impl();
		}

		@Override
		public void onDestroy() {
			mNotificationMananger.cancelAll();
			super.onDestroy();
		}
	}

	private static final String TAG = AndroidUtils
			.getClassName(ServiceActivity.class);
	private Intent mServiceIntent = null;
	private int mCounter = 0;
	private ServiceConnection mServiceConnect = null;
	private ServiceConnection mServiceConnect2 = null;
	private IRemoteService mRemoteService = null;
	private IRemoteService2 mRemoteService2 = null;
	// UI控件
	private Button mStartBtn = null;
	private Button mStopBtn = null;
	private ToggleButton mBindBtn = null;
	private Button mCallBtn = null;
	private ToggleButton mBindBtn2 = null;
	private Button mCallBtn2 = null;
	private Button mTestBtn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, TAG + ":Current process id:" + android.os.Process.myPid());
		initData();
		bindUI();
		AndroidUtils.getThreadSignature();
	}

	@Override
	protected void onResume() {
		super.onResume();
		AndroidUtils.showLongToast("taskID:" + getTaskId());
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopService();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	}

	/**
	 * 私有工具函数
	 */
	private void initData() {

		mServiceIntent = new Intent(this, BackgroundService.class);

		mServiceConnect = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {
				Log.d(TAG, "onServiceDisconnected");
				mRemoteService = null;
				mBindBtn.setChecked(false);
				mCallBtn.setEnabled(false);
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				Log.d(TAG, "onServiceConnected");
				mRemoteService = IRemoteService.Stub.asInterface(service);
				mBindBtn.setChecked(true);
				mCallBtn.setEnabled(true);
			}
		};

		mServiceConnect2 = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {
				Log.d(TAG, "onServiceDisconnected");
				mRemoteService2 = null;
				mBindBtn2.setChecked(false);
				mCallBtn2.setEnabled(false);
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				Log.d(TAG, "onServiceConnected");
				mRemoteService2 = IRemoteService2.Stub.asInterface(service);
				mBindBtn2.setChecked(true);
				mCallBtn2.setEnabled(true);
			}
		};
	}

	private void bindUI() {
		setContentView(R.layout.service_layout);
		mStartBtn = (Button) findViewById(R.id.start_service);
		mStartBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mServiceIntent.putExtra(BackgroundService.EXTRAS_KEY,
						++mCounter);
				startService(mServiceIntent);
			}
		});

		mStopBtn = (Button) findViewById(R.id.stop_service);
		mStopBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				stopService();
			}
		});

		mBindBtn = (ToggleButton) findViewById(R.id.bind_service);
		mBindBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ToggleButton btn = (ToggleButton) v;
				if (btn.isChecked()) {
					bindService(new Intent(IRemoteService.class.getName()),
							mServiceConnect, BIND_AUTO_CREATE);
				} else {
					unbindService(mServiceConnect);
					mCallBtn.setEnabled(false);
				}
			}
		});

		mCallBtn = (Button) findViewById(R.id.call_service);
		mCallBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					double val = mRemoteService.getQuote("ANDROID");
					AndroidUtils.showToast(String.valueOf(val));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});

		mBindBtn2 = (ToggleButton) findViewById(R.id.bind_service2);
		mBindBtn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ToggleButton btn = (ToggleButton) v;
				if (btn.isChecked()) {
					bindService(new Intent(IRemoteService2.class.getName()),
							mServiceConnect2, BIND_AUTO_CREATE);
				} else {
					unbindService(mServiceConnect2);
					mCallBtn.setEnabled(false);
				}
			}
		});

		mCallBtn2 = (Button) findViewById(R.id.call_service2);
		mCallBtn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Person person = new Person();
					person.mAge = 27;
					person.mName = "garyzhao";
					AndroidUtils.showToast(String.valueOf(mRemoteService2
							.getQuote("ANDROID", person)));
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		
		mTestBtn = (Button)findViewById(R.id.test_btn);
		mTestBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ServiceActivity.this, MainListActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
	}

	private void stopService() {

		if (stopService(mServiceIntent)) {
			Log.d(TAG, "Stop successful");
		} else {
			Log.d(TAG, "Stop unsuccessful");
		}

		if (mBindBtn.isChecked()) {
			unbindService(mServiceConnect);
		}
	}
}

class ServiceRunnable implements Runnable {

	private static final String TAG = AndroidUtils
			.getClassName(ServiceRunnable.class);
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
