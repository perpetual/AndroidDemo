package com.example.androiddemo.activity;

import com.example.androiddemo.R;
import com.example.androiddemo.R.id;
import com.example.androiddemo.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewFlipper;

public class FlipperActivity extends Activity implements OnClickListener {
	private Button mShowNextBtn = null;
	private ViewFlipper mFlipperView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flipper_layout);
		BindUI();
	}

	/**
	 * ˽�й��ߺ���
	 */
	private void BindUI() {
		mFlipperView = (ViewFlipper) findViewById(R.id.flipper_view);
		mShowNextBtn = (Button) findViewById(R.id.show_next);
		mShowNextBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.show_next:
			mFlipperView.showNext();
			break;
		default:
			break;
		}

	}
}
