package com.example.androiddemo.activity;

import com.example.androiddemo.R;
import com.example.androiddemo.R.id;
import com.example.androiddemo.R.layout;

import android.app.Activity;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;


public class AnimationActivity extends Activity {

	private View mAnimationView = null;
	private Button mTestButton = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animation_layout);
		bindView();
	}

	/**
	 * 私有工具函数
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
	}

	private void startRotate3dAnimation() {
		Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(0, 180,
				mAnimationView.getWidth() / 2, mAnimationView.getHeight() / 4, 0, true);
		rotate3dAnimation.setDuration(3000);
		mAnimationView.startAnimation(rotate3dAnimation);
	}
}

class Rotate3dAnimation extends Animation {
	// 开始角度
	private final float mFromDegrees;
	// 结束角度
	private final float mToDegrees;
	// 中心点
	private final float mCenterX;
	private final float mCenterY;
	private final float mDepthZ;
	// 是否需要扭曲
	private final boolean mReverse;
	// 摄像头
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

	// 生成Transformation
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		final float fromDegrees = mFromDegrees;
		// 生成中间角度
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
		// 取得变换后的矩阵
		camera.getMatrix(matrix);
		camera.restore();

		matrix.preTranslate(-centerX, -centerY);
		matrix.postTranslate(centerX, centerY);
	}
}