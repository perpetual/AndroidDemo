package com.example.androiddemo.activity;

import android.app.Activity;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;

import com.example.androiddemo.R;


public class AnimationActivity extends Activity {

	private View mAnimationView = null;
	private View mCircleAnimationView = null;
	private Button mTestButton = null;
	private AlphaAnimation mCircleAnimation = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animation_layout);
		bindView();
		initView();
		updateView();
	}

	private void initView() {
		initCircleAniamation();
	}
	/**
	 * ˽�й��ߺ���
	 */
	private void bindView() {
		mTestButton = (Button) findViewById(R.id.test_btn);
		mTestButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startRotate3dAnimation();
			}
		});
		mAnimationView = findViewById(R.id.animation_view);
		mCircleAnimationView = findViewById(R.id.circle_animation_view);
	}

	private void updateView() {
		mCircleAnimationView.startAnimation(mCircleAnimation);
	}
	
	private void initCircleAniamation() {
		mCircleAnimation = new AlphaAnimation(1.0f, 0.2f);
		mCircleAnimation.setDuration(2000);
		mCircleAnimation.setRepeatMode(Animation.REVERSE);
		AccelerateInterpolator interpolator = new AccelerateInterpolator();
		mCircleAnimation.setInterpolator(interpolator);
		mCircleAnimation.setRepeatCount(Animation.INFINITE);
	}
	
	private void startRotate3dAnimation() {
		Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(0, 180,
				mAnimationView.getWidth() / 2, mAnimationView.getHeight() / 4, 0, true);
		rotate3dAnimation.setDuration(3000);
		mAnimationView.startAnimation(rotate3dAnimation);
	}
}

class Rotate3dAnimation extends Animation {
	// ��ʼ�Ƕ�
	private final float mFromDegrees;
	// �����Ƕ�
	private final float mToDegrees;
	// ���ĵ�
	private final float mCenterX;
	private final float mCenterY;
	private final float mDepthZ;
	// �Ƿ���ҪŤ��
	private final boolean mReverse;
	// ����ͷ
	private Camera mCamera;

	public Rotate3dAnimation(float fromDegrees, float toDegrees, float centerX,
			float centerY, float depthZ, boolean reverse) {
		mFromDegrees = fromDegrees;
		mToDegrees = toDegrees;
		mCenterX = centerX;
		mCenterY = centerY;
		mDepthZ = depthZ;
		mReverse = reverse;
	}

	@Override
	public void initialize(int width, int height, int parentWidth,
			int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
		mCamera = new Camera();
	}

	// ����Transformation
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		final float fromDegrees = mFromDegrees;
		// �����м�Ƕ�
		float degrees = fromDegrees
				+ ((mToDegrees - fromDegrees) * interpolatedTime);

		final float centerX = mCenterX;
		final float centerY = mCenterY;
		final Camera camera = mCamera;

		final Matrix matrix = t.getMatrix();

		camera.save();
		if (mReverse) {
			camera.translate(0.0f, 0.0f, mDepthZ * interpolatedTime);
		} else {
			camera.translate(0.0f, 0.0f, mDepthZ * (1.0f - interpolatedTime));
		}
		camera.rotateY(degrees);
		// ȡ�ñ任��ľ���
		camera.getMatrix(matrix);
		camera.restore();

		matrix.preTranslate(-centerX, -centerY);
		matrix.postTranslate(centerX, centerY);
	}
}