package com.campuspo.http;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.campuspo.domain.User;
import com.campuspo.util.Logger;

public class GetProfileExecuter extends HttpExecuter{
	
	public static final String TAG = GetProfileExecuter.class.getSimpleName();
	
	private final static Method methodType = Method.GET;
	private final static String PATH = "/getUserInfo";	
	
	private HashMap<String, String> mParams;
	
	public GetProfileExecuter( HashMap<String, String> params) {
		mParams = params;
	}

	@Override
	protected Request buildRequest() {
		
		URI uri = URI.create(HttpExecuter.HttpMetaData.DOMAIN + PATH);
				
		return new Request(methodType, uri, null, null);
	}

	@Override
	protected Result buildResult(Response response) {
		
		Result result = null;
		if(response.getStatusCode != -1) {
			Logger.debug(TAG, "========");
			try {
				String bodyString = new String(response.getBody(), "UTF-8");
				JSONObject object = new JSONObject(bodyString);
				
				int code = object.getInt(KEY_CODE);
				
				String errorMsg = null;
				User user = null;
				if(code == 1)  {
					JSONObject data = object.getJSONObject("data");					
					user = new User(data);					
				}else
					errorMsg = object.getString(KEY_MESSAGE);
				
				Logger.debug(TAG, "========");
				result = new Result(code, errorMsg, user);	
				
				
			} catch (UnsupportedEncodingException e) {
				
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(result == null)
			result = new Result(-1, null, null);
		
		return result;
	}

}
