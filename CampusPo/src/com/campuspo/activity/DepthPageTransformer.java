package com.campuspo.activity;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

public class DepthPageTransformer implements PageTransformer{

	private static final float MIN_SCALE = 0.75f;
	
	public DepthPageTransformer() {
	}

	@Override
	public void transformPage(View view, float position) {
		
		if(position < -1) {
			view.setAlpha(0f);
		} else if(position <= 0) {
			
			view.setAlpha(1);
			view.setTranslationX(0);
			view.setScaleX(1);
			view.setScaleY(1);
		} else if(position <= 1) {
			
			view.setAlpha(1 - position);
			view.setTranslationX(-1 * view.getWidth() * position);
			
			float SCALE_FACTOR = MIN_SCALE + (1 - Math.abs(position)) * (1 - MIN_SCALE);
			view.setScaleX(SCALE_FACTOR);
			view.setScaleY(SCALE_FACTOR);
		} else {
			view.setAlpha(0);
		}
		
	}

}
