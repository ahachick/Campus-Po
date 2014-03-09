package com.campuspo.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.campuspo.R;
import com.campuspo.domain.Poster;
import com.campuspo.provider.PublicTimelineProviderMetaData.PosterTableMetaData;
import com.campuspo.util.ImageLoader;

public class PosterCursorAdapter extends CursorAdapter {

	private LayoutInflater mInflater;
	private ImageLoader mImageLoader;

	public PosterCursorAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		mInflater = LayoutInflater.from(context);
		mImageLoader = ImageLoader.getInstance(context);
		mImageLoader.setDefaultBitmap(R.drawable.ic_head_default);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {

		ViewHolder holder = null;

		View convertView = mInflater.inflate(R.layout.item_poster_title,
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

		return convertView;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = (ViewHolder) view.getTag();

		Poster poster = parsePosterFromCursor(cursor);
		// hold image asynchronously here
		mImageLoader
				.loadImage(poster.getProfileIconUrl(), holder.ivProfileIcon);
		holder.tvSponserName.setText(poster.getUserScreenName());
		holder.tvPosterTitle.setText(poster.getPosterTitle());

		if (poster.isWanted() == true)
			if (poster.getWantedNum() != -1)
				holder.tvParticipantNum.setText(poster.getParticipantNum()
						+ "/" + poster.getWantedNum());
		//holder.tvReleasedTime.setText(DateUtils.getTimeDiff(poster
		//		.getReleasedTime()));
	}

	public Poster parsePosterFromCursor(Cursor cursor) {
		Poster poster = new Poster();
		poster.setPosterId(cursor.getLong(cursor
				.getColumnIndex(PosterTableMetaData.POSTER_ID)));
		poster.setPosterTitle(cursor.getString(cursor
				.getColumnIndex(PosterTableMetaData.POSTER_TITLE)));
		poster.setPosterDescription(cursor.getString(cursor
				.getColumnIndex(PosterTableMetaData.POSTER_DESCRIPTION)));
		poster.setProfileIconUrl(cursor.getString(cursor
				.getColumnIndex(PosterTableMetaData.PROFILE_ICON_URL)));
		poster.setUserScreenName(cursor.getString(cursor
				.getColumnIndex(PosterTableMetaData.USER_SCREEN_NAME)));
		poster.setParticipantNum(cursor.getInt(cursor
				.getColumnIndex(PosterTableMetaData.PARTICIPANT_NUM)));
		poster.setWantedNum(cursor.getInt(cursor
				.getColumnIndex(PosterTableMetaData.WANTED_NUM)));
		//poster.setReleasedTime(cursor.getLong(cursor.getColumnIndex(cursor
		//		.getColumnIndex(PosterTableMetaData.CREATED_AT)));
		return null;
	}

	class ViewHolder {
		public ImageView ivProfileIcon;
		public TextView tvSponserName;
		public TextView tvPosterTitle;
		public TextView tvReleasedTime;
		public TextView tvParticipantNum;
	}

}
