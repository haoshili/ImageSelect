package com.qiao.view;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class PageImageView extends FrameLayout {
	private PhotoView imageView;
	private ProgressBar progressBar;
	private boolean isLoadSuccess;

	/**
	 * 需要隐藏的视图
	 */
	private View hiddenView = null;

	private Activity activity;
	private Context context;

	/**
	 * 是否是全屏显示
	 */
	private boolean isfull = false;

	/**
	 * 隐藏标题头的方法
	 * 
	 * @param context
	 * @param view
	 */
	public PageImageView(Context context, View view, Activity activity) {
		super(context);
		this.activity = activity;
		this.context = context;
		this.hiddenView = view;
		initalize();
	}

	public PageImageView(Context context) {
		super(context);
		this.context = context;
		initalize();
	}

	public PageImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initalize();
	}

	// 图片缩放初始化
	private void initalize() {
		imageView = new PhotoView(getContext());

		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		addView(imageView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		progressBar = new ProgressBar(getContext());
		LayoutParams flp = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		flp.gravity = Gravity.CENTER;
		addView(progressBar, flp);

		imageView.setVisibility(View.INVISIBLE);
		progressBar.setVisibility(View.VISIBLE);
		isLoadSuccess = true;

		imageView.setOnPhotoTapListener(new OnPhotoTapListener() {

			@Override
			public void onPhotoTap(View arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub

				if (hiddenView != null) {

					if (isfull) {
						isfull = false;
						fullTooBar(isfull);
						hiddenView.setVisibility(View.VISIBLE);
					} else {
						isfull = true;
						fullTooBar(isfull);
						hiddenView.setVisibility(View.INVISIBLE);
					}
				}
			}
		});
	}

	public PhotoView getImageView() {
		return imageView;
	}

	public void setImageView(PhotoView imageView) {
		this.imageView = imageView;
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public boolean isLoadSuccess() {
		return isLoadSuccess;
	}

	public void setLoadSuccess(boolean isLoadSuccess) {
		this.isLoadSuccess = isLoadSuccess;
	}

	private void fullTooBar(boolean enable) {
		if (enable) {
			WindowManager.LayoutParams lp = activity.getWindow()
					.getAttributes();
			lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
			activity.getWindow().setAttributes(lp);
			activity.getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		} else {
			WindowManager.LayoutParams attrs = activity.getWindow()
					.getAttributes();
			attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
			activity.getWindow().setAttributes(attrs);

		}
	}
}
