package com.campuspo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.campuspo.R;

public class ScreenSlideActivity extends FragmentActivity{

	private ViewPager mViewPager;
	
	private PagerAdapter mAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screenslide);
		
		mAdapter = new PagerAdapter(getSupportFragmentManager());
		
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setBackgroundColor(0xf0000000);
		mViewPager.setAdapter(mAdapter);
		
		//mViewPager.setPageTransformer(true, new DepthPageTransformer());
		mViewPager.setPageTransformer(true, new ZoomOutTransformer());
	}
	
	public static class ScreenSlideFragment extends Fragment {
		
		private final static String KEY_INDEX = "INDEX";
		
		public static ScreenSlideFragment newInstance(int index) {
			Bundle arg = new Bundle();
			arg.putInt(KEY_INDEX, index);
			
			ScreenSlideFragment fragment = new ScreenSlideFragment();
			fragment.setArguments(arg);
			return fragment;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);
			
			RelativeLayout layout = new RelativeLayout(getActivity());
			
			layout.setBackgroundColor(0);
			layout.setGravity(Gravity.CENTER);
			layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
							LayoutParams.MATCH_PARENT));
			
			TextView tv = new TextView(getActivity());
			tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			
			tv.setTextSize(30);
		
			Bundle arg = getArguments();
			int index = arg.getInt(KEY_INDEX, 0);
			
			int bgColor = 0;
			switch(index) {
			case 0:
				bgColor = 0xff33b5e5;
				break;
			case 1:
				bgColor = 0xffaa66cc;
				break;
			case 2:
				bgColor = 0xff99cc00;
				break;
			case 3:
				bgColor = 0xffffbb33;
				break;
			case 4:
				bgColor = 0xffff4444;
				break;
			}
			
			layout.setBackgroundColor(bgColor);
			if(arg != null ) {
				tv.setText("" + index);
			}
				
			layout.addView(tv);
			
			return layout;
		}

	}
	
	public class PagerAdapter extends FragmentStatePagerAdapter {

		private int mTotalNum = 5;
		
		public PagerAdapter(FragmentManager fm) {
			super(fm);
		}
		
		@Override
		public Fragment getItem(int index) {
			return ScreenSlideFragment.newInstance(index);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mTotalNum;
		}
		
	}

}
