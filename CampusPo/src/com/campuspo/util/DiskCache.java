package com.campuspo.util;

import java.io.File;

import android.content.Context;

public class DiskCache {
	
	private File mCacheDir;

	private static DiskCache sInstance;
	private DiskCache(Context ctx) {
		if(android.os.Environment.getExternalStorageState()
				.equals(android.os.Environment.MEDIA_MOUNTED)) {
			mCacheDir = new File(android.os.Environment.getExternalStorageDirectory()
					,ctx.getPackageName());
		} else {
			mCacheDir = ctx.getCacheDir();
		}
		
		if(!mCacheDir.exists()) {
			mCacheDir.mkdir();
		}
	}
	
	public static synchronized DiskCache getInstance(Context ctx) {
		if(sInstance == null) {
			sInstance = new DiskCache(ctx);
		}
		
		return sInstance;
	}
	
	
	public synchronized File getFile(String url) {
		
		String filename = String.valueOf(url.hashCode());
		File file = new File(mCacheDir.getAbsolutePath(),filename);
		return file;
	}
	
	public synchronized void clear() {
		File[] files = mCacheDir.listFiles();
		
		if(null == files) {
			return;
		}
		for(File file : files) {
			file.delete();
		}
	}
}
