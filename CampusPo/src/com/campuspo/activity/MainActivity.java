package com.campuspo.activity;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.Window;

import com.campuspo.R;
import com.campuspo.fragment.HomePageFragment;
import com.campuspo.fragment.PersonalPageFragment;
import com.campuspo.fragment.SpecialPageFragment;

public class MainActivity extends ActionBarActivity {

	public static final String TAG = "MainActivity";

	private ViewPager mViewPager;
	private TabsAdapter mTabsAdapter;
	private String[] mTabTitleArray;

	public static final String TAB_POS = "tab_pos";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.pager);
		//requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(mViewPager);
		//setProgressBarIndeterminateVisibility(true);
		
		mTabTitleArray = getResources().getStringArray(R.array.tab_title);
		mTabsAdapter = new TabsAdapter(this, mViewPager);

		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

		mTabsAdapter.addTab(bar.newTab().setText(mTabTitleArray[0]), 
				HomePageFragment.class, null);
		mTabsAdapter.addTab(bar.newTab().setText(mTabTitleArray[1]),
				SpecialPageFragment.class, null);
		mTabsAdapter.addTab(bar.newTab().setText(mTabTitleArray[2]),
				PersonalPageFragment.class, null);

		if (savedInstanceState != null)
			bar.setSelectedNavigationItem(savedInstanceState.getInt(TAB_POS, 0));
		// 初始化
		// prepareData();
		// initUIComponents();
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putInt(TAB_POS, getSupportActionBar()
				.getSelectedNavigationIndex());
	}
	
	@Override
	public boolean onCreateOptionsMenu (Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}
	
	
	public static class TabsAdapter extends FragmentPagerAdapter implements
			ActionBar.TabListener, ViewPager.OnPageChangeListener {

		private Context mContext;
		private ActionBar mActionBar;
		private ViewPager mViewPager;
		private ArrayList<TabInfo> mTabInfoList = new ArrayList<TabInfo>();

		static final class TabInfo {
			private final Class<?> mClass;
			private final Bundle mArgs;

			TabInfo(Class<?> cls, Bundle args) {
				mClass = cls;
				mArgs = args;
			}
		}

		public TabsAdapter(ActionBarActivity activity, ViewPager pager) {
			super(activity.getSupportFragmentManager());

			mContext = activity;
			mActionBar = activity.getSupportActionBar();
			mViewPager = pager;
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}

		public void addTab(ActionBar.Tab tab, Class<?> cls, Bundle args) {
			TabInfo info = new TabInfo(cls, args);
			tab.setTag(info);
			tab.setTabListener(this);
			mTabInfoList.add(info);
			mActionBar.addTab(tab);
			notifyDataSetChanged();
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int index) {
			mActionBar.setSelectedNavigationItem(index);
		}

		@Override
		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {

		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			Object info = tab.getTag();
			int size = mTabInfoList.size();
			for (int i = 0; i < size; i++) {
				if (info == mTabInfoList.get(i))
					mViewPager.setCurrentItem(i);
			}

		}

		@Override
		public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {

		}

		@Override
		public Fragment getItem(int pos) {

			TabInfo info = mTabInfoList.get(pos);

			return Fragment.instantiate(mContext, info.mClass.getName(),
					info.mArgs);
		}

		@Override
		public int getCount() {
			return mTabInfoList.size();
		}

	}

}
