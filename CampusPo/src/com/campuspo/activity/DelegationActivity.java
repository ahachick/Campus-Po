package com.campuspo.activity;

import java.io.Serializable;
import java.util.List;

import TestData.Data;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.campuspo.R;
import com.campuspo.domain.Delegation;
import com.campuspo.util.ImageLoader;

import external.library.widget.XListView;
import external.library.widget.XListView.IXListViewListener;

public class DelegationActivity extends ActionBarActivity implements
		IXListViewListener {

	@SuppressWarnings("unused")
	private static final String TAG = DelegationActivity.class.getSimpleName();

	private List<Delegation> mDelegationList;
	private DelegationAdapter mDelegationAdapter;
	private XListView mListView;

	private static final String KEY_DATA = "DATA";

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_delegation);

		// mGridView = new GridView(this);
		// mGridView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		// LayoutParams.MATCH_PARENT));
		// int mColumnWidth =
		// this.getResources().getDimensionPixelOffset(R.dimen.item_delegation_width);

		if (savedInstanceState != null) {
			mDelegationList = (List<Delegation>) savedInstanceState
					.getSerializable(KEY_DATA);
		} else {
			// Test data
			/*
			 * mDelegationList = new ArrayList<Delegation>();
			 * mDelegationList.add(new Delegation()); mDelegationList.add(new
			 * Delegation()); mDelegationList.add(new Delegation());
			 * mDelegationList.add(new Delegation()); mDelegationList.add(new
			 * Delegation());
			 */

			mDelegationList = Data.getDelegations();

		}

		mListView = (XListView) findViewById(R.id.listview);
		mListView.setPullRefreshEnable(true);
		mListView.setXListViewListener(this);
		mDelegationAdapter = new DelegationAdapter(this);

		mListView.setAdapter(mDelegationAdapter);

	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);

		savedInstanceState.putSerializable(KEY_DATA,
				(Serializable) mDelegationList);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu_timeline_fragment, menu);
		// MenuItem item = menu.add(getResources().getString(R.string.publish));
		// MenuItemCompat item = new MenuItemCompat();
		// item.setIcon(getResources().getDrawable(R.id.action_edit));
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_edit:
			Intent intent = new Intent(this, PublishDelegationActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	private class DelegationAdapter extends BaseAdapter {

		private Context mContext;
		private LayoutInflater mInflater;
		private ImageLoader mImageLoader;

		public DelegationAdapter(Context ctx) {
			mContext = ctx;
			mInflater = LayoutInflater.from(mContext);
			mImageLoader = ImageLoader.getInstance(ctx);
			mImageLoader.setDefaultBitmap(R.drawable.ic_head_default);
		}

		@Override
		public int getCount() {
			return mDelegationList.size();
		}

		@Override
		public Object getItem(int position) {
			return mDelegationList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			Delegation d = (Delegation) getItem(position);

			ViewHolder holder = null;

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_delegation,
						parent, false);
				holder = new ViewHolder();
				holder.tvUserScreenName = (TextView) convertView
						.findViewById(R.id.tv_user_name);
				holder.ivProfileIcon = (ImageView) convertView
						.findViewById(R.id.iv_profile_icon);
				holder.tvDelegationTitle = (TextView) convertView
						.findViewById(R.id.tv_delegation_title);
				holder.tvDelegationReward = (TextView) convertView
						.findViewById(R.id.tv_reward);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tvUserScreenName.setText(d.getUserScreenName());
			holder.tvDelegationReward.setText(d.getReward());
			holder.tvDelegationTitle.setText(d.getDelegationTitle());
			mImageLoader.loadImage(d.getProfileIconUrl(), holder.ivProfileIcon);

			return convertView;
		}

		class ViewHolder {
			ImageView ivProfileIcon;
			TextView tvUserScreenName;
			TextView tvDelegationTitle;
			TextView tvDelegationReward;
		}

	}

	@Override
	public void onRefresh() {
		mListView.stopRefresh();
		/**
		 * load soming asynchronizedly
		 */
		mDelegationAdapter.notifyDataSetChanged();
	}

	@Override
	public void onLoadMore() {
		mListView.stopLoadMore();

		/**
		 * load soming asynchronizedly
		 */
		mDelegationAdapter.notifyDataSetChanged();
	}
}
