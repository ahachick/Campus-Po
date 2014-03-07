package com.campuspo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.campuspo.R;
import com.campuspo.activity.ScreenSlideActivity;

public class SpecialPageFragment extends ListFragment {

	public static final String TAG = SpecialPageFragment.class.getSimpleName();

	private String[] mTitles;

	private TitleAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mTitles = getResources().getStringArray(R.array.list_title);
		mAdapter = new TitleAdapter(getActivity());
		this.setListAdapter(mAdapter);

		setHasOptionsMenu(false);
	}

	/*
	 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
	 * container, Bundle savedInstanceState) {
	 * 
	 * LinearLayout group = new LinearLayout(getActivity());
	 * group.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
	 * LayoutParams.MATCH_PARENT)); return new View(getActivity()); }
	 */

	public void perpareData() {

	}

	private class TitleAdapter extends BaseAdapter {

		private Context mContext;
		private LayoutInflater inflater;

		public TitleAdapter(Context ctx) {
			mContext = ctx;
			inflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			return mTitles.length;
		}

		@Override
		public Object getItem(int position) {
			return mTitles[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = inflater.inflate(R.layout.item_special, parent, false);
			TextView tv = (TextView) v.findViewById(R.id.tv_special_title);
			tv.setText(mTitles[position]);
			return v;
		}

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = null;
		switch (position) {
		case 0:
			intent = new Intent(getActivity(), ScreenSlideActivity.class);
			break;
		default:

		}
		if(null != intent) {
			getActivity().startActivity(intent);
		}
	}
}
