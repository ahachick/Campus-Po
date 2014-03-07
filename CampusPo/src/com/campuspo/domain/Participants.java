package com.campuspo.domain;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class Participants implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6098771231882183791L;
	private ArrayList<Participant> participants;

	public Participants(JSONArray array) {
		init(array);
	}
	
	
	

	public void init(JSONArray jsonArray) {
		int length = jsonArray.length();

		participants = new ArrayList<Participant>();

		try {
			for (int i = 0; i < length; i++)
				participants.add(new Participant(jsonArray.getJSONObject(i)));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<Participant> getParticipants() {
		return participants;
	}

}
