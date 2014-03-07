package com.campuspo.service;

import java.util.HashMap;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.campuspo.BuildConfig;
import com.campuspo.http.GetParticipantExecuter;
import com.campuspo.http.GetProfileExecuter;
import com.campuspo.http.GetPublicTimelineExecuter;
import com.campuspo.http.GetSponsorTimelineExecuter;
import com.campuspo.http.HttpExecuter;
import com.campuspo.http.JoinExecuter;
import com.campuspo.http.LoginExecuter;
import com.campuspo.http.PosterQuitExecuter;
import com.campuspo.http.PublishPosterExecuter;
import com.campuspo.http.Result;

public class CampusPoService extends IntentService{
	
	private static final String TAG = "CampusPoService";
	
	public static final String REQUEST_PARAMS = "REQUEST_PARAMS";
	public static final String REQUEST_CALLBACK = "REQUEST_CALLBACK";
	
	private ResultReceiver mCallback;

	public CampusPoService() {
		super("CampusPoService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		if(BuildConfig.DEBUG)
			Log.d(TAG, "onHandleIntent() called");
		
		Intent requestIntent = intent;
		int requestType = requestIntent.getIntExtra(ServiceContants.REQUEST_TYPE, 0);
		
		@SuppressWarnings("unchecked")
		HashMap<String, String> params =
				(HashMap<String, String>) requestIntent.getSerializableExtra(CampusPoService.REQUEST_PARAMS);
		mCallback = (ResultReceiver)requestIntent.getParcelableExtra(CampusPoService.REQUEST_CALLBACK);
		
		HttpExecuter executer = createExecuter(requestType, params);
		
		Result result = executer.excute();
		
		int resultCode = result.getResultCode();
		
		if(BuildConfig.DEBUG)
			Log.d(TAG, "send result");
		
		mCallback.send(resultCode, buildResultData(requestType, result));
	}
	
	public HttpExecuter createExecuter(int requestType, 
			HashMap<String, String> params) {
		
		HttpExecuter executer = null;
		switch (requestType) {
		case ServiceContants.REQUEST_PUBLIC_TIMELINE :
			executer = new GetPublicTimelineExecuter(params);
			break;		
		case ServiceContants.REQUEST_SPONSOR_TIMELINE :
			executer = new GetSponsorTimelineExecuter(params);
			break;			
		case ServiceContants.REQUEST_PROFILE :
			executer = new GetProfileExecuter(params);
			break;
		case ServiceContants.REQUEST_PUBLISH_POSTER :
			executer = new PublishPosterExecuter(params);
			break;
		case ServiceContants.REQUEST_LOGIN :
			executer = new LoginExecuter(params);
			break;
		case ServiceContants.REQUEST_JOIN :
			executer = new JoinExecuter(params);
			break;
		case ServiceContants.REQUEST_QUIT:
			executer = new PosterQuitExecuter(params);
			break;
		case ServiceContants.REQUEST_PARTICIPANTS :
			executer = new GetParticipantExecuter(params);
			break;
		}
		return executer;
	}
	
	public Bundle buildResultData(int requestType, Result result) {
		Bundle data = new Bundle();
		
		data.putInt(ServiceContants.REQUEST_TYPE, requestType);
		data.putString(ServiceContants.RESULT_ERROR_MSG, result.getErrorMessage());
		data.putSerializable(ServiceContants.RESULT_SERIALIZABLE,result.getEntity());
		
		
		return data;
	}
	
	
}
