package com.qiao.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.qiao.imageselector.R;
import com.qiao.util.ImageLoadUtil.ImageLoadListener;
import com.qiao.util.MyLoadLocalImageUtil;
import com.qiao.view.MyVewPager;
import com.qiao.view.PageImageView;

/**
 * Created by bingosoft on 15/4/17.
 */
public class ImageBrowserFragment extends Fragment {
	private Activity activity;
	private View backView;
	private int currIndex, count;
	private List<String> dataList;
	private ViewPager viewPager;
	private ImagePagerAdapter adapter = new ImagePagerAdapter();

	/**
	 * 当前的位置
	 */
	private TextView tv_curPosition;

	/**
	 * 需要隐藏的actionBar
	 */
	private LinearLayout re_action_bar;

	/**
	 * 点击下载图片
	 */
	private RelativeLayout downland_image;

	@Override
	public void onAttach(Activity activity) {
		this.activity = activity;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		initData();
		return rootView = inflater.inflate(R.layout.activity_image_browser,
				null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initViews();
		initListeners();
	}

	private void initData() {
		Intent intent = activity.getIntent();
		currIndex = intent.getIntExtra("currIndex", 0);
		dataList = (List<String>) intent.getSerializableExtra("dataList");
		if (dataList == null) {
			dataList = new ArrayList<String>();
		}
		count = dataList.size();
	}

	protected View rootView;

	protected View findViewById(int viewId) {
		return rootView.findViewById(viewId);
	}

	private void initViews() {
		tv_curPosition = (TextView) findViewById(R.id.tv_curPosition);
		backView = findViewById(R.id.re_menu_home);
		downland_image = (RelativeLayout) findViewById(R.id.downland_image);
		viewPager = (MyVewPager) findViewById(R.id.view_pager);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(currIndex);
		tv_curPosition.setText((currIndex + 1) + "/" + dataList.size());
		re_action_bar = (LinearLayout) findViewById(R.id.re_action_bar);
	}

	@SuppressWarnings("deprecation")
	private void initListeners() {
		backView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				activity.onBackPressed();
			}
		});

		downland_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SaveImageToSysAlbum();
			}
		});

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				tv_curPosition.setText((arg0 + 1) + "/" + dataList.size());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	/**
	 * 将当前的图片存储到相册
	 */
	private void SaveImageToSysAlbum() {
		if (true) {
			BitmapDrawable bmpDrawable = (BitmapDrawable) adapter.currView
					.getImageView().getDrawable();
			Bitmap bmp = bmpDrawable.getBitmap();
			if (bmp != null) {
				try {
					ContentResolver cr = activity.getContentResolver();
					String url = MediaStore.Images.Media.insertImage(cr, bmp,
							String.valueOf(System.currentTimeMillis()), "");
					Toast.makeText(activity, "success", Toast.LENGTH_SHORT)
							.show();

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {

			}
		} else {

		}
	}

	private PageImageView convertView;

	/**
	 * 显示图片的图片适配器
	 */
	class ImagePagerAdapter extends PagerAdapter {
		protected PageImageView currView;

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void destroyItem(ViewGroup collection, int position, Object view) {
			collection.removeView((View) view);
		}

		@Override
		public void setPrimaryItem(ViewGroup container, int position,
				Object object) {
			super.setPrimaryItem(container, position, object);
			if (currIndex == position && currView != null)
				return;
			if (currView != null) {
				currView.getImageView().reset();
			}
			currIndex = position;
			currView = (PageImageView) object;
			if (!currView.isLoadSuccess()) {
				Toast.makeText(activity, "图片加载失败,请确认图片可用!", Toast.LENGTH_SHORT)
						.show();
			}
		}

		@Override
		public Object instantiateItem(ViewGroup collection, int position) {
			final PageImageView convertView = new PageImageView(activity,
					re_action_bar, activity);
			// convertView = new PageImageView(activity, re_action_bar,
			// activity);
			final String path = dataList.get(position);
			collection.addView(convertView, 0);

			// 这样调用会显示的图片更清晰
			convertView.getImageView().post(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					MyLoadLocalImageUtil.getInstance(activity)
							.displayFromServer(path,
									convertView.getImageView(),
									getListenser(convertView));
				}
			});
			return convertView;
		}

		private ImageLoadingListener listener = new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String imageUri, View view) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onLoadingComplete(String imageUri, View view,
					Bitmap loadedImage) {
				// 把那个bar显示出来
				convertView.setLoadSuccess(true);
				convertView.getProgressBar().setVisibility(View.GONE);
				convertView.getImageView().setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				// TODO Auto-generated method stub

			}
		};

		private ImageLoadingListener getListenser(
				final PageImageView pageImageView) {

			return new ImageLoadingListener() {

				@Override
				public void onLoadingStarted(String imageUri, View view) {

				}

				@Override
				public void onLoadingFailed(String imageUri, View view,
						FailReason failReason) {

				}

				@Override
				public void onLoadingComplete(String imageUri, View view,
						Bitmap loadedImage) {
					pageImageView.getProgressBar().setVisibility(View.GONE);
					pageImageView.getImageView().setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingCancelled(String imageUri, View view) {

				}
			};
		}

		private ImageLoadListener bindListener(final PageImageView pageImageView) {
			return new ImageLoadListener() {

				@Override
				public void onLoadComplete(ImageView imageView, Bitmap bitmap,
						boolean isSuccess) {
					pageImageView.setLoadSuccess(isSuccess);
					pageImageView.getProgressBar().setVisibility(View.GONE);
					pageImageView.getImageView().setVisibility(View.VISIBLE);
				}
			};
		}

	}

}
