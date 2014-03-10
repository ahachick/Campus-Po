package com.campuspo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.campuspo.R;
import com.campuspo.activity.ScreenSlideActivity;

public class SpecialPageFragment extends Fragment {

	public static final String TAG = SpecialPageFragment.class.getSimpleName();

	private String[] mTitles;

	private TitleAdapter mAdapter;
	
	private ListView mListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		mTitles = getResources().getStringArray(R.array.list_title);
		mAdapter = new TitleAdapter(getActivity());

		setHasOptionsMenu(false);
	}
	
	

	/*
	 * 
	 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
	 * container, Bundle savedInstanceState) {
	 * 
	 * LinearLayout group = new LinearLayout(getActivity());
	 * group.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
	 * LayoutParams.MATCH_PARENT)); return new View(getActivity()); }
	 */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		 super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_special, container, false);
		
		mListView= (ListView) view.findViewById(R.id.listview);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
		
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
			
		});
		return view;
	}



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

}
