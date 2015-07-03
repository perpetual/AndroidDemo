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
import com.example.androiddemo.service.IRemoteDemoService2;
import com.example.androiddemo.tools.RemoteDemoManager;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.LogUtil;

public class ServiceActivity extends DemoSuperActivity {

	private static final String TAG = AndroidDemoUtil
			.getClassName(ServiceActivity.class);
	private Intent mLocalServiceIntent = null;
	private int mCounter = 0;
	private ServiceConnection mRemoteServiceConnection2 = null;
	private IRemoteDemoService2 mRemoteDemoService2 = null;

	private void stopService() {
		boolean ret = stopService(mLocalServiceIntent);
		updateButton(TEXT_VIEW_TOP, AndroidDemoUtil.argumentsToString("Stop successful", ret));
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

		mRemoteServiceConnection2 = new ServiceConnection() {

			@Override
			public void onServiceDisconnected(ComponentName name) {
				Log.d(TAG, "onServiceDisconnected");
				mRemoteDemoService2 = null;
				updateTextView(TEXT_VIEW_TOP, AndroidDemoUtil.argumentsToString(
						"onServiceDisconnected2", "ComponentName", name), true);
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				Log.d(TAG, "onServiceConnected");
				updateTextView(TEXT_VIEW_TOP, AndroidDemoUtil.argumentsToString(
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
		if (RemoteDemoManager.getInstance().isServiceBind()) {
			RemoteDemoManager.getInstance().unbindService();
		} else {
			RemoteDemoManager.getInstance().bindService();
		}
	}
	
	@Override
	protected String getRightButtonText() {
		return "use remote service";
	}
	
	@Override
	protected void doRightButtonClick() {
		try {
			updateTextView(
					TEXT_VIEW_TOP,
					String.valueOf(RemoteDemoManager.getInstance().getRemoteDemoService1()
							.getQuote("")), true);
		} catch (RemoteException e) {
			updateTextView(TEXT_VIEW_BOTTOM, e.toString(), false);
		}
	}
}

class ServiceClient implements IBinder.DeathRecipient {

	@Override
	public void binderDied() {
		
	}
	
}
