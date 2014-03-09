package com.campuspo.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.campuspo.R;
import com.campuspo.fragment.HomePageFragment;
import com.campuspo.fragment.PersonalPageFragment;
import com.campuspo.fragment.SpecialPageFragment;

public class NavigationActivity extends FragmentActivity {

	private DrawerLayout mDrawerLayout;
	private String[] mNavigationTitles;

	private ListView mNavigationList;
	List<Fragment> fragments;

	private Handler mHandler = new Handler();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_navigation);
		
		fragments = new ArrayList<Fragment>();
		fragments.add(new HomePageFragment());
		fragments.add(new SpecialPageFragment());
		//fragments.add(new SpecialPageFragment());
		//fragments.add(new SpecialPageFragment());
		//fragments.add(new SpecialPageFragment());
		fragments.add(new PersonalPageFragment());
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mNavigationTitles = new String[] { "title1", "title2", "title3" };
		mNavigationList = (ListView) findViewById(R.id.listview);
		ArrayAdapter<String> titleAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mNavigationTitles);
		mNavigationList.setAdapter(titleAdapter);
		mNavigationList.setOnItemClickListener(new DrawerItemClickedListener());
		selectItem(0);
	}

	private class DrawerItemClickedListener implements
			ListView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	public void selectItem(int position) {
		Fragment fragment = fragments.get(position);
		
		if(fragment != null) {
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			
			ft.replace(R.id.container, fragment);
			ft.commit();
			mNavigationList.setItemChecked(position, true);
			
			//mDrawerLayout.closeDrawer(mNavigationList);
			
			//由于FragmentTransaction.commit()的操作是异步的
			//为防止DrawerLayout收起时产生卡顿感，延迟收起的操作
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					mDrawerLayout.closeDrawer(findViewById(R.id.left_layout));	
				}
				
			});
		}
				
	}
}
