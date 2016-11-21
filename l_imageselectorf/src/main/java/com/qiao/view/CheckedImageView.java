package com.qiao.view;

import com.qiao.imageselector.R;
import com.qiao.util.ImageLoadUtil;
import com.qiao.util.LoadLocalImageUtil;
import com.qiao.util.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * 图片类
 * 
 * @author Qiao 2015-3-18
 */
public class CheckedImageView extends FrameLayout {
	public final static String TAG = "CheckedImageView:";
	final int checkedColor = Color.parseColor("#99000000");

	private ImageView imageView; // 图片
	private View checkedView; // 选中框
	private String path;
	private boolean isChecked;

	public CheckedImageView(Context context) {
		super(context);
		initialize();
	}

	public CheckedImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}

	private void initialize() {
		LayoutInflater.from(getContext()).inflate(R.layout.checked_image_item,
				this);
		imageView = (ImageView) findViewById(R.id.image_view);
		checkedView = findViewById(R.id.choosed_view);
		// imageView.getViewTreeObserver().addOnGlobalLayoutListener(new
		// OnGlobalLayoutListener() {
		// @Override
		// public void onGlobalLayout() {
		// imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
		// ViewGroup.LayoutParams vlp = imageView.getLayoutParams();
		// vlp.width = imageView.getWidth();
		// vlp.height = imageView.getHeight();
		// imageView.setLayoutParams(vlp);
		// if (path != null && !path.trim().isEmpty()) {
		// ImageLoadUtil.getInstance(3,ImageLoadUtil.Type.LIFO).loadImage(path,imageView);
		// }
		// }
		// });
	}

	/**
	 * 绘制每个item，这个是从缓存中得到的图片
	 * 
	 * @param path
	 *            每张图片的路径
	 * @param isChecked
	 *            这张图片是否被点击过
	 */
	public void setView(String path, boolean isChecked) {

		// 设置成为默认的图片
		imageView.setImageResource(R.drawable.pic_default);
		if (!Util.isNullOrWhiteSpace(path)) {
			this.path = path;
			setTag(TAG + path);
			Bitmap bm = ImageLoadUtil.getInstance().getBitmapFromLruCache(path);
			if (bm != null) {
				imageView.setImageBitmap(bm);
			}
		}
		setChecked(isChecked);
	}

	/**
	 * 绘制图片
	 */
	public void loadImage() {
		if (!Util.isNullOrWhiteSpace(path)) {
			this.path = path;
			ImageLoadUtil.getInstance(3, ImageLoadUtil.Type.LIFO).loadImage(
					path, imageView);
			
//			LoadLocalImageUtil.getInstance()
//			.displayFromSDCard(LoadLocalImageUtil.getImageContentUri(getContext(), path).toString(), imageView);
		}
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
		if (isChecked) {

			// 设置图片的遮罩层
			imageView.setColorFilter(checkedColor);
			checkedView.setVisibility(VISIBLE);
		} else {
			imageView.setColorFilter(Color.TRANSPARENT);
			checkedView.setVisibility(INVISIBLE);
		}
	}

	public String getPath() {
		return path;
	}

	public boolean isChecked() {
		return isChecked;
	}
}
