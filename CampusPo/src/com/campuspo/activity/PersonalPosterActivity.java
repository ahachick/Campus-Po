package com.campuspo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.OnNavigationListener;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.campuspo.R;
import com.campuspo.fragment.SponserPosterFragment;
import com.campuspo.util.ImageLoader;

public class PersonalPosterActivity extends ActionBarActivity{
	
	private static final String TAG = PersonalPosterActivity.class.getSimpleName();
	
	public OnNavigationListener mNavigationListener;
	SpinnerAdapter mSpinnerAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState ) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_poster);
		
		
		mNavigationListener = new OnNavigationListener() {

			@Override
			public boolean onNavigationItemSelected(int pos, long itemId) {
				SponserPosterFragment fragment = new SponserPosterFragment();
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.fragment_container, fragment);
				ft.commit();
				return true;
			}
			
		};
		mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.action_poster_list,
		          android.R.layout.simple_spinner_dropdown_item);

		ActionBar actionbar = getSupportActionBar();
		actionbar.setDisplayShowTitleEnabled(false);
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionbar.setListNavigationCallbacks(mSpinnerAdapter, mNavigationListener);
	}
}
