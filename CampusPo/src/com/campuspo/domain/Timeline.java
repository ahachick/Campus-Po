package com.campuspo.domain;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

public class Timeline implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3307897780997318385L;
	private List<Poster> posters;

	public Timeline() {
		
	}
	
	public Timeline(JSONArray array) {
		init(array);
	}

	public void init(JSONArray jsonArray) {
		int length = jsonArray.length();

		posters = new ArrayList<Poster>();

		try {
			for (int i = 0; i < length; i++)
				posters.add(new Poster(jsonArray.getJSONObject(i)));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void setPosters(List<Poster> posters) {
		this.posters =posters;
	}

	public List<Poster> getPosters() {

		return posters;
	}
}
