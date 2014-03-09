package com.campuspo.fragment;

import java.util.ArrayList;

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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.campuspo.BuildConfig;
import com.campuspo.R;
import com.campuspo.activity.PosterActivity;
import com.campuspo.domain.Participant;
import com.campuspo.domain.Participants;
import com.campuspo.domain.Poster;
import com.campuspo.service.CampusPoServiceHelper;
import com.campuspo.service.ServiceContants;
import com.campuspo.util.ImageLoader;

public class ParticipantFragment extends Fragment {

	private static final String TAG = ParticipantFragment.class.getSimpleName();

	//private TextView m_tvNoWanted;
	private LinearLayout mBlank;
	private TextView m_tvParticipant;
	private Button m_btnJoin;
	private Button m_btnQuit;
	private ListView m_lvParticipant;

	private ParticipantAdapter mParticipantAdapter;

	private ArrayList<Participant> mParticipants;
	private Poster mPoster;

	private CampusPoServiceHelper mServiceHelper;
	private BroadcastReceiver mRequestReceiver;

	private ImageLoader mImageLoader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);

		mServiceHelper = CampusPoServiceHelper.getInstance(getActivity());
		mParticipants = new ArrayList<Participant>();
		mPoster = (Poster) this.getArguments().get(PosterActivity.KEY_DATA);

		mParticipantAdapter = new ParticipantAdapter(getActivity());
		mImageLoader = ImageLoader.getInstance(getActivity());
		mImageLoader.setDefaultBitmap(R.drawable.ic_head_default);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View v = inflater.inflate(R.layout.fragment_participant, container,
				false);
		//m_tvNoWanted = (TextView) v.findViewById(R.id.tv_no_wanted);
		mBlank = (LinearLayout) v.findViewById(R.id.blank);
		m_tvParticipant = (TextView) v.findViewById(R.id.tv_participant);
		m_btnJoin = (Button) v.findViewById(R.id.btn_join);
		m_btnQuit = (Button) v.findViewById(R.id.btn_quit);
		// set Adapter for listView
		m_lvParticipant = (ListView) v.findViewById(R.id.listview);

		m_lvParticipant.setAdapter(mParticipantAdapter);

		m_btnJoin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
					mServiceHelper.join(mPoster.getPosterId());
			}
		});

		m_btnQuit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mServiceHelper.quit(mPoster.getPosterId());
			}
		});
		populateUi();

		return v;
	}

	public void populateUi() {
		// set the visibilty of the buttons
		
			
		if (mPoster.isJoined()) {
			m_btnJoin.setVisibility(View.GONE);
			m_btnQuit.setVisibility(View.VISIBLE);
		} else {
			m_btnJoin.setVisibility(View.VISIBLE);
			m_btnQuit.setVisibility(View.GONE);
		}

		if (mPoster.isSponsor()) {
			m_btnJoin.setVisibility(View.GONE);
			m_btnQuit.setVisibility(View.GONE);
		}
		if (mPoster.isWanted() && mPoster.getWantedNum() != 0)
			m_tvParticipant.setText(mPoster.getParticipantNum() + "/"
					+ mPoster.getWantedNum());
		
		if(!mPoster.isWanted()) {
			mBlank.setVisibility(View.VISIBLE);
		}else
			mBlank.setVisibility(View.GONE);   
	}

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

				int resultCode = intent.getIntExtra(
						CampusPoServiceHelper.REQUEST_RESULT_CODE, 0);
				Bundle data = intent
						.getBundleExtra(CampusPoServiceHelper.REQUEST_RESULT_DATA);

				if (requestType == ServiceContants.REQUEST_PARTICIPANTS) {
					if (BuildConfig.DEBUG)
						Log.d(TAG, "processing...");

					if (resultCode == 1) {
						Participants participants = (Participants) data
								.getSerializable(ServiceContants.RESULT_SERIALIZABLE);
						if (BuildConfig.DEBUG)
							Log.d(TAG, "get Participants - finished");
						update(participants);
						populateUi();
					} else if (resultCode == -1) {
						Toast.makeText(getActivity(), "无法连接服务器：连接超时",
								Toast.LENGTH_LONG).show();
					}
				}
				
				if (requestType == ServiceContants.REQUEST_QUIT) {
					if (BuildConfig.DEBUG)
						Log.d(TAG, "processing...quit");

					if (resultCode == 1) {
						Participants participants = (Participants) data
								.getSerializable(ServiceContants.RESULT_SERIALIZABLE);
						if (BuildConfig.DEBUG)
							Log.d(TAG, "quit - finished");
						update(participants);
						mPoster.setJoined(false);
						populateUi();
						mServiceHelper.getParticipants(mPoster.getPosterId());

					} else if (resultCode == -1) {
						Toast.makeText(getActivity(), "无法连接服务器：连接超时",
								Toast.LENGTH_LONG).show();
					}
				}

				if (requestType == ServiceContants.REQUEST_JOIN) {
					if (BuildConfig.DEBUG)
						Log.d(TAG, "processing...join");

					if (resultCode == 1) {
						Participants participants = (Participants) data
								.getSerializable(ServiceContants.RESULT_SERIALIZABLE);
						if (BuildConfig.DEBUG)
							Log.d(TAG, "join - finished");
						update(participants);
						mPoster.setJoined(true);
						populateUi();
						mServiceHelper.getParticipants(mPoster.getPosterId());

					} else if (resultCode == -1) {
						Toast.makeText(getActivity(), "无法连接服务器：连接超时",
								Toast.LENGTH_LONG).show();
					}
				}

			}

		};

		this.getActivity().registerReceiver(mRequestReceiver, filter);
		mServiceHelper.getParticipants(mPoster.getPosterId());

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		MenuItem item = menu.add("刷新");

		item.setIcon(R.drawable.ic_action_refresh);

		MenuItemCompat.setShowAsAction(item,
				MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		mServiceHelper.getParticipants(mPoster.getPosterId());

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mRequestReceiver != null)
			getActivity().unregisterReceiver(mRequestReceiver);
	}

	public void update(Participants participants) {
		if (participants == null)
			return;

		mParticipants = participants.getParticipants();
		mPoster.setParticipantNum(mParticipants.size());
		mParticipantAdapter.notifyDataSetChanged();

	}

	private class ParticipantAdapter extends BaseAdapter {

		private LayoutInflater mInflater;
		private Context mContext;

		public ParticipantAdapter(Context ctx) {
			mContext = ctx;
			mInflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			return mParticipants.size();
		}

		@Override
		public Object getItem(int position) {
			return mParticipants.get(position);
		}

		@Override
		public long getItemId(int position) {
			return mParticipants.get(position).getUser().getUserId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			Participant p = (Participant) getItem(position);

			if (convertView == null) {
				holder = new ViewHolder();
				convertView = mInflater.inflate(
						R.layout.item_poster_participant, parent, false);

				holder.ivProfileIcon = (ImageView) convertView
						.findViewById(R.id.iv_profile_icon);
				holder.tvParticipantName = (TextView) convertView
						.findViewById(R.id.tv_participant_name);
				holder.tvJoinDate = (TextView) convertView
						.findViewById(R.id.tv_join_date);
				holder.btnRemove = (Button) convertView
						.findViewById(R.id.btn_remove);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			mImageLoader.loadImage(p.getUser().getProfileIconUrl(),
					holder.ivProfileIcon);
			holder.tvParticipantName.setText(p.getUser().getUserScreenName());
			// holder.tvJoinDate.setText(p.getJoinedAt().toString());
			return convertView;
		}

		private class ViewHolder {

			public ImageView ivProfileIcon;
			public TextView tvParticipantName;
			public TextView tvJoinDate;
			public Button btnRemove;

		}

	}

}