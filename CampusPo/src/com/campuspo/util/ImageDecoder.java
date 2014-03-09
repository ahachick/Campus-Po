package com.campuspo.util;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageDecoder {

	private Resources mRes;
	private Context mContext;
	
	private int mReqWidth;
	private int mReqHeight;

	private int mDefaultBitmapId;
	
	public ImageDecoder(Context ctx) {
		mContext = ctx;
		mRes = ctx.getResources();
	}
	
	public ImageDecoder(Context ctx, int reqWidth, int reqHeight)
	{
		mContext = ctx;
		mRes = ctx.getResources();
		mReqWidth = reqWidth;
		mReqHeight = reqHeight;
	}
	public void setReqWidth(int width) {
		mReqWidth = width;
	}
	
	public void setReqHeight(int height) {
		mReqHeight = height;
	}
	

	public int getDefaultBitmap() {
		return mDefaultBitmapId;
	}

	public void setDefaultBitmap(int resId) {
		this.mDefaultBitmapId = resId;
	}

	public void setDecodedImage(ImageView iv, int resId) {

		iv.setImageResource(resId);;
		
		DecodeAsyncTask mDecodeTask = new DecodeAsyncTask(iv);
		mDecodeTask.execute(new Integer(resId));
	}

	public Bitmap decodeBitmapFromResId(Resources res, int resId, int reqWidth,
			int reqHeight) {

		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inJustDecodeBounds = false;

		BitmapFactory.decodeResource(res, resId, option);

		option.inSampleSize = calculateInSamepleSize(option, reqWidth,
				reqHeight);

		option.inJustDecodeBounds = true;

		return BitmapFactory.decodeResource(res, resId, option);

	}

	private int calculateInSamepleSize(BitmapFactory.Options option,
			int reqWidth, int reqHeight) {
		int width = option.outWidth;
		int height = option.outHeight;

		int inSampleSize = 1;

		// if( width < resWidth || height < resHeight) {
		// return inSampleSize;
		// }
		//
		// while ( (width / inSampleSize > resWidth)
		// || height / inSampleSize > resHeight) {
		// inSampleSize = inSampleSize << 1;// means inSampleSize * 2
		//
		// }

		if (height > reqHeight || width > reqWidth) {
			float heightRatio = height / reqHeight;
			float widthRatio = width / reqWidth;

			inSampleSize = heightRatio < widthRatio ? Math.round(heightRatio)
					: Math.round(widthRatio);

			int totalPixels = width * height;
			int totalPixelsSampleCap = reqWidth * reqHeight;
			while (totalPixels / inSampleSize / inSampleSize > totalPixelsSampleCap) {
				inSampleSize++;
			}
		}
		return inSampleSize;
	}

	class DecodeAsyncTask extends AsyncTask<Object, Void, Bitmap> {

		private WeakReference<ImageView> mWeakReferenceImageView;

		public DecodeAsyncTask(ImageView imageView) {
			mWeakReferenceImageView = new WeakReference<ImageView>(imageView);
		}

		@Override
		protected Bitmap doInBackground(Object... params) {
			int resId = (Integer) params[0];

			return decodeBitmapFromResId(mRes, resId, mReqWidth, mReqHeight);
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (bitmap == null)
				return;
			
			ImageView iv = mWeakReferenceImageView.get();
			if(null != iv) {
				iv.setImageBitmap(bitmap);
			}
		}

	}
}
