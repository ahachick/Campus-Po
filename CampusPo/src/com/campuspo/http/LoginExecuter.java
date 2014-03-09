package com.campuspo.http;

import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URI;
import java.util.HashMap;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import com.campuspo.BuildConfig;
import com.campuspo.domain.User;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class LoginExecuter extends HttpExecuter {
	
	public static final String TAG = LoginExecuter.class.getSimpleName();
	
	private final static Method methodType = Method.GET;
	private final static String PATH = "/userlogin";	
	
	private HashMap<String, String> mParams;

	public LoginExecuter(HashMap<String,String> params) {
		mParams = params;
	}

	@Override
	protected Request buildRequest() {
		
		//URI uri = URI.create(HttpExecuter.HttpMetaData.DOMAIN + PATH);
		
		//byte[] requestBody = HttpUtils.parseParamsToByteArray(mParams);
		
		//============have to modify
		CookieManager manager = new CookieManager();
		CookieHandler.setDefault(manager);
		
		StringBuilder sb = new StringBuilder();
		sb.append(HttpExecuter.HttpMetaData.DOMAIN).append(PATH);
		if(mParams != null)
			sb.append("?");
			for(Entry<String, String> entry: mParams.entrySet()) {
				sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		
		
		
		String uriString = sb.substring(0, sb.length() - 1);
		
		if(BuildConfig.DEBUG)
			Log.d(TAG, uriString);
		URI uri = URI.create(uriString);
		return new Request(methodType, uri, null,null);
	}

	@Override
	protected Result buildResult(Response response) {
		
		int statusCode = response.getStatusCode;
		
		Result result = null;
		byte[] body = response.getBody();
		
		if(BuildConfig.DEBUG)
			try {
				Log.d(TAG,  new String(body, "UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		// have to set the cookie
		
		User user = null;
		
		String errorMsg = null;
		
		try {
			
			if(statusCode != -1) {
				String bodyString = new String(body, "UTF-8");
				
				JSONObject jsonObject = new JSONObject(bodyString);
				
				Log.d(TAG, "==="+jsonObject.toString());
				
				int code = jsonObject.getInt(KEY_CODE);
						
				if(code == 1) {
					JSONObject data = jsonObject.getJSONObject(KEY_DATA);
					user = new User(data);
				}else
					errorMsg = jsonObject.getString(KEY_MESSAGE);
				
				result = new Result(code, errorMsg, user); 
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
