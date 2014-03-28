package com.campuspo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.campuspo.R;
import com.campuspo.domain.Poster;
import com.campuspo.fragment.ParticipantFragment;
import com.campuspo.fragment.PosterDetailFragment;
import com.campuspo.fragment.PosterRelatedFragment;
import com.campuspo.util.ImageLoader;


public class PosterActivity extends ActionBarActivity{
	
	@SuppressWarnings("unused")
	private static final String TAG = PosterActivity.class.getSimpleName();
	
	public static final String KEY_DATA = "POSTER";
	
	private FragmentTabHost mTabHost;
	private TextView m_tvUserScreenName;
	private ImageView m_ivProfileIcon;
	private TextView m_tvPosterTitle;
	
	private Poster mPoster;
	
	private ImageLoader mImageLoader;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poster);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		prepareData();
		
	}
	
	public void prepareData() {
		
		Intent intent = this.getIntent();
		mPoster = (Poster) intent.getSerializableExtra("POSTER");
		
		Bundle data = new Bundle();
		data.putSerializable(KEY_DATA, mPoster);
		
		m_tvUserScreenName = (TextView) findViewById(R.id.tv_user_screen_name);
		m_tvPosterTitle = (TextView) findViewById(R.id.tv_poster_title);
		m_ivProfileIcon = (ImageView) findViewById(R.id.iv_profile_icon);
		
		
		
		mTabHost = (FragmentTabHost) findViewById(R.id.tabhost);

		mTabHost.setup(this, getSupportFragmentManager(), R.id.fl_content);
		
		mTabHost.addTab(mTabHost.newTabSpec("Detail").setIndicator("Detail"),
						PosterDetailFragment.class, data);
		mTabHost.addTab(mTabHost.newTabSpec("Participant").setIndicator("Join"),
						ParticipantFragment.class, data);
		mTabHost.addTab(mTabHost.newTabSpec("Related").setIndicator("Related"),
						PosterRelatedFragment.class, data);		
		
		mImageLoader = ImageLoader.getInstance(this);
		mImageLoader.setDefaultBitmap(R.drawable.ic_head_default);
		populateUi();
		
	}
	
	public void populateUi() {
		if(mPoster != null) {
		m_tvUserScreenName.setText(mPoster.getUserScreenName());
		m_tvPosterTitle.setText(mPoster.getPosterTitle());
		//Load profile icon here
		mImageLoader.loadImage(mPoster.getProfileIconUrl(), m_ivProfileIcon);
		}
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case android.R.id.home :
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
}
