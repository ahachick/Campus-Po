package com.campuspo.service;

import java.util.HashMap;
import java.util.LinkedList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

import com.campuspo.BuildConfig;
import com.campuspo.app.CampusPoApplication;
import com.campuspo.util.NetworkUtils;

public class CampusPoServiceHelper {
	private static final String TAG = CampusPoServiceHelper.class
			.getSimpleName();

	private static CampusPoServiceHelper instance;

	public static final String ACTION_REQUEST_RESULT = "ACTION_REQUEST_RESULT";
	public static final String REQUEST_TYPE = "REQUEST_TYPE";
	public static final String REQUEST_RESULT_CODE = "REQUEST_RESULT_CODE";
	public static final String REQUEST_RESULT_DATA = "REQUEST_RESUTL_DATA";

	private Context mContext;
	private LinkedList<Integer> mPendingRequests = new LinkedList<Integer>();

	private CampusPoServiceHelper(Context ctx) {
		mContext = ctx;
	}

	public synchronized static CampusPoServiceHelper getInstance(Context ctx) {
		if (instance == null)
			instance = new CampusPoServiceHelper(ctx);
		return instance;
	}

	// fetch the latest, default length of the timeline, the default length is
	// 20;
	public void getPublicTimeline() {
		this.getPublicTimeline(-1, 20);
	}

	public void getPublicTimeline(int startId, int length) {

		if (BuildConfig.DEBUG)
			Log.d(TAG, "getPublicTime() called");
		
		//check whether the network is available
		if(!isNetworkConnected())
					return;

		// if(mPendingRequests.contains(ServiceContants.REQUEST_PUBLIC_TIMELINE))
		// return;

		mPendingRequests.add(ServiceContants.REQUEST_PUBLIC_TIMELINE);

		Intent intent = new Intent(mContext, CampusPoService.class);

		ResultReceiver serviceCallback = new ResultReceiver(null) {

			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {

				if (BuildConfig.DEBUG)
					Log.d(TAG, "onReceiveResult() called");
				handleRequestResponse(resultCode, resultData);
			}
		};

		HashMap<String, String> params = new HashMap<String, String>();

		params.put("startId", "" + startId);
		params.put("length", "" + length);

		intent.putExtra(ServiceContants.REQUEST_TYPE,
				ServiceContants.REQUEST_PUBLIC_TIMELINE);
		intent.putExtra(CampusPoService.REQUEST_PARAMS, params);
		intent.putExtra(CampusPoService.REQUEST_CALLBACK, serviceCallback);

		if (BuildConfig.DEBUG)
			Log.d(TAG, "start Service");

		mContext.startService(intent);
	}

	public void getUserTimeline(int uid) {

	}

	public void getSponsorTimeline(int uid, int startId, int length) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "getPublicTime() called");
		
		//check whether the network is available
		if(!isNetworkConnected())
					return;

		// if(mPendingRequests.contains(ServiceContants.REQUEST_PUBLIC_TIMELINE))
		// return;

		mPendingRequests.add(ServiceContants.REQUEST_SPONSOR_TIMELINE);

		Intent intent = new Intent(mContext, CampusPoService.class);

		ResultReceiver serviceCallback = new ResultReceiver(null) {

			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {

				if (BuildConfig.DEBUG)
					Log.d(TAG, "onReceiveResult() called");
				handleRequestResponse(resultCode, resultData);
			}
		};

		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("user_id", "" + uid);
		params.put("startId", "" + startId);
		params.put("length", "" + length);

		intent.putExtra(ServiceContants.REQUEST_TYPE,
				ServiceContants.REQUEST_SPONSOR_TIMELINE);
		intent.putExtra(CampusPoService.REQUEST_PARAMS, params);
		intent.putExtra(CampusPoService.REQUEST_CALLBACK, serviceCallback);

		if (BuildConfig.DEBUG)
			Log.d(TAG, "start Service");

		mContext.startService(intent);
	}
	
	public void getParticipants(long posterId) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "getParticipants() called");
		
		//check whether the network is available
		if(!isNetworkConnected())
					return;

		// if(mPendingRequests.contains(ServiceContants.REQUEST_PUBLIC_TIMELINE))
		// return;

		mPendingRequests.add(ServiceContants.REQUEST_PARTICIPANTS);

		Intent intent = new Intent(mContext, CampusPoService.class);

		ResultReceiver serviceCallback = new ResultReceiver(null) {

			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {

				if (BuildConfig.DEBUG)
					Log.d(TAG, "onReceiveResult() called");
				handleRequestResponse(resultCode, resultData);
			}
		};

		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("poster_id", "" + posterId);


		intent.putExtra(ServiceContants.REQUEST_TYPE,
				ServiceContants.REQUEST_PARTICIPANTS);
		intent.putExtra(CampusPoService.REQUEST_PARAMS, params);
		intent.putExtra(CampusPoService.REQUEST_CALLBACK, serviceCallback);

		if (BuildConfig.DEBUG)
			Log.d(TAG, "start Service");

		mContext.startService(intent);
	}
	
	public void join(long posterId) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "join() called");
		
		//check whether the network is available
		if(!isNetworkConnected())
					return;

		// if(mPendingRequests.contains(ServiceContants.REQUEST_PUBLIC_TIMELINE))
		// return;

		mPendingRequests.add(ServiceContants.REQUEST_JOIN);

		Intent intent = new Intent(mContext, CampusPoService.class);

		ResultReceiver serviceCallback = new ResultReceiver(null) {

			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {

				if (BuildConfig.DEBUG)
					Log.d(TAG, "onReceiveResult() called");
				handleRequestResponse(resultCode, resultData);
			}
		};

		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("poster_id", "" + posterId);


		intent.putExtra(ServiceContants.REQUEST_TYPE,
				ServiceContants.REQUEST_JOIN);
		intent.putExtra(CampusPoService.REQUEST_PARAMS, params);
		intent.putExtra(CampusPoService.REQUEST_CALLBACK, serviceCallback);

		if (BuildConfig.DEBUG)
			Log.d(TAG, "start Service");

		mContext.startService(intent);
		
	}
	
	public void quit(long posterId) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "quit() called");
		
		//check whether the network is available
		if(!isNetworkConnected())
					return;

		// if(mPendingRequests.contains(ServiceContants.REQUEST_PUBLIC_TIMELINE))
		// return;

		mPendingRequests.add(ServiceContants.REQUEST_QUIT);

		Intent intent = new Intent(mContext, CampusPoService.class);

		ResultReceiver serviceCallback = new ResultReceiver(null) {

			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {

				if (BuildConfig.DEBUG)
					Log.d(TAG, "onReceiveResult() called");
				handleRequestResponse(resultCode, resultData);
			}
		};

		HashMap<String, String> params = new HashMap<String, String>();
		
		params.put("poster_id", "" + posterId);


		intent.putExtra(ServiceContants.REQUEST_TYPE,
				ServiceContants.REQUEST_QUIT);
		intent.putExtra(CampusPoService.REQUEST_PARAMS, params);
		intent.putExtra(CampusPoService.REQUEST_CALLBACK, serviceCallback);

		if (BuildConfig.DEBUG)
			Log.d(TAG, "start Service");

		mContext.startService(intent);
		
	}


	public void getUserProfile(long userId) {

	}

	public void getUserProfile() {
		
		if (BuildConfig.DEBUG)
			Log.d(TAG, "getUserProfile() called");

		//check whether the network is available
				if(!isNetworkConnected())
					return;
		
		mPendingRequests.add(ServiceContants.REQUEST_PROFILE);

		Intent intent = new Intent(mContext, CampusPoService.class);

		ResultReceiver serviceCallback = new ResultReceiver(null) {

			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {

				if (BuildConfig.DEBUG)
					Log.d(TAG, "onReceiveResult() called");
				handleRequestResponse(resultCode, resultData);
			}
		};

		HashMap<String, String> params = new HashMap<String, String>();

		intent.putExtra(ServiceContants.REQUEST_TYPE,
				ServiceContants.REQUEST_PROFILE);
		intent.putExtra(CampusPoService.REQUEST_PARAMS, params);
		intent.putExtra(CampusPoService.REQUEST_CALLBACK, serviceCallback);

		if (BuildConfig.DEBUG)
			Log.d(TAG, "start Service");

		mContext.startService(intent);
	}
	
	public void publishPoster(String title, String description,
								boolean wanted, int wantedNum) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "publishPoster() called");
		//check whether the network is available
		if(!isNetworkConnected())
			return;

		mPendingRequests.add(ServiceContants.REQUEST_PUBLISH_POSTER);

		Intent intent = new Intent(mContext, CampusPoService.class);

		ResultReceiver serviceCallback = new ResultReceiver(null) {

			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {

				if (BuildConfig.DEBUG)
					Log.d(TAG, "onReceiveResult() called" + resultCode);
				if(resultCode == 200) {
					Toast.makeText(CampusPoApplication.getAppContext(),
										"发布成功",Toast.LENGTH_SHORT).show();//have to modify
				}else 
					Toast.makeText(CampusPoApplication.getAppContext(),
							"无法连接服务器：网络超时",Toast.LENGTH_SHORT).show();//have to modify
			}
		};

		HashMap<String, String> params = new HashMap<String, String>();

		params.put("poster_title", "" + title);
		params.put("poster_description", "" + description);
		params.put("wanted", "" + wanted);
		params.put("wanted_num", "" + wantedNum);

		intent.putExtra(ServiceContants.REQUEST_TYPE,
				ServiceContants.REQUEST_PUBLISH_POSTER);
		intent.putExtra(CampusPoService.REQUEST_PARAMS, params);
		intent.putExtra(CampusPoService.REQUEST_CALLBACK, serviceCallback);

		if (BuildConfig.DEBUG)
			Log.d(TAG, "start Service");

		mContext.startService(intent);
		
		
	}
	
	public void doLogin(String email, String password) {
		if (BuildConfig.DEBUG)
			Log.d(TAG, "doLogin() called");

		//check whether the network is available
		if(!isNetworkConnected())
					return;
		
		mPendingRequests.add(ServiceContants.REQUEST_LOGIN);

		Intent intent = new Intent(mContext, CampusPoService.class);

		ResultReceiver serviceCallback = new ResultReceiver(null) {

			@Override
			protected void onReceiveResult(int resultCode, Bundle resultData) {

				if (BuildConfig.DEBUG)
					Log.d(TAG, "onReceiveResult() called");
				handleRequestResponse(resultCode, resultData);
			}
		};

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		params.put("password", password);

		intent.putExtra(ServiceContants.REQUEST_TYPE,
				ServiceContants.REQUEST_LOGIN);
		intent.putExtra(CampusPoService.REQUEST_PARAMS, params);
		intent.putExtra(CampusPoService.REQUEST_CALLBACK, serviceCallback);

		if (BuildConfig.DEBUG)
			Log.d(TAG, "start Service");

		mContext.startService(intent);
	}

	public void handleRequestResponse(int resultCode, Bundle resultData) {

		if (BuildConfig.DEBUG)
			Log.d(TAG, "handleRequestResponse() called");
		
		int requestType = resultData.getInt(ServiceContants.REQUEST_TYPE);
		

		Intent intent = new Intent(ACTION_REQUEST_RESULT);

		intent.putExtra(REQUEST_RESULT_CODE, resultCode);
		intent.putExtra(REQUEST_RESULT_DATA, resultData);
		intent.putExtra(ServiceContants.REQUEST_TYPE,
				requestType);// has problem
		
		//remove the request from the pending Request here
		
		mContext.sendBroadcast(intent);
	}
	
	
	public boolean isNetworkConnected() {
		
		if(!NetworkUtils.isConnectInternet(mContext)){
			Toast.makeText(mContext, "网络连接不可用，请稍后重试", Toast.LENGTH_SHORT).show();//have to modify
			return false;
		}
		
		return true;
	}

}
