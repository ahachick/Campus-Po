package com.campuspo.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class PublicTimelineProviderMetaData {
	public static final String AUTHORITY = "com.campuspo.provider.PublicTimelineProvider";
	
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "public_timeline.db";
	public static final String POSTERS_TABLE_NAME = "posters";
	
	private PublicTimelineProviderMetaData() {
		
	}
	
	public static final class PosterTableMetaData 
					implements BaseColumns{
		private PosterTableMetaData() {
			
		}
		
		public static final String TABLE_NAME = "posters";
		
		public static final Uri CONTENT_URI =
					Uri.parse("content://" + AUTHORITY +"/posters");
		public static final String CONTENT_TYPE =
					"vnd.android.cursor.dir/vnd.campuspo.poster";
		public static final String CONTENT_ITEM_TYPE =
					"vnd.android.cursor.item/vnd.campuspo.poster";
		
		public static final String DEFAULT_SORT_ORDER = "DESC";
		
		public static final String POSTER_ID = "poster_id";
		public static final String POSTER_TITLE = "poster_title";
		public static final String POSTER_DESCRIPTION = "poster_description";
		public static final String WANTED = "wanted";
		public static final String WANTED_NUM = "wanted_num";
		public static final String PARTICIPANT_NUM = "participant_num";
		public static final String CREATED_AT = "created_at";
		public static final String JOINED = "joined";
		public static final String FAVORITED = "favortited";
		public static final String USER_ID = "user_id";
		public static final String USER_SCREEN_NAME = "user_screen_name";
		public static final String PROFILE_ICON_URL = "profile_icon_url";
		public static final String PROFILE_BIG_ICON_URL = "profile_big_icon_url";
		public static final String IS_SPONSOR = "is_sponsor";
	}
}
