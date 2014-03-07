	package com.campuspo.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.campuspo.BuildConfig;

public class ImageLoader {

	private static final String TAG = ImageLoader.class.getSimpleName();

	private static final int DEFAULT_MEM_CACHE_SIZE = 1024 * 5;// 5Mb

	private LruCache<String, Bitmap> mMemoryCache;//memory cahce 

	private Map<ImageView, String> mImageViews;//for judging the imageview whether has been reused

	private Context mContext;
	private int mDefaultBitmapId;//the id of default bitmap resource

	private static ImageLoader instance;//the single instance

	private ImageLoader(Context ctx) {
		mContext = ctx;
		mMemoryCache = new LruCache<String, Bitmap>(DEFAULT_MEM_CACHE_SIZE);
		mImageViews = Collections
				.synchronizedMap(new WeakHashMap<ImageView, String>());
	}

	public synchronized static ImageLoader getInstance(Context ctx) {

		if (instance == null) {
			instance = new ImageLoader(ctx);
		}

		return instance;
	}

	public void setDefaultBitmap(int resId) {
		mDefaultBitmapId = resId;
	}

	public void loadImage(String url, ImageView iv) {

		Bitmap bm = mMemoryCache.get(url);

		if (bm != null)
			iv.setImageBitmap(bm);
		else {
			mImageViews.put(iv, url);
			
			if(mDefaultBitmapId != 0)
				iv.setImageResource(mDefaultBitmapId);
			BitmapFetcherTask fetcherTask = new BitmapFetcherTask(iv);
			fetcherTask.execute(url);
		}

	}

	private class BitmapFetcherTask extends AsyncTask<String, Void, Bitmap> {

		private String mUrl;
		private WeakReference<ImageView> mImageViewReference;

		public BitmapFetcherTask(ImageView iv) {
			mImageViewReference = new WeakReference<ImageView>(iv);
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			
			if(BuildConfig.DEBUG)
				Log.d(TAG, "doInBackground() called");

			mUrl = params[0];
			
			if(isImageViewReused(mUrl, getImageView()))
				return null;
			
			Bitmap bm = fetchBitmapFromNet(mUrl);
			IconCreator ic = new IconCreator(bm);
			ic.createIcon();
			return ic.getCreatedIcon();
			//return bm;
		}

		@Override
		protected void onPostExecute(Bitmap bm) {
			
			if(bm != null) {
				mMemoryCache.put(mUrl, bm);
				
				ImageView iv = getImageView();
				if(!isImageViewReused(mUrl, iv))
					iv.setImageBitmap(bm);
				
				if(BuildConfig.DEBUG)
					Log.d(TAG, "set bitmap finished");
					
			}
		}

		private ImageView getImageView() {

			return mImageViewReference.get();
		}

	}

	private Bitmap fetchBitmapFromNet(String urlString) {
		
		if(BuildConfig.DEBUG)
			Log.d(TAG, "fetch bitmap begins");
		
		URL url = null;
		HttpURLConnection conn = null;

		BufferedInputStream input = null;
		ByteArrayOutputStream output = null;

		try {
			url = new URL(urlString);
			conn = (HttpURLConnection) url.openConnection();

			input = new BufferedInputStream(conn.getInputStream());
			output = new ByteArrayOutputStream();

			int b = 0;
			while ((b = input.read()) != -1)
				output.write(b);
			
			output.flush();
			byte[] byteArray = output.toByteArray();
			
			if(BuildConfig.DEBUG)
				Log.d(TAG, "fetch bitmap finished");
			
			return BitmapFactory
					.decodeByteArray(byteArray, 0, byteArray.length);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.disconnect();
			try {
				if (input != null)
					input.close();
				if (output != null)
					output.close();
			} catch (IOException e) {}

		}
		
		if(BuildConfig.DEBUG)
			Log.d(TAG, "fetch bitmap failed");

		return null;
	}
	
	/**
	 * Judge whether the imageView in ListView/GridView has been reused
	 * @param url
	 * @param iv
	 * @return
	 */
	private boolean isImageViewReused(String url, ImageView iv) {

		String tag = mImageViews.get(iv);
		if (tag == null || !tag.equals(url))
			return true;
		return false;
	}
}
