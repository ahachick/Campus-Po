package com.campuspo.domain;

import java.text.ParseException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.campuspo.BuildConfig;

import android.util.Log;

public class Poster extends Entity implements java.io.Serializable {
	
	private static final String TAG = "Poster";

	/**
	 * 
	 */
	private static final long serialVersionUID = 3340186305346216581L;
	private long posterId;
	private String posterTitle;
	private String posterDescription;
	private boolean wanted;
	private int wantedNum;
	private int participantNum;
	private Date releasedTime;
	
	private long userId;
	private String userScreenName;
	private String profileIconUrl;

	private boolean favorited = false;
	private boolean joined = false;
	private boolean isSponsor = false;

	
	public Poster() {}
	
	public Poster(JSONObject object) {
		init(object);
	}

	public Poster(String str) {
		try {
			init(new JSONObject(str));
		} catch (JSONException e) {

			e.printStackTrace();
		}
	}

	public void init(JSONObject object) {
		try {
			this.posterId = object.getLong("poster_id");
			this.posterTitle = object.getString("poster_title");
			this.posterDescription = object.getString("poster_description");
			this.wanted = object.getBoolean("wanted");
			this.wantedNum = object.getInt("wanted_num");
			this.participantNum = object.getInt("participant_num");
			
			// parseDate by format "yyyy-MM-dd HH:mm:ss.S"
			this.releasedTime = parseDate(object.getString("poster_released_time"));
			this.userId = object.getLong("user_id");
			this.userScreenName = object.getString("user_screen_name");//----------
			this.profileIconUrl = object
					.getString("profile_icon_url");
			

			this.favorited = object.getBoolean("favorited");
			this.joined = object.getBoolean("joined");
			this.isSponsor = object.getBoolean("is_sponsor");
		} catch (JSONException e) {

			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	public long getPosterId() {
		return posterId;
	}

	public void setPosterId(long posterId) {
		this.posterId = posterId;
	}

	public String getPosterTitle() {
		return posterTitle;
	}

	public void setPosterTitle(String posterTitle) {
		this.posterTitle = posterTitle;
	}

	public String getPosterDescription() {
		return posterDescription;
	}

	public void setPosterDescription(String posterDescription) {
		this.posterDescription = posterDescription;
	}

	public int getWantedNum() {
		return wantedNum;
	}

	public void setWantedNum(int wantedNum) {
		this.wantedNum = wantedNum;
	}

	public boolean isWanted() {
		return wanted;
	}

	public void setWanted(boolean wanted) {
		this.wanted = wanted;
	}

	public int getParticipantNum() {
		return participantNum;
	}

	public void setParticipantNum(int participantNum) {
		this.participantNum = participantNum;
	}

	public Date getReleasedTime() {
		return releasedTime;
	}

	public void setReleasedTime(Date releasedTime) {
		this.releasedTime = releasedTime;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserScreenName() {
		return userScreenName;
	}

	public void setUserScreenName(String userScreenName) {
		this.userScreenName = userScreenName;
	}

	public String getProfileIconUrl() {
		return profileIconUrl;
	}

	public void setProfileIconUrl(String profileIconUrl) {
		this.profileIconUrl = profileIconUrl;
	}

	public boolean isJoined() {
		return joined;
	}

	public void setJoined(boolean joined) {
		this.joined = joined;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	public boolean isSponsor() {
		return isSponsor;
	}

	public void setSponsor(boolean isSponsor) {
		this.isSponsor = isSponsor;
	}

}