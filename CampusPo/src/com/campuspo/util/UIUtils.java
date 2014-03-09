package com.campuspo.util;

import android.util.DisplayMetrics;

import com.campuspo.app.CampusPoApplication;

public class UIUtils {
	
	@SuppressWarnings("unused")
	private static final String TAG = UIUtils.class.getSimpleName();
	
	public static float getScale() {
		// 获取当前屏幕DispatchMetrics信息
        DisplayMetrics dm = CampusPoApplication.getAppContext().getResources().getDisplayMetrics();
        
        // 密度因子
        return (float)dm.densityDpi / DisplayMetrics.DENSITY_DEFAULT;
	}
	
	
	public static int dip2Pixel(int dip) {
		return (int) (dip * getScale() + 0.5f);
	}
	
	
}
