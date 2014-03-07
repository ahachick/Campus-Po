package com.campuspo.app;

import android.app.Application;
import android.content.Context;

import com.campuspo.domain.User;

public class CampusPoApplication extends Application{
private static Context mAppContext;
private static User mUser;
	
	@Override
	public void onCreate() {
		super.onCreate();
		// 放在最前面
		mAppContext = getApplicationContext();
		
		
	}
	
	public static Context getAppContext() {
		return mAppContext;
	}
	
	public static void setUser(User user) {
		mUser = user;
	}
	
	public static User getUser(){
		return mUser;
	}
}
