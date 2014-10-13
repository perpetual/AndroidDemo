package com.example.androiddemo.activity;

import com.example.androiddemo.R;
import com.example.androiddemo.R.id;
import com.example.androiddemo.R.layout;
import com.example.androiddemo.utils.AndroidUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HandlerActivity extends Activity {
	private DeferWorkHandler mHandler = new DeferWorkHandler(this);

	private Thread mWorkThread = null;
	// UI控件
	private Button mTestBtn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		mHandler.sendTestMessage(1500);
		initData();
		bindView();
	}

	/**
	 * 私有工具函数
	 */
	private void initData() {
		mWorkThread = new Thread(new WorkThreadRunnable(mHandler));
	}

	private void bindView() {
		setContentView(R.layout.handler_layout);
		mTestBtn = (Button) findViewById(R.id.test_btn);
		mTestBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mWorkThread.start();
			}
		});
	}
}

class DeferWorkHandler extends Handler {
	private static final String KEY_MESSAGE = "message";
	private int mCount = 0;
	private HandlerActivity mParentActivity = null;

	public DeferWorkHandler(HandlerActivity parentActivity) {
		mParentActivity = parentActivity;
	}

	private void printMessage(String xyz) {
		AndroidUtils.showToast(xyz);
	}

	public void sendTestMessage(long interval) {
		Message m = obtainMessage();
		prepareMessage(m);
		sendMessageDelayed(m, interval);
	}

	private void prepareMessage(Message m) {
		Bundle b = new Bundle();
		b.putString(KEY_MESSAGE, "Hello World:" + mCount);
		m.setData(b);
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		
		String content = null;
		if (null != (content = msg.getData().getString(KEY_MESSAGE))) {

			String pm = new String("message called:" + mCount + ""
					+ msg.getData().getString(KEY_MESSAGE));
			printMessage(pm);
			if (mCount > 5) {
				return;
			}
			++mCount;
			sendTestMessage(1);
		} else if (null != (content = msg.getData().getString(WorkThreadRunnable.KEY_INFO))) {
			printMessage(content);
		}
	}
}

class WorkThreadRunnable implements Runnable {

	public static final String KEY_INFO = "info";
	private Handler mHandler = null;

	WorkThreadRunnable(Handler handler) {
		mHandler = handler;
	}

	/**
	 * 私有工具函数
	 */
	private void infoMiddle(int count) {
		Message m = mHandler.obtainMessage();
		Bundle bundle = new Bundle();
		bundle.putString(KEY_INFO, "done:" + count);
		m.setData(bundle);
		mHandler.sendMessage(m);
	}

	private void infoStart() {
		Message m = mHandler.obtainMessage();
		Bundle bundle = new Bundle();
		bundle.putString(KEY_INFO, "start");
		m.setData(bundle);
		mHandler.sendMessage(m);
	}

	private void infoFinish() {
		Message m = mHandler.obtainMessage();
		Bundle bundle = new Bundle();
		bundle.putString(KEY_INFO, "finish");
		m.setData(bundle);
		mHandler.sendMessage(m);
	}

	@Override
	public void run() {
		infoStart();
		for (int i = 0; i < 5; ++i) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			infoMiddle(i);
		}
		infoFinish();
	}

}