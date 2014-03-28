package com.campuspo.domain;


import org.json.JSONException;
import org.json.JSONObject;

public class Delegation implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7614622812582217135L;

	
	private long delegationId;
	private long userId;
	private String userScreenName;
	private String profileIconUrl;
	private String delegationTitle;
	private String delegationDescription;
	private String reward;
	private long createAt;
	
	public Delegation() {
	}
	
	public Delegation(JSONObject object) {
		init(object);
	}
	
	public void init(JSONObject object) {
		
		if(object == null) {
			return;
		}
		try {
			
			this.delegationId = object.getLong("delegation_id");
			this.delegationTitle = object.getString("delegation_title");
			//this.delegationDescription = object.getString("delegation_description");
			this.profileIconUrl = object.getString("profile_icon_url");
			this.userId = object.getLong("user_id");
			this.userScreenName = object.getString("user_screen_name");
			this.reward = object.getString("reward");
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		} 
		
	}
	
	public long getDelegationId() {
		return delegationId;
	}
	public void setDelegationId(long delegationId) {
		this.delegationId = delegationId;
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
	public void setProfilIconUrl(String profileIconUrl) {
		this.profileIconUrl = profileIconUrl;
	}
	public String getDelegationTitle() {
		return delegationTitle;
	}
	public void setDelegationTitle(String delegationTitle) {
		this.delegationTitle = delegationTitle;
	}
	public String getDelegationDescription() {
		return delegationDescription;
	}
	public void setDelegationDscription(String delegationDescription) {
		this.delegationDescription = delegationDescription;
	}
	public String getReward() {
		return reward;
	}
	public void setReward(String reward) {
		this.reward = reward;
	}

	public long getCreateAt() {
		return createAt;
	}

	public void setCreateAt(long createAt) {
		this.createAt = createAt;
	}
}
