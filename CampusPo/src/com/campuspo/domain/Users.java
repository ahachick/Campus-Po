package com.campuspo.domain;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class Users implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4251343147274267897L;
	private ArrayList<User> users;
	
	public Users(JSONArray array) {
		init(array);
	}

	public void init(JSONArray jsonArray) {
		int length = jsonArray.length();

		users = new ArrayList<User>();

		try {
			for (int i = 0; i < length; i++)
				users.add(new User(jsonArray.getJSONObject(i)));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<User> getUsers() {
		return users;
	}

}
