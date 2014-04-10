package com.campuspo.gcm;

import com.google.android.gcm.GCMBaseIntentService;

import android.content.Context;
import android.content.Intent;

public class GcmIntentService extends GCMBaseIntentService{

	private static final String TAG = GcmIntentService.class.getSimpleName();
	
	public GcmIntentService() {
		super("GcmIntentService");
	}


	@Override
	protected void onError(Context ctx, String arg1) {
		
	}

	@Override
	protected void onMessage(Context arg0, Intent arg1) {
		
	}

	@Override
	protected void onRegistered(Context arg0, String arg1) {
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		
	}

}
