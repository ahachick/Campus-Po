package com.campuspo.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.campuspo.R;
import com.campuspo.domain.Poster;
import com.campuspo.util.DateUtils;
import com.campuspo.util.ImageLoader;


public class PosterListAdapter extends BaseAdapter {
	
	private List<Poster> mPosters;

	private LayoutInflater mInflater;
	
	private ImageLoader mImageLoader;
	

	public PosterListAdapter(Context ctx , List<Poster> posters) {
		mPosters = posters;
		mInflater = LayoutInflater.from(ctx);
		mImageLoader = ImageLoader.getInstance(ctx);
		mImageLoader.setDefaultBitmap(R.drawable.ic_head_default);
	}

	@Override
	public int getCount() {
		return mPosters.size();
	}

	@Override
	public Object getItem(int position) {
		return mPosters.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {

			convertView = mInflater.inflate(R.layout.item_poster_title,
					parent, false);

			holder = new ViewHolder();

			holder.ivProfileIcon = (ImageView) convertView
					.findViewById(R.id.iv_profile_icon);
			holder.tvSponserName = (TextView) convertView
					.findViewById(R.id.tv_sponser_name);
			holder.tvPosterTitle = (TextView) convertView
					.findViewById(R.id.tv_poster_title);
			holder.tvReleasedTime = (TextView) convertView
					.findViewById(R.id.tv_released_time);
			holder.tvParticipantNum = (TextView) convertView
					.findViewById(R.id.tv_participant_number);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// set the value to each ui component;
		Poster poster = (Poster) getItem(position);
		// hold image asynchronously here
		mImageLoader.loadImage(poster.getProfileIconUrl(),
				 	holder.ivProfileIcon);
		holder.tvSponserName.setText(poster.getUserScreenName());
		holder.tvPosterTitle.setText(poster.getPosterTitle());
		
		if(poster.isWanted() == true)
			if(poster.getWantedNum() != -1)
			holder.tvParticipantNum.setText(poster.getParticipantNum() + "/"
					+ poster.getWantedNum());
		holder.tvReleasedTime.setText(DateUtils.getTimeDiff(poster.getReleasedTime()));

		return convertView;
	}

	class ViewHolder {
		public ImageView ivProfileIcon;
		public TextView tvSponserName;
		public TextView tvPosterTitle;
		public TextView tvReleasedTime;
		public TextView tvParticipantNum;
	}
}
