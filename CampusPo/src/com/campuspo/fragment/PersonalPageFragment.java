package com.campuspo.fragment;

import TestData.Data;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.campuspo.BuildConfig;
import com.campuspo.R;
import com.campuspo.activity.PersonalPosterActivity;
import com.campuspo.domain.User;
import com.campuspo.service.CampusPoServiceHelper;
import com.campuspo.service.ServiceContants;
import com.campuspo.util.ImageLoader;

public class PersonalPageFragment extends Fragment implements
		View.OnClickListener {

	private static final String TAG = "PersonalPageFragment";

	private ImageView mImageViewProfileIcon;
	private TextView mTextViewScreenName;
	private TextView mTextViewUserDescription;
	private ProgressBar mProgressBar;

	private Button mButtonSkill;
	private Button mButtonPoster;
	private Button mButtonFollowing;
	private Button mButtonMessage;
	private Button mButtonDetail;

	private User mUser;
	private BroadcastReceiver mReceiver;

	private ImageLoader mImageLoader;
	private CampusPoServiceHelper mServiceHelper;
	
	public static PersonalPageFragment newInstance(Bundle arg) {
		PersonalPageFragment fragment = new PersonalPageFragment();
		fragment.setArguments(arg);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// initialize the user here from the cache/contentProvider if exists
		
		
		
		if(savedInstanceState == null) {
			Bundle args = getArguments();
			
			if(null !=  args) {
				mUser = (User) args.getSerializable("USER");
				
			}else {
				mUser = Data.getUserProfile();
			}
			
		}else {
			mUser = (User) savedInstanceState.getSerializable("USER");
		}

		prepareData();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View v = inflater.inflate(R.layout.fragment_personal_page, container,
				false);
		// bind id to each component
		mImageViewProfileIcon = (ImageView) v
				.findViewById(R.id.iv_profile_icon);
		mImageViewProfileIcon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				uploadProfileIcon();
			}
		});
		mTextViewScreenName = (TextView) v
				.findViewById(R.id.tv_user_screen_name);
		mTextViewUserDescription = (TextView) v
				.findViewById(R.id.tv_user_description);
		
		mProgressBar = (ProgressBar) v.findViewById(R.id.progressbar);
		mButtonSkill = (Button) v.findViewById(R.id.btn_skill);
		mButtonPoster = (Button) v.findViewById(R.id.btn_poster);
		mButtonFollowing = (Button) v.findViewById(R.id.btn_following);
		mButtonMessage = (Button) v.findViewById(R.id.btn_message);
		mButtonDetail = (Button) v.findViewById(R.id.btn_detail);
		// bind the listener to each button
		mButtonSkill.setOnClickListener(this);
		mButtonPoster.setOnClickListener(this);
		mButtonFollowing.setOnClickListener(this);
		mButtonMessage.setOnClickListener(this);
		mButtonDetail.setOnClickListener(this);
		// Populate the UI component
		populateUi();
		// update the data
		return v;
	}

	protected void uploadProfileIcon() {
	}

	public void prepareData() {

		this.setHasOptionsMenu(true);
		mImageLoader = ImageLoader.getInstance(getActivity());
		mImageLoader.setDefaultBitmap(R.drawable.ic_head_default);
		mServiceHelper = CampusPoServiceHelper.getInstance(getActivity());
	}

	private void populateUi() {

		if (mUser != null) {
			
			if(BuildConfig.DEBUG)
				Log.d(TAG,"populating...");
			mTextViewScreenName.setText(mUser.getUserScreenName());
			mTextViewUserDescription.setText(mUser.getUserDescription());

			String profileIconUrl = mUser.getProfileIconUrl();
			if (null != profileIconUrl && !"".equals(profileIconUrl))
				mImageLoader.loadImage(profileIconUrl, mImageViewProfileIcon);

		}
	}

	

	@Override
	public void onResume() {
		super.onResume();
		
		//mProgressBar.setVisibility(View.VISIBLE);
		
		
		
		IntentFilter filter = new IntentFilter(
				CampusPoServiceHelper.ACTION_REQUEST_RESULT);
		mReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

				int requestType = intent.getIntExtra(
						ServiceContants.REQUEST_TYPE, -1);

				if (requestType == ServiceContants.REQUEST_PROFILE) {
					if(BuildConfig.DEBUG)
						Log.d(TAG, "receive result");
					
					int code = intent.getIntExtra(
							CampusPoServiceHelper.REQUEST_RESULT_CODE, -1);
					Bundle data = intent.getBundleExtra(CampusPoServiceHelper.REQUEST_RESULT_DATA);
					if(BuildConfig.DEBUG)
						Log.d(TAG, "fetch finished" + code);
					if (code == 1) {
						
						mUser = (User) data.getSerializable(ServiceContants.RESULT_SERIALIZABLE);
						
						populateUi();
					} else if (code == 0) {
						String errorMsg = intent
								.getStringExtra(ServiceContants.RESULT_ERROR_MSG);
						Toast.makeText(getActivity(), errorMsg,
								Toast.LENGTH_SHORT).show();
						populateUi();
					}
					mProgressBar.setVisibility(View.GONE);

				}
			}

		};

		getActivity().registerReceiver(mReceiver, filter);
		
		if(mUser == null) {
			mProgressBar.setVisibility(View.VISIBLE);
			mServiceHelper.getUserProfile();
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		if (mReceiver != null) {
			getActivity().unregisterReceiver(mReceiver);
		}
	}
	

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		
		super.onCreateOptionsMenu(menu, inflater);
		
		inflater.inflate(R.menu.menu_timeline_fragment, menu);
		
		MenuItem item = menu.getItem(0);
		MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()) {
		case R.id.action_refresh :
			
			if(BuildConfig.DEBUG)
				Log.d(TAG, "send request for user profile--begin");
			mProgressBar.setVisibility(View.VISIBLE);
			mServiceHelper.getUserProfile();
			break;
		default:
		}
		
		
		return super.onOptionsItemSelected(item);
	}
	

	@Override
	public void onClick(View v) {

		Intent intent = new Intent();

		// set the class of Activity that will be invoked here
		switch (v.getId()) {
		case R.id.btn_skill:
			break;
		case R.id.btn_poster:
			intent.setClass(getActivity(), PersonalPosterActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_following:
			break;
		case R.id.btn_message:
			break;
		case R.id.btn_detail:
			break;
		}

		//startActivity(intent);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putSerializable("USER", mUser);
	}
	


}
