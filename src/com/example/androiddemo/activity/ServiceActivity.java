package com.example.androiddemo.activity;

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
import com.example.androiddemo.service.BackgroundDemoService;
import com.example.androiddemo.utils.AndroidDemoUtil;

public class ServiceActivity extends SuperActivity {

	private static final String TAG = AndroidDemoUtil
			.getClassName(ServiceActivity.class);
	private Intent mServiceIntent = null;
	private int mCounter = 0;
	private ServiceConnection mServiceConnect = null;
	private ServiceConnection mServiceConnect2 = null;
	private IRemoteService mRemoteService = null;
	private IRemoteService2 mRemoteService2 = null;

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
		AndroidDemoUtil.getThreadSignature();
	}

	@Override
	protected void onResume() {
		super.onResume();
		AndroidDemoUtil.showLongToast("taskID:" + getTaskId());
	}
	
	@Override
	protected void onStop() {
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

		mServiceIntent = new Intent(this, BackgroundDemoService.class);

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

				mServiceIntent.putExtra(BackgroundDemoService.EXTRAS_KEY,
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
					AndroidDemoUtil.showToast(String.valueOf(val));
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
					AndroidDemoUtil.showToast(String.valueOf(mRemoteService2
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
