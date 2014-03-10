package com.campuspo.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.campuspo.BuildConfig;
import com.campuspo.app.CampusPoApplication;

/**
 *
 * @author ji.jiaxiang
 *
 *	This is a class for loading image asynchronized with memory cache and disk cache
 *  The disk cache have to clear manually by call "clearDiskCache"
 */

public class ImageLoader {

	private static final String TAG = ImageLoader.class.getSimpleName();

	private static final int DEFAULT_MEM_CACHE_SIZE = 1024 * 5;// 5Mb

	private LruCache<String, Bitmap> mMemoryCache;// memory cahce

	private DiskCache mDiskCache;
	private Map<ImageView, String> mImageViews;// for judging the imageview
												// whether has been reused

	private static final int RETRY_TIMES = 3;

	private Context mContext;
	private int mDefaultBitmapId;// the id of default bitmap resource

	private static ImageLoader instance;// the single instance

	private Resources mResources;

	private ImageLoader(Context ctx) {
		mContext = ctx;
		mMemoryCache = new LruCache<String, Bitmap>(DEFAULT_MEM_CACHE_SIZE);
		mImageViews = Collections
				.synchronizedMap(new WeakHashMap<ImageView, String>());
		mDiskCache = DiskCache.getInstance(mContext);
		mResources = CampusPoApplication.getAppContext().getResources();
	}
	
	/**
	 * 
	 * @param ctx
	 * @return return the singleton ImageLoader
	 */
	public synchronized static ImageLoader getInstance(Context ctx) {

		if (instance == null) {
			instance = new ImageLoader(ctx);
		}

		return instance;
	}
	/*
	 * set Default Bitmap for the target ImageView
	 */
	public void setDefaultBitmap(int resId) {
		mDefaultBitmapId = resId;
	}

	public void loadImage(String url, ImageView iv) {

		Bitmap bm = mMemoryCache.get(url);

		if (bm != null)
			iv.setImageBitmap(bm);
		else {
			mImageViews.put(iv, url);

			if (mDefaultBitmapId != 0)
				iv.setImageResource(mDefaultBitmapId);
			BitmapFetcherTask fetcherTask = new BitmapFetcherTask(iv);
			fetcherTask.execute(url);
		}

	}

	/*
	 * Clear bitmap cache in the disk;
	 */
	public void clearDiskCache() {
		mDiskCache.clear();
	}
	
	
	/**
	 * Decode and sample down a bitmap from a file input stream to the requested
	 * 
	 * @param res 
	 * 			The context resources
	 * @param resId 
	 * 			The image id
	 * @param reqWidth 
	 * 			The requested width of the resulting bitmap
	 * @param reqHeight
	 * 			Thre requested height of the result bitmap
	 * @return	A bitmap sampled down from the original with the same aspect
	 *         ratio and dimensions that are equal to or greater than the
	 *         requested width and height		
	 */
	public Bitmap decodeSampledBitmapFromResId(Resources res, int resId, int reqWidth,
			int reqHeight) {

		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inJustDecodeBounds = false;

		BitmapFactory.decodeResource(res, resId, option);

		option.inSampleSize = calculateInSampleSize(option, reqWidth, reqHeight);

		option.inJustDecodeBounds = true;

		return BitmapFactory.decodeResource(res, resId, option);

	}


	/**
	 * Decode and sample down a bitmap from a file input stream to the requested
	 * width and height.
	 * 
	 * @param fileDescriptor
	 *            The file descriptor to read from
	 * @param reqWidth
	 *            The requested width of the resulting bitmap
	 * @param reqHeight
	 *            The requested height of the resulting bitmap
	 * @return A bitmap sampled down from the original with the same aspect
	 *         ratio and dimensions that are equal to or greater than the
	 *         requested width and height
	 */
	public Bitmap decodeSampledBitmapFromDescriptor(
			FileDescriptor fileDescriptor, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory
				.decodeFileDescriptor(fileDescriptor, null, options);
	}

	/**
	 * Calculate an inSampleSize for use in a {@link BitmapFactory.Options}
	 * object when decoding bitmaps using the decode* methods from
	 * {@link BitmapFactory}. This implementation calculates the closest
	 * inSampleSize that will result in the final decoded bitmap having a width
	 * and height equal to or larger than the requested width and height. This
	 * implementation does not ensure a power of 2 is returned for inSampleSize
	 * which can be faster when decoding but results in a larger bitmap which
	 * isn't as useful for caching purposes.
	 * 
	 * @param options
	 *            An options object with out* params already populated (run
	 *            through a decode* method with inJustDecodeBounds==true
	 * @param reqWidth
	 *            The requested width of the resulting bitmap
	 * @param reqHeight
	 *            The requested height of the resulting bitmap
	 * @return The value to be used for inSampleSize
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee a final image
			// with both dimensions larger than or equal to the requested height
			// and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

			// This offers some additional logic in case the image has a strange
			// aspect ratio. For example, a panorama may have a much larger
			// width than height. In these cases the total pixels might still
			// end up being too large to fit comfortably in memory, so we should
			// be more aggressive with sample down the image (=larger
			// inSampleSize).

			final float totalPixels = width * height;

			// Anything more than 2x the requested pixels we'll sample down
			// further
			final float totalReqPixelsCap = reqWidth * reqHeight * 2;

			while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
				inSampleSize++;
			}
		}
		return inSampleSize;
	}
	
	/*
	 *  get Bitmap from disk cache if the target bimap is not in memory 
	 */
	public Bitmap getBitmapFromDiskCache(File file) {

		FileInputStream input = null;
		try {
			input = new FileInputStream(file);
			Bitmap bm = decodeSampledBitmapFromDescriptor(input.getFD(), 100,
					100);
			return bm;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != input) {
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	/**
	 * 
	 * This is used to fetch bitmap from net and write it to the disk
	 */
	private class BitmapFetcherTask extends AsyncTask<String, Void, Bitmap> {

		private String mUrl;
		private WeakReference<ImageView> mImageViewReference;

		public BitmapFetcherTask(ImageView iv) {
			mImageViewReference = new WeakReference<ImageView>(iv);
		}

		@Override
		protected Bitmap doInBackground(String... params) {

			if (BuildConfig.DEBUG)
				Log.d(TAG, "doInBackground() called");

			/*
			 * if(isImageViewReused(mUrl, getImageView())) return null;
			 */

			mUrl = params[0];

			Bitmap bm = null;

			File file = mDiskCache.getFile(mUrl);

			if (file.exists()) {
				if (BuildConfig.DEBUG)
					Log.d(TAG, "File is exist?" + file.exists());
				bm = getBitmapFromDiskCache(file);
			}

			if (bm != null) {
				IconCreator creator = new IconCreator(bm);
				creator.createIcon();
				return creator.getCreatedIcon();
			}
			if (isImageViewReused(mUrl, getImageView()))
				return null;

			file = fetchBitmapFromNet(mUrl);
			bm = getBitmapFromDiskCache(file);
			
			//If bitmap is not null
			//then crop it to circle shape
			if (bm != null) {
				IconCreator ic = new IconCreator(bm);
				ic.createIcon();
				return ic.getCreatedIcon();
			}
			return bm;
		}

		@Override
		protected void onPostExecute(Bitmap bm) {

			if (bm != null) {
				mMemoryCache.put(mUrl, bm);

				ImageView iv = getImageView();
				if (!isImageViewReused(mUrl, iv))
					iv.setImageBitmap(bm);

				if (BuildConfig.DEBUG)
					Log.d(TAG, "set bitmap finished");

			}
		}

		private ImageView getImageView() {

			return mImageViewReference.get();
		}

	}

	private File fetchBitmapFromNet(String urlString) {

		if (BuildConfig.DEBUG)
			Log.d(TAG, "fetch bitmap begins");

		URL url = null;
		HttpURLConnection conn = null;

		BufferedInputStream input = null;
		ByteArrayOutputStream output = null;

		int retry = 0;

		while (retry < RETRY_TIMES) {
			try {
				url = new URL(urlString);
				conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(20 * 1000);
				input = new BufferedInputStream(conn.getInputStream());
				output = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int length = 0;
				while ((length = input.read(buffer)) != -1)
					output.write(buffer, 0, length);

				output.flush();
				byte[] byteArray = output.toByteArray();

				if (BuildConfig.DEBUG)
					Log.d(TAG, "fetch bitmap finished");

				File file = mDiskCache.getFile(urlString);
				writeToDiskCache(new ByteArrayInputStream(byteArray),
						new FileOutputStream(file));

				return file;

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				retry++;
			} finally {
				if (conn != null)
					conn.disconnect();
				try {
					if (input != null)
						input.close();
					if (output != null)
						output.close();
				} catch (IOException e) {
				}

			}
		}

		if (BuildConfig.DEBUG)
			Log.d(TAG, "fetch bitmap failed");

		return null;
	}

	private void writeToDiskCache(InputStream inputStream,
			OutputStream outputStream) {
		BufferedInputStream input = new BufferedInputStream(inputStream);
		BufferedOutputStream output = new BufferedOutputStream(outputStream);
		int i = 0;
		try {
			while ((i = input.read()) != -1) {
				output.write(i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Judge whether the imageView in ListView/GridView has been reused
	 * 
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
