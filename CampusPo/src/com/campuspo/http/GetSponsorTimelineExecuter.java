package com.campuspo.http;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.campuspo.BuildConfig;
import com.campuspo.domain.Timeline;

public class GetSponsorTimelineExecuter extends HttpExecuter{
	
	private static final String TAG = GetSponsorTimelineExecuter.class.getSimpleName();
	
	private static final String PATH = "/getMySponsorPosters";
	
	private static final Method sMethodType = Method.GET;
	
	private HashMap<String, String> mParams;
	
	public GetSponsorTimelineExecuter(HashMap<String, String> params) {
		this.mParams = params;
	}

	@Override
	protected Request buildRequest() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(HttpExecuter.HttpMetaData.DOMAIN).append(PATH);
		/*if(mParams != null)
			sb.append("?");
			for(Entry<String, String> entry: mParams.entrySet()) {
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		
		
		
		String uriString = sb.substring(0, sb.length() - 1);*/
		
		//if(BuildConfig.DEBUG)
		//	Log.d(TAG, uriString);
		URI uri = URI.create(sb.toString());
		
		Request request = new Request(sMethodType, uri, null, null);
		
		return request;
	}

	@Override
	protected Result buildResult(Response response) {
		
		int statusCode = response.getStatusCode;
		
		Result result = null;
		byte[] body = response.getBody();
		
		if(BuildConfig.DEBUG)
			Log.d(TAG, body.toString());
		// have to set the cookie
		
		Timeline timeline = null;
		
		String errorMsg = null;
		
		try {
			
			if(statusCode != -1) {
				String bodyString = new String(body, "UTF-8");
				
				JSONObject jsonObject = new JSONObject(bodyString);
				
				Log.d(TAG, "==="+jsonObject.toString());
				
				int code = jsonObject.getInt(KEY_CODE);
						
				if(code == 1) {
					JSONObject data = jsonObject.getJSONObject(KEY_DATA);
					JSONArray ob = data.getJSONArray("timeline");
					timeline = new Timeline(ob);
				}else
					errorMsg = jsonObject.getString(KEY_MESSAGE);
				
				result = new Result(code, errorMsg, timeline); 
			}
			else 
				result = new Result(statusCode, null, null);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if(result == null)
			result = new Result(-1, null, null);
		
		return result;
	}
	
	
}
