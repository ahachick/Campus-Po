package com.campuspo.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.campuspo.R;
import com.campuspo.util.Logger;

public class ScreenSlideActivity extends FragmentActivity {

	private static final String TAG = ScreenSlideActivity.class.getSimpleName();

	private TextView mTextDate;
	private ViewPager mViewPager;
	private PagerAdapter mAdapter;

	private List<BigPoster> mPosterList; // Test Data!!

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// set full screen without actionbar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_screenslide);
		
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//		WindowManager.LayoutParams.FLAG_FULLSCREEN);

		prepareData();

		mAdapter = new PagerAdapter(getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mTextDate = (TextView) findViewById(R.id.tv_date);
		mViewPager.setAdapter(mAdapter);

		// mViewPager.setPageTransformer(true, new DepthPageTransformer());
		mViewPager.setPageTransformer(true, new ZoomOutTransformer());
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}

			@Override
			public void onPageSelected(int position) {
				Logger.debug(TAG, "Pager on position:" + position
						+ " is selected");
				setDateText(position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				if (state == ViewPager.SCROLL_STATE_IDLE) {
					mTextDate.setVisibility(View.VISIBLE);
				} else {
					mTextDate.setVisibility(View.INVISIBLE);
				}
			}

		});

		if (mPosterList != null && mPosterList.size() != 0) {
			mViewPager.setCurrentItem(0);
			setDateText(0);
		} else {
			mTextDate.setVisibility(View.INVISIBLE);
		}

	}

	public void setDateText(int position) {
		String dateStr = String.format(
				getResources().getString(R.string.date_of_big_poster),
				convertTimeToString(mPosterList.get(position).mCreateAt));
		mTextDate.setText(dateStr);
	}

	public void prepareData() {
		mPosterList = new ArrayList<BigPoster>();

		BigPoster p1 = new BigPoster();
		p1.mTitle = "光迹涂鸦";
		p1.mDescription = "想用光影描绘出只属于你的奇迹吗？即使你没有一个能用光描绘出妹纸的好基友，但是在光涂之夜也可以实现你所期盼已久的愿望噢！明晚光涂之夜，说不定真有只属于你的意外发生呢！\n4.29.一饭排球场";
		p1.mCreateAt = new Date().getTime() - 36 * 3600 * 1000;
		p1.mSponsorName = "百步梯";
		p1.mResId = R.drawable.ic_delegation;

		BigPoster p2 = new BigPoster();
		p2.mTitle = "Aha大讲堂";
		p2.mDescription = "4.29晚A4204\n Aha~Aha~Aha~Aha~Aha~Aha~Aha~Aha~Aha~Aha~Aha~Aha~Aha~Aha~Aha~Aha~Aha~Aha~Aha~Aha~";
		
		p2.mCreateAt = new Date().getTime();
		p2.mSponsorName = "Aha团队";
		p2.mResId = R.drawable.ic_headline;

		BigPoster p3 = new BigPoster();
		p3.mTitle = "噗哈哈哲学";
		p3.mDescription = "hahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahahaha";
		p3.mCreateAt = new Date().getTime() - 56 * 3600 * 1000;
		p3.mSponsorName = "爱笑社";
		p3.mResId = R.drawable.ic_community;
		
		/*BigPoster dummy = new BigPoster();
		p3.mTitle = "噗哈哈哲学";
		p3.mDescription = "Dummy";
		p3.mCreateAt = new Date().getTime() - 56 * 3600 * 1000;
		p3.mSponsorName = "Dummy";*/
		
		mPosterList.add(p2);
		mPosterList.add(p1);
		mPosterList.add(p3);
		
	}

	public static class ScreenSlideFragment extends Fragment {

		private final static String KEY_INDEX = "INDEX";
		private final static String KEY_DATA = "DATA";

		private ImageView mImageBackground;
		private TextView mTextTitle;
		private TextView mTextDescription;
		private TextView mTextSponsor;

		private int mPos;
		private BigPoster mBigPoster;

		public static ScreenSlideFragment newInstance(int index, BigPoster bp) {
			Bundle arg = new Bundle();
			arg.putInt(KEY_INDEX, index);
			arg.putSerializable(KEY_DATA, bp);

			ScreenSlideFragment fragment = new ScreenSlideFragment();
			fragment.setArguments(arg);
			return fragment;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Bundle args = getArguments();
			if (args != null) {
				mPos = getArguments().getInt(KEY_INDEX, 0);
				mBigPoster = (BigPoster) args.getSerializable(KEY_DATA);
			}
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			super.onCreateView(inflater, container, savedInstanceState);

			View v = inflater.inflate(R.layout.fragment_screen, container,
					false);

			mImageBackground = (ImageView) v.findViewById(R.id.iv_poster_bg);
			mTextTitle = (TextView) v.findViewById(R.id.tv_title);
			mTextDescription = (TextView) v.findViewById(R.id.tv_description);
			mTextSponsor = (TextView) v.findViewById(R.id.tv_sponsor);
			// set Height to ImageView
			final float ratio = (3.0f / 7.0f);
			int width = getActivity().getResources().getDisplayMetrics().widthPixels;
			int height = (int) (width * ratio);

			// set Title
			mTextTitle.setText(mBigPoster.mTitle);
			// set Description
			mTextDescription.setText(mBigPoster.mDescription);
			// set Sponsor name
			mTextSponsor.setText(mBigPoster.mSponsorName);
			Logger.debug(TAG, "" + v.getWidth() + ";" + height);
			mImageBackground.setLayoutParams(new LayoutParams(
					LayoutParams.FILL_PARENT, height));
			mImageBackground.setImageResource(mBigPoster.mResId);
			
			return v;
		}

	}

	public String convertTimeToString(long time) {

		// Calendar c = Calendar.getInstance();
		long today = new Date().getTime();

		long deltaClock = (today - time) / 1000 / 60 / 60;

		if (deltaClock < 24) {
			return "今日";
		} else if (deltaClock < 48) {
			return "昨日";
		} else {
			String format = "%d月%d日";
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(time);
			int month = c.get(Calendar.MONTH) + 1;
			int date = c.get(Calendar.DAY_OF_MONTH);

			return String.format(format, month, date);
		}

	}

	public static class BigPoster implements java.io.Serializable {
		public String mTitle;
		public String mDescription;
		public Long mCreateAt;
		public String mSponsorName;
		public int mResId;
	}

	private class PagerAdapter extends FragmentStatePagerAdapter {

		public PagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int index) {
			return ScreenSlideFragment.newInstance(index,
					mPosterList.get(index));
		}

		@Override
		public int getCount() {
			return mPosterList.size();
		}

	}

}
