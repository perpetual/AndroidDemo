package com.example.androiddemo.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;

import com.example.androiddemo.service.BackgroundDemoService;
import com.example.androiddemo.service.IRemoteDemoService;
import com.example.androiddemo.service.IRemoteDemoService2;
import com.example.androiddemo.service.RemoteDemoService1;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;

public class ServiceActivity extends SuperActivity {

	private static final String TAG = AndroidDemoUtil
			.getClassName(ServiceActivity.class);
	private Intent mLocalServiceIntent = null;
	private int mCounter = 0;
	private static ServiceConnection mRemoteServiceConnection = null;
	private ServiceConnection mRemoteServiceConnection2 = null;
	private IRemoteDemoService mRemoteDemoService = null;
	private IRemoteDemoService2 mRemoteDemoService2 = null;

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
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		LogUtil.d(TAG, "onNewIntent", intent);
	}

	@Override
	public void initData(Context context, AttributeSet attrs) {
		AndroidDemoUtil.getThreadSignature();
		
		mLocalServiceIntent = new Intent(this, BackgroundDemoService.class);

		mRemoteServiceConnection = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {
				Log.d(TAG, "onServiceDisconnected");
				mRemoteDemoService = null;
				updateTextView(TEXT_VIEW_TOP, AndroidDemoUtil.converIndeterminateArgumentsToString(
						"onServiceDisconnected1", "ComponentName", name), true);
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				Log.d(TAG, "onServiceConnected");
				mRemoteDemoService = IRemoteDemoService.Stub.asInterface(service);
				double getQuote = 0.0;
				try {
					getQuote = mRemoteDemoService.getQuote("xxx");
				} catch (RemoteException e) {
					LogUtil.d(TAG, e);
				}
				updateTextView(TEXT_VIEW_TOP, AndroidDemoUtil.converIndeterminateArgumentsToString(
						"onServiceConnected1", "ComponentName", name, "getQuote", getQuote), true);
			}
		};

		mRemoteServiceConnection2 = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {
				Log.d(TAG, "onServiceDisconnected");
				mRemoteDemoService2 = null;
				updateTextView(TEXT_VIEW_TOP, AndroidDemoUtil.converIndeterminateArgumentsToString(
						"onServiceDisconnected2", "ComponentName", name), true);
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				Log.d(TAG, "onServiceConnected");
				updateTextView(TEXT_VIEW_TOP, AndroidDemoUtil.converIndeterminateArgumentsToString(
						"onServiceConnected2", "ComponentName", name), true);
			}
		};
	}

	@Override
	protected String getTopButtonText() {
		return "start local service";
	}
	
	@Override
	protected void doTopButtonClick() {
		mLocalServiceIntent.putExtra(BackgroundDemoService.EXTRAS_KEY,
				++mCounter);
		startService(mLocalServiceIntent);
	}
	
	
	@Override
	protected String getBottomButtonText() {
		return "stop local service";
	}
	
	@Override
	protected void doBotttomButtonClick() {
		stopService();
	}
	
	@Override
	protected String getLeftButtonText() {
		return "start remote service";
	}
	
	@Override
	protected void doLeftButtonClick() {
		if (null == mRemoteDemoService) {
			Intent intent = new Intent(AndroidDemoUtil.APPLICATION_CONTEXT, RemoteDemoService1.class);
			bindService(intent, mRemoteServiceConnection, BIND_AUTO_CREATE);
		}
	}
	
	@Override
	protected String getRightButtonText() {
		return "stop remote service";
	}
	
	@Override
	protected void doRightButtonClick() {
		if (null != mRemoteDemoService) {
			unbindService(mRemoteServiceConnection);
			mRemoteDemoService = null;
		}
	}

//	private void bindUI() {
//		mCallBtn = (Button) findViewById(R.id.call_service);
//		mCallBtn.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				try {
//					double val = mRemoteService.getQuote("ANDROID");
//					AndroidDemoUtil.showToast(String.valueOf(val));
//				} catch (RemoteException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//
//		mBindBtn2 = (ToggleButton) findViewById(R.id.bind_service2);
//		mBindBtn2.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				ToggleButton btn = (ToggleButton) v;
//				if (btn.isChecked()) {
//					bindService(new Intent(IRemoteService2.class.getName()),
//							mServiceConnect2, BIND_AUTO_CREATE);
//				} else {
//					unbindService(mServiceConnect2);
//					mCallBtn.setEnabled(false);
//				}
//			}
//		});
//
//		mCallBtn2 = (Button) findViewById(R.id.call_service2);
//		mCallBtn2.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				try {
//					Person person = new Person();
//					person.mAge = 27;
//					person.mName = "garyzhao";
//					AndroidDemoUtil.showToast(String.valueOf(mRemoteService2
//							.getQuote("ANDROID", person)));
//				} catch (RemoteException e) {
//					e.printStackTrace();
//				}
//			}
//		});
//		
//		mTestBtn = (Button)findViewById(R.id.test_btn);
//		mTestBtn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(ServiceActivity.this, MainListActivity.class);
//				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//				startActivity(intent);
//			}
//		});
//	}

	private void stopService() {
		boolean ret = stopService(mLocalServiceIntent);
		updateButton(TEXT_VIEW_TOP, AndroidDemoUtil.converIndeterminateArgumentsToString("Stop successful", ret));
	}
}
