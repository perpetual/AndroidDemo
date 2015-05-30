package com.example.androiddemo.activity;

import android.animation.ObjectAnimator;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;

import com.example.androiddemo.R;
import com.example.androiddemo.animation.BreatheAniamation;
import com.example.androiddemo.animation.RandomShakeAnimation;
import com.example.androiddemo.animation.Rotate3dAnimation;
import com.example.androiddemo.animation.ShakeAnimation;
import com.example.androiddemo.utils.AndroidDemoUtil;
import com.example.androiddemo.utils.ThreadUtils;


public class AnimationActivity extends DemoSuperActivity {

	private BreatheAniamation mCircleAnimation = null;
	private Rotate3dAnimation mRotate3dAnimation = null;
	private ShakeAnimation mShakeAnimation = null;
	private RandomShakeAnimation mRandomShakeAnimation = null;

	private void startBreatheAnimation() {
//		getCustomView().clearAnimation();
//		getCustomView().startAnimation(mCircleAnimation);
		ObjectAnimator translationXAnim = ObjectAnimator.ofFloat(getCustomView(), "translationX",
				AndroidDemoUtil.dip2px(100) * 1f, 0f);
		
		ObjectAnimator translationXAnim2 = ObjectAnimator.ofFloat(getCustomView(), "translationX",
				AndroidDemoUtil.dip2px(100) * 0f, 0.5f);
		translationXAnim.setDuration(3000).start();
		translationXAnim2.setDuration(1000).start();
		ThreadUtils.runOnMainThread(new Runnable() {
			
			@Override
			public void run() {
				ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) getCustomView().getLayoutParams();
				if (null == lp) {
					lp = new LayoutParams(33, 33);
				} else {
					lp.width = lp.height = 33;
				}
				getCustomView().setVisibility(View.GONE);
				ThreadUtils.runOnMainThread(new Runnable() {
					
					@Override
					public void run() {
						getCustomView().setVisibility(View.VISIBLE);
						
					}
				}, 500);
				getCustomView().setLayoutParams(lp);
				
			}
		}, 1500);
	}
	
	private void startRotate3dAnimation() {
		getCustomView().clearAnimation();
		getCustomView().startAnimation(mRotate3dAnimation);
	}
	
	private void startShakeAnimation() {
		getCustomView().clearAnimation();
		getCustomView().startAnimation(mShakeAnimation);
	}
	
	private void startRadomShakeAnimation() {
		getCustomView().clearAnimation();
		getCustomView().startAnimation(mRandomShakeAnimation);
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
	protected String getBottomButtonText() {
		return "Random shake animation";
	}
	
	@Override
	protected void doTopButtonClick() {
		startShakeAnimation();
	}
	
	@Override
	protected void doBotttomButtonClick() {
		startRadomShakeAnimation();
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
		mRandomShakeAnimation = new RandomShakeAnimation();
	}

}