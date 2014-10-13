package com.example.androiddemo.activity;

import android.util.DisplayMetrics;

public class TextViewActivity extends TestActivity {
	@Override
	protected String getDisplayString() {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		return String.valueOf(dm.density) + "²âÊÔ×ÖÌå¼ä¾à";
	}
}
