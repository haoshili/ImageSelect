package com.qiao.activity;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.qiao.fragment.ImageSelectorFragment;
import com.qiao.fragment.ImageSelectorFragment.TakePictureListenser;
import com.qiao.util.FileUtils;

public class DemoActivity extends BaseImageSelectorActivity {
	protected View containerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ImageLoader.getInstance().init(
				ImageLoaderConfiguration.createDefault(this));

		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
						| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

		containerView = new FrameLayout(this);
		containerView.setId(android.R.id.content);
		setContentView(containerView);

		File cameraFile = FileUtils.createCameraFile(this);
		cameraPath = cameraFile.getAbsolutePath();
		initFragment();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ImageLoader.getInstance().clearMemoryCache();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_CAMERA) {
				sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
						Uri.fromFile(new File(cameraPath))));
			}
		}
	}

	public void fins() {
		Intent intent = getIntent();
		intent.putExtra("ha", "doubi");
		setResult(100, intent);
		finish();
	}

	private String cameraPath;
	public final static int REQUEST_IMAGE = 66;
	public static final int REQUEST_CAMERA = 65;

	private void initFragment() {
		Intent intent = getIntent();
		Class<?> fragmentClass = (Class<?>) intent
				.getSerializableExtra("class");
		if (fragmentClass == null) {
			fragmentClass = ImageSelectorFragment.class;

		}
		try {
			Fragment fragment = (Fragment) fragmentClass.newInstance();
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			ft.add(android.R.id.content, fragment);
			ft.commitAllowingStateLoss();

			inItListenser((ImageSelectorFragment) fragment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化相机监听者
	 * 
	 * @param fragment
	 */
	private void inItListenser(ImageSelectorFragment fragment) {
		fragment.setPictureListenser(new TakePictureListenser() {
			@Override
			public void takePicture() {
				startCamera();
			}

			@Override
			public void onOkClick() {
				fins();
			}
		});
	}

	/**
	 * 打开系统的照相机
	 */
	private void startCamera() {
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (cameraIntent.resolveActivity(getPackageManager()) != null) {
			File cameraFile = FileUtils.createCameraFile(this);
			cameraPath = cameraFile.getAbsolutePath();
			cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(cameraFile));
			startActivityForResult(cameraIntent, DemoActivity.REQUEST_CAMERA);
		}
	}

	public static Intent makeIntent(Context context,
			Class<? extends Fragment> clazz) {
		Intent intent = new Intent(context, DemoActivity.class);
		intent.putExtra("class", clazz);
		return intent;
	}
}
