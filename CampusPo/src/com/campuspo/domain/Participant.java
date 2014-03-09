package com.campuspo.domain;

import java.text.ParseException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class Participant extends Entity implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8823164832226165565L;

	private User user;
	
	private Date joinedAt;
	private long posterId;
	
	public Participant(JSONObject object){
		init(object);
	}
	
	public void init(JSONObject object) {
		try {
			this.setUser(new User(object));
			
			this.joinedAt = parseDate(object.getString("join_at"));//----------------
			this.setPosterId(object.getLong("poster_id"));
		} catch (JSONException e) {
			
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	

	public Date getJoinedAt() {
		return joinedAt;
	}

	public void setJoinedAt(Date joinedAt) {
		this.joinedAt = joinedAt;
	}

	public long getPosterId() {
		return posterId;
	}

	public void setPosterId(long posterId) {
		this.posterId = posterId;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
}
