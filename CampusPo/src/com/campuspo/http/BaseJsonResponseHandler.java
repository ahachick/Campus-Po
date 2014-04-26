package com.campuspo.http;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.campuspo.R;
import com.campuspo.app.CampusPoApplication;
import com.campuspo.app.Constants;
import com.eric.android.http.TextResponseHandler;

public abstract class BaseJsonResponseHandler extends TextResponseHandler{
	
	private static final String KEY_ERROR_MESSAGE = "error_message";
	private static final String KEY_ERROR_CODE = "error_code";
	
	protected abstract void onSuccess(JSONObject jsonObject);
	protected abstract void onFailure(int errorCode, String errorMessage);
	
	@Override
	protected void onSuccess(int statusCode, Map<String, List<String>> headers,
			String responseStr) {
		
		int errorCode;
		String errorMessage;
		
		if(statusCode == 200) {
			try {
				JSONObject jsonObject = new JSONObject(responseStr);
				if(0 != jsonObject.optInt(KEY_ERROR_CODE)) {
					errorCode = jsonObject.getInt(KEY_ERROR_CODE);
					errorMessage = jsonObject.optString(KEY_ERROR_MESSAGE);
					onFailure(errorCode, errorMessage);
				} else {
					onSuccess(jsonObject);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onFailure(int statusCode, Map<String, List<String>> headers,
			String responseString, Throwable t) {
		int errorCode;
		String errorString;
		if(t != null) {
			if(t instanceof UnknownHostException) {
				errorCode = Constants.ERROR_UNKNOWN_HOST;
				errorString = CampusPoApplication.getAppContext().getString(R.string.error_unknown_host);
			}else if(t instanceof SocketTimeoutException) {
				errorCode = Constants.ERROR_TIMEOUT;
				errorString = CampusPoApplication.getAppContext().getString(R.string.error_timeout);
			}else {
				errorCode = 0;
				errorString = "";
			}
			
			onFailure(errorCode, errorString);
		}
		
	}
	

}
