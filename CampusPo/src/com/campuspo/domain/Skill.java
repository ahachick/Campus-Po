package com.campuspo.domain;

import org.json.JSONException;
import org.json.JSONObject;

public class Skill implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5456781655015066399L;
	private long masterId;
	private long skillId;
	private String skillDescription;
	private int likeNum;
	
	public Skill(String str) {
		try {
			init(new JSONObject(str));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Skill(JSONObject object) {
		init(object);
	}
	
	public void init(JSONObject object) {
		
		try {
			this.skillId = object.getLong("skill_id");
			this.skillDescription = object.getString("skill_description");
			this.masterId = object.getLong("masterId");
			this.likeNum = object.getInt("like_num");
		} catch (JSONException e) {
			e.printStackTrace();
		}
				
	}
	
	public long getSkillId() {
		return skillId;
	}
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	public long getMasterId() {
		return masterId;
	}
	public void setMasterId(int masterId) {
		this.masterId = masterId;
	}
	public String getSkillDescription() {
		return skillDescription;
	}
	public void setSkillDecription(String skillName) {
		this.skillDescription = skillName;
	}
	public int getLikeNum() {
		return likeNum;
	}
	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
	}

}
