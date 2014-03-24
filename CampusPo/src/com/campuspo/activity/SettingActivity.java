package com.campuspo.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.campuspo.R;
import com.campuspo.util.Utils;

public class SettingActivity extends PreferenceActivity{

	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(Utils.hasHoneycomb()) {
			//setContentView(R.layout.activity_setting);
			if(null == savedInstanceState) {
				buildPreferenceFragment();
			}
		}else {
			addPreferencesFromResource(R.xml.preferences);
		}		
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
	}
	
	@SuppressLint("NewApi")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void buildPreferenceFragment() {
		getFragmentManager().beginTransaction()
			.replace(android.R.id.content, new SettingFragment()).commit();	
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class SettingFragment extends PreferenceFragment {
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			addPreferencesFromResource(R.xml.preferences);
		}
	}
}
