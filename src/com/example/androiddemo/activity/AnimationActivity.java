package com.example.androiddemo.activity;

import android.content.Context;
import android.util.AttributeSet;

import com.example.androiddemo.R;
import com.example.androiddemo.animation.BreatheAniamation;
import com.example.androiddemo.animation.Rotate3dAnimation;
import com.example.androiddemo.animation.ShakeAnimation;


public class AnimationActivity extends DemoSuperActivity {

	private BreatheAniamation mCircleAnimation = null;
	private Rotate3dAnimation mRotate3dAnimation = null;
	private ShakeAnimation mShakeAnimation = null;

	private void startBreatheAnimation() {
		getCustomView().clearAnimation();
		getCustomView().startAnimation(mCircleAnimation);
	}
	
	private void startRotate3dAnimation() {
		getCustomView().clearAnimation();
		getCustomView().startAnimation(mRotate3dAnimation);
	}
	
	private void startShakeAnimation() {
		getCustomView().clearAnimation();
		getCustomView().startAnimation(mShakeAnimation);
	}
	
	@Override
	protected String getLeftButtonText() {
		return "Breathe animation";
	}
	
	@Override
	protected void doLeftButtonClick() {
		startBreatheAnimation();
	}
	
	@Override
	protected String getRightButtonText() {
		return "Rotate3D animation";
	}
	
	@Override
	protected void doRightButtonClick() {
		startRotate3dAnimation();
	}
	
	@Override
	protected String getTopButtonText() {
		return "Shake animation";
	}
	
	@Override
	protected void doTopButtonClick() {
		startShakeAnimation();
	}
	
	@Override
	protected int getCustomViewAreaLayoutResource() {
		return R.layout.common_custom_view_layout;
	}
	
	@Override
	public void initData(Context context, AttributeSet attrs) {
		mCircleAnimation = new BreatheAniamation();
	}
	
	@Override
	public void initView() {
		super.initView();
		mShakeAnimation = new ShakeAnimation(getCustomView());
		mRotate3dAnimation = new Rotate3dAnimation(0, 180, getCustomView().getWidth() / 2,
				getCustomView().getHeight() / 4, 0, true);
	}

}