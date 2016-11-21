package com.qiao.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.qiao.imageselector.R;

/**
 * 异步加载本地图片工具类
 * 
 * @author tony
 * 
 */

public class MyLoadLocalImageUtil {

	private MyLoadLocalImageUtil() {
	}

	private static MyLoadLocalImageUtil instance = null;

	public static synchronized MyLoadLocalImageUtil getInstance(Context context) {
		if (instance == null) {
			instance = new MyLoadLocalImageUtil();

			ImageLoader.getInstance().init(getConfig(context));
		}

		return instance;
	}

	protected static ImageLoaderConfiguration getConfig(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				// .memoryCacheExtraOptions(480, 800)
				// max width, max height，即保存的每个缓存文件的最大长宽
				// .discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75,
				// null)
				// Can slow ImageLoader, use it carefully (Better don't use
				// it)/设置缓存的详细信息，最好不要设置这个
				.threadPoolSize(3)
				// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				// You can pass your own memory cache
				// implementation/你可以通过自己的内存缓存实现
				.memoryCacheSize(2 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheFileCount(100)
				// 缓存的文件数量
				// .discCache(new UnlimitedDiscCache(cacheDir))
				// 自定义缓存路径
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(
						new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout
				.writeDebugLogs() // Remove for release app
				.build();// 开始构建

		return config;
	}

	/**
	 * 从内存卡中异步加载本地图片
	 * 
	 * @param uri
	 * @param imageView
	 */
	public void displayFromSDCard(String uri, ImageView imageView) {
		// String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
		ImageLoader.getInstance().displayImage("file://" + uri, imageView);

	}

	/**
	 * 从内存卡中异步加载本地图片
	 * 
	 * @param uri
	 * @param imageView
	 */
	public void displayFromSDCardOptions(String uri, ImageView imageView,
			ImageLoadingListener listener) {
		DisplayImageOptions options;
		options = new DisplayImageOptions.Builder()
				// .showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.resetViewBeforeLoading(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.ARGB_8888).considerExifParams(true)
				.build();

		ImageLoader.getInstance().displayImage("file://" + uri, imageView,
				options, listener);

	}

	/**
	 * 从服务器加载图片
	 * 
	 * @param uri
	 * @param imageView
	 */
	public void displayFromServer(String uri, ImageView imageView,
			ImageLoadingListener listener) {
		DisplayImageOptions options;
		options = new DisplayImageOptions.Builder()
				// .showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.resetViewBeforeLoading(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.ARGB_8888).considerExifParams(true)
				.build();

		ImageLoader.getInstance().displayImage(uri, imageView, options,
				listener);

	}

	/**
	 * 从内存卡中异步加载本地图片
	 * 
	 * @param uri
	 * @param imageView
	 */
	public void displayFromSDCardOptionsHigh(String uri, ImageView imageView) {
		// String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
		DisplayImageOptions options;
		// options = new DisplayImageOptions.Builder()
		// .showImageOnLoading(R.drawable.ic_launcher) // 设置图片在下载期间显示的图片
		// .showImageForEmptyUri(R.drawable.ic_launcher)// 设置图片Uri为空或是错误的时候显示的图片
		// .showImageOnFail(R.drawable.ic_launcher) // 设置图片加载/解码过程中错误时候显示的图片
		// .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
		// // .cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
		// // .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
		// .bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
		// // .delayBeforeLoading(int delayInMillis)//int
		// // delayInMillis为你设置的下载前的延迟时间
		// // 设置图片加入缓存前，对bitmap进行设置
		// // .preProcessor(BitmapProcessor preProcessor)
		// .resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
		// .displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
		// .displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
		// .build();// 构建完成

		options = new DisplayImageOptions.Builder()
				// .showImageOnLoading(R.drawable.ic_launcher)
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(false)
				.cacheOnDisk(false).considerExifParams(true)
				// .displayer(new RoundedBitmapDisplayer(20))
				.build();

		ImageLoader.getInstance().displayImage("file://" + uri, imageView,
				options);

	}

	/**
	 * 从assets文件夹中异步加载图片
	 * 
	 * @param imageName
	 *            图片名称，带后缀的，例如：1.png
	 * @param imageView
	 */
	public void dispalyFromAssets(String imageName, ImageView imageView) {
		// String imageUri = "assets://image.png"; // from assets
		ImageLoader.getInstance().displayImage("assets://" + imageName,
				imageView);
	}

	/**
	 * 从drawable中异步加载本地图片
	 * 
	 * @param imageId
	 * @param imageView
	 */
	public void displayFromDrawable(int imageId, ImageView imageView) {
		// String imageUri = "drawable://" + R.drawable.image; // from drawables
		// (only images, non-9patch)

		// String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
		DisplayImageOptions options;
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_launcher) // 设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_launcher)// 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_launcher) // 设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillis为你设置的下载前的延迟时间
				// 设置图片加入缓存前，对bitmap进行设置
				// .preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
				.displayer(new RoundedBitmapDisplayer(20))// 是否设置为圆角，弧度为多少
				.displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
				.build();// 构建完成

		ImageLoader.getInstance().displayImage("drawable://" + imageId,
				imageView, options);
	}

	/**
	 * 从内容提提供者中抓取图片
	 */
	public void displayFromContent(String uri, ImageView imageView) {
		// String imageUri = "content://media/external/audio/albumart/13"; //
		// from content provider
		ImageLoader.getInstance().displayImage("content://" + uri, imageView);
	}

}
