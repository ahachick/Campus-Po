package com.campuspo.provider;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.campuspo.BuildConfig;
import com.campuspo.provider.PublicTimelineProviderMetaData.PosterTableMetaData;

public class PublicTimelineProvider extends ContentProvider {

	private static final String TAG = PublicTimelineProvider.class
			.getSimpleName();

	private static HashMap<String, String> sPostersProjectionMap;// set the
																	// projection
																	// from
																	// contentvalue
																	// to table

	static {
		sPostersProjectionMap = new HashMap<String, String>();
		sPostersProjectionMap.put(PosterTableMetaData.POSTER_ID,
				PosterTableMetaData.POSTER_ID);
		sPostersProjectionMap.put(PosterTableMetaData.POSTER_TITLE,
				PosterTableMetaData.POSTER_TITLE);
		sPostersProjectionMap.put(PosterTableMetaData.POSTER_DESCRIPTION,
				PosterTableMetaData.POSTER_DESCRIPTION);
		// More key-values here

	}

	private static final UriMatcher sUriMatcher;
	private static final int INCOMING_POSTER_COLLECTION_URI_INDICATOR = 1;
	private static final int INCOMING_POSTER_ITEM_URI_INDICATOR = 2;
	// Initial the urimatcher
	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(PublicTimelineProviderMetaData.AUTHORITY, "posters",
				INCOMING_POSTER_COLLECTION_URI_INDICATOR);
		sUriMatcher.addURI(PublicTimelineProviderMetaData.AUTHORITY,
				"posters/#", INCOMING_POSTER_ITEM_URI_INDICATOR);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, PublicTimelineProviderMetaData.DATABASE_NAME, null,
					PublicTimelineProviderMetaData.DATABASE_VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			if (BuildConfig.DEBUG)
				Log.d(TAG, "inner oncreated called");
			StringBuilder statement = new StringBuilder();
			statement.append("CREATE TABLE ")
					.append(PosterTableMetaData.TABLE_NAME).append("(")
					.append(PosterTableMetaData.POSTER_ID)
					.append(" LONG PRIMARY KEY,")
					.append(PosterTableMetaData.POSTER_TITLE).append(" TEXT,")
					.append(PosterTableMetaData.POSTER_DESCRIPTION)
					.append(" TEXT,").append(PosterTableMetaData.WANTED)
					.append(" INTEGER,").append(PosterTableMetaData.WANTED_NUM)
					.append(" INTEGER,")
					.append(PosterTableMetaData.PARTICIPANT_NUM)
					.append(" INTEGER,").append(PosterTableMetaData.CREATED_AT)
					.append(" TEXT,").append(PosterTableMetaData.FAVORITED)
					.append(" INTEGER,").append(PosterTableMetaData.JOINED)
					.append(" INTEGER,").append(PosterTableMetaData.IS_SPONSOR)
					.append(" INTEGER,").append(PosterTableMetaData.USER_ID)
					.append(" LONG,")
					.append(PosterTableMetaData.USER_SCREEN_NAME)
					.append(" TEXT,")
					.append(PosterTableMetaData.PROFILE_ICON_URL)
					.append(" TEXT").append(");");
			db.execSQL(statement.toString());
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			if (BuildConfig.DEBUG)
				Log.d(TAG, "inner onupgrade called");

			db.execSQL("DROP TABLE IF EXISTS " + PosterTableMetaData.TABLE_NAME);
			onCreate(db);
		}
	}

	DatabaseHelper mOpenHelper;

	@Override
	public boolean onCreate() {

		if (BuildConfig.DEBUG)
			Log.d(TAG, "main oncreated called");
		mOpenHelper = new DatabaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (sUriMatcher.match(uri)) {
		case INCOMING_POSTER_COLLECTION_URI_INDICATOR:
			qb.setTables(PosterTableMetaData.TABLE_NAME);
			qb.setProjectionMap(sPostersProjectionMap);
			break;
		case INCOMING_POSTER_ITEM_URI_INDICATOR:
			qb.setTables(PosterTableMetaData.TABLE_NAME);
			qb.setProjectionMap(sPostersProjectionMap);
			qb.appendWhere(PosterTableMetaData.POSTER_ID + "="
					+ uri.getPathSegments().get(1));
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);

		}

		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, null);

		int i = c.getCount();
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {
		case INCOMING_POSTER_COLLECTION_URI_INDICATOR:
			return PosterTableMetaData.CONTENT_TYPE;
		case INCOMING_POSTER_ITEM_URI_INDICATOR:
			return PosterTableMetaData.CONTENT_ITEM_TYPE;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);

		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		throw new UnsupportedOperationException("Unsupported Operation: insert");
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		switch (sUriMatcher.match(uri)) {
		case INCOMING_POSTER_COLLECTION_URI_INDICATOR:

			
			db.delete(PosterTableMetaData.TABLE_NAME, null, null);
			break;
		case INCOMING_POSTER_ITEM_URI_INDICATOR:
			db.delete(PosterTableMetaData.TABLE_NAME, "poster_id = ?",
					new String[] { uri.getPathSegments().get(1) });
			break;
		default:
			
		}
		
		db.close();
		throw new IllegalArgumentException("Invalid uri: " + uri.toString());

	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		throw new UnsupportedOperationException("Unsupported Operation: update");
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		
		switch(sUriMatcher.match(uri)) {
		case INCOMING_POSTER_COLLECTION_URI_INDICATOR:
			
			SQLiteDatabase db = mOpenHelper.getWritableDatabase();
			db.beginTransaction();
			
			db.delete(PosterTableMetaData.TABLE_NAME, null, null);
			
			int numOfArray = values.length;
			for(int i = 0; i < numOfArray; i++) {
				db.insert(PosterTableMetaData.TABLE_NAME, null, values[i]);
			}
			
			db.setTransactionSuccessful();
			db.endTransaction();
			db.close();
			
			getContext().getContentResolver().notifyChange(uri, null);
			
			return numOfArray;
		
		default :
			throw new IllegalArgumentException("Invalid uri : " + uri);
		}
		

		//return super.bulkInsert(uri, values);
	}

}
