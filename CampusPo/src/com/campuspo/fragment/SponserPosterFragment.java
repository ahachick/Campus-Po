package com.campuspo.fragment;

import java.util.ArrayList;
import java.util.List;

import TestData.Data;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.campuspo.BuildConfig;
import com.campuspo.activity.PosterActivity;
import com.campuspo.app.CampusPoApplication;
import com.campuspo.domain.Poster;
import com.campuspo.domain.Timeline;
import com.campuspo.service.CampusPoServiceHelper;
import com.campuspo.service.ServiceContants;
import com.campuspo.ui.adapter.PosterListAdapter;

public class SponserPosterFragment extends ListFragment {
	
	private static final String TAG = SponserPosterFragment.class.getSimpleName();
	
	private ArrayList<Poster> mPosterList;
	private PosterListAdapter mAdapter;
	
	private CampusPoServiceHelper mServiceHelper;
	private BroadcastReceiver mRequestReceiver;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mServiceHelper = CampusPoServiceHelper.getInstance(CampusPoApplication.getAppContext());
		mPosterList = new ArrayList<Poster>();
		mAdapter = new PosterListAdapter(getActivity(), mPosterList);
		setListAdapter(mAdapter);
	}


	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent  = new Intent(getActivity(), PosterActivity.class);
		intent.putExtra("POSTER", mPosterList.get(position));
		startActivity(intent);
		
		
	}


	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		super.onResume();
		
		IntentFilter filter = new IntentFilter(
				CampusPoServiceHelper.ACTION_REQUEST_RESULT);

		mRequestReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

				if (BuildConfig.DEBUG)
					Log.d(TAG, "onReceive() called");

				int requestType = intent.getIntExtra(
						ServiceContants.REQUEST_TYPE, 0);

				if (requestType == ServiceContants.REQUEST_PUBLIC_TIMELINE
						|| requestType == ServiceContants.REQUEST_SPONSOR_TIMELINE) {
					if (BuildConfig.DEBUG)
						Log.d(TAG, "processing...");
					
					int resultCode = intent.getIntExtra(
							CampusPoServiceHelper.REQUEST_RESULT_CODE, 0);
					Bundle data = intent
							.getBundleExtra(CampusPoServiceHelper.REQUEST_RESULT_DATA);

					if (resultCode == 1) {
						Timeline timeline = (Timeline) data
								.getSerializable(ServiceContants.RESULT_SERIALIZABLE);
						if (BuildConfig.DEBUG)
							Log.d(TAG, "get Timeline - finished");

						update(timeline.getPosters());	

					} else if (resultCode == -1) {
						Toast.makeText(getActivity(), "无法连接服务器：连接超时",
								Toast.LENGTH_LONG).show();
					}

				}

			}

		};

		this.getActivity().registerReceiver(mRequestReceiver, filter);
		mServiceHelper.getSponsorTimeline(123,123,123);
		
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		if(mRequestReceiver != null)
			getActivity().unregisterReceiver(mRequestReceiver);
	}


	protected void update(List<Poster> posters) {
		mPosterList = (ArrayList<Poster>) posters;
		
		mAdapter = new PosterListAdapter(getActivity(), mPosterList);
		
		this.setListAdapter(mAdapter);
		
	}
	
	
	
}
