package com.smartgps;

import java.io.File;

import android.graphics.Bitmap;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		ActiveAndroid.initialize(this);

		File cacheDir = StorageUtils.getCacheDirectory(MyApplication.this);

		DisplayImageOptions imageOptions = new DisplayImageOptions.Builder()
				.cacheOnDisc(true).cacheInMemory(false)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		ImageLoaderConfiguration imageLoader = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).threadPoolSize(5)
				.discCache(new UnlimitedDiscCache(cacheDir))
				.discCacheSize(50 * 1024 * 1024)
				.defaultDisplayImageOptions(imageOptions).build();
		ImageLoader.getInstance().init(imageLoader);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		ActiveAndroid.dispose();
	}
}
