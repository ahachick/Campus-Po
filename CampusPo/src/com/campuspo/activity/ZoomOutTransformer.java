package com.campuspo.activity;

import android.support.v4.view.ViewPager;
import android.view.View;


public class ZoomOutTransformer implements ViewPager.PageTransformer{

	private static final float MIN_SCALE = 0.85f;
	private static final float MIN_ALPHA = 0.5f;
	
	public ZoomOutTransformer() {
	}

	@Override
	public void transformPage(View view, float position) {
		
		float pageWidth = view.getWidth();
		float pageHeight = view.getHeight();
		
		if(position <= -1) {
			view.setAlpha(0f);
		} else if(position <= 1) {
			
			final float SCALE_FACTOR = Math.max(MIN_SCALE, 1 - Math.abs(position));
			
			float vertMargin = pageHeight * (1 - SCALE_FACTOR) / 2;
			float horiMargin = pageWidth * (1 - SCALE_FACTOR) / 2;
			
			if(position < 0) {
				view.setTranslationX( horiMargin - vertMargin / 2);
			} else {
				view.setTranslationX( - horiMargin + vertMargin /2);
			}
			view.setScaleX(SCALE_FACTOR);
			view.setScaleY(SCALE_FACTOR);
			
			final float ALPHA_FACTOR = (MIN_ALPHA + 
								(SCALE_FACTOR - MIN_SCALE) /
								(1 - MIN_SCALE)*(1 - MIN_ALPHA));

			view.setAlpha(ALPHA_FACTOR);
		} else {
			view.setAlpha(0f);
		}
	}

}
