package com.campuspo.domain;


import java.text.ParseException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class User extends Entity implements java.io.Serializable{

	private static final String TAG = "User";
	/**
	 * 
	 */
	private static final long serialVersionUID = 6235826363309376722L;
	private long userId;
	private String userEmail;
	private String userScreenName;
	private String profileIconUrl;
	private String profileMiddelIconUrl;
	private String profileBigIconUrl;
	private String profileBackgroundUrl;
	private String userDescription;
	
	private String userName;
	private int gender;
	private String school;
	private Date createdAt;
	
	private boolean following;
	private long followingCount;
	
	
	public User(JSONObject object) {
		try {
			this.userId = object.getLong("user_id");
			this.userEmail = object.getString("user_email");
			this.userScreenName = object.getString("user_screen_name");
			this.profileIconUrl = object.getString("profile_icon_url");
			this.userDescription = object.getString("user_description");
			this.userName = object.getString("user_name");
			//this.gender = object.getInt("gender");
			this.createdAt = parseDate(object.getString("created_at"));
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("parsing Date occurs Error");
			e.printStackTrace();
		}		
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getProfileIconUrl() {
		return profileIconUrl;
	}

	public void setProfileIconUrl(String profileIconUrl) {
		this.profileIconUrl = profileIconUrl;
	}

	public String getProfileMiddelIconUrl() {
		return profileMiddelIconUrl;
	}

	public void setProfileMiddelIconUrl(String profileMiddelIconUrl) {
		this.profileMiddelIconUrl = profileMiddelIconUrl;
	}

	public String getProfileBackgroundUrl() {
		return profileBackgroundUrl;
	}

	public void setProfileBackgroundUrl(String profileBackgroundUrl) {
		this.profileBackgroundUrl = profileBackgroundUrl;
	}

	public String getProfileBigIconUrl() {
		return profileBigIconUrl;
	}

	public void setProfileBigIconUrl(String profileBigIconUrl) {
		this.profileBigIconUrl = profileBigIconUrl;
	}



	public String getUserDescription() {
		return userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isFollowing() {
		return following;
	}

	public void setFollowing(boolean following) {
		this.following = following;
	}

	public long getFollowingCount() {
		return followingCount;
	}

	public void setFollowingCount(long followingCount) {
		this.followingCount = followingCount;
	}




	
	
}
