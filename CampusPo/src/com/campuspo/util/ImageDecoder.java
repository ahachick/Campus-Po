package com.campuspo.util;

import java.io.FileDescriptor;
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
	
	/**
	 * Decode and sample down a bitmap with resource id to the requested width and height.
	 * 
	 * @param res the context Resource
	 * @param resId the image id
	 * @param reqWidth The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
	 * @return	A bitmap sampled down from the original with the same aspect ratio and dimensions
     *         that are equal to or greater than the requested width and height
	 */
	public Bitmap decodeSampledBitmapFromResId(Resources res, int resId, int reqWidth,
			int reqHeight) {

		BitmapFactory.Options option = new BitmapFactory.Options();
		option.inJustDecodeBounds = false;

		BitmapFactory.decodeResource(res, resId, option);

		option.inSampleSize = calculateInSampleSize(option, reqWidth,
				reqHeight);

		option.inJustDecodeBounds = true;

		return BitmapFactory.decodeResource(res, resId, option);

	}
	
	/**
     * Decode and sample down a bitmap from a file input stream to the requested width and height.
     *
     * @param fileDescriptor The file descriptor to read from
     * @param reqWidth The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return A bitmap sampled down from the original with the same aspect ratio and dimensions
     *         that are equal to or greater than the requested width and height
     */
    public  Bitmap decodeSampledBitmapFromDescriptor(
            FileDescriptor fileDescriptor, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }

    /**
     * Calculate an inSampleSize for use in a {@link BitmapFactory.Options} object when decoding
     * bitmaps using the decode* methods from {@link BitmapFactory}. This implementation calculates
     * the closest inSampleSize that will result in the final decoded bitmap having a width and
     * height equal to or larger than the requested width and height. This implementation does not
     * ensure a power of 2 is returned for inSampleSize which can be faster when decoding but
     * results in a larger bitmap which isn't as useful for caching purposes.
     *
     * @param options An options object with out* params already populated (run through a decode*
     *            method with inJustDecodeBounds==true
     * @param reqWidth The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return The value to be used for inSampleSize
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
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

			return decodeSampledBitmapFromResId(mRes, resId, mReqWidth, mReqHeight);
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
