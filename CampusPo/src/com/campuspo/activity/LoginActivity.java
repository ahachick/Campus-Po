package com.campuspo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.campuspo.BuildConfig;
import com.campuspo.R;
import com.campuspo.service.CampusPoServiceHelper;
import com.campuspo.service.ServiceContants;

public class LoginActivity extends FragmentActivity {

	public static final String TAG = "LoginActivity";

	private View mLogoView;
	private EditText mEditTextEmail;
	private EditText mEditTextPassword;
	private Button mButtonLogin;
	private Button mButtonRegister;
	private TextView mTextViewForget;
	private LinearLayout mContainerLayout;
	
	private Animation mTranslateUp;
	
	private CampusPoServiceHelper mServiceHelper;
	private BroadcastReceiver mReceiver;


	@Override
	public void onCreate(Bundle outState) {
		super.onCreate(outState);
		setContentView(R.layout.activity_login);
		/*
		 * SharedPreferences preferences = getSharedPreferences("preferences",
		 * Context.MODE_PRIVATE); boolean stayLogin =
		 * preferences.getBoolean("stayLogin", false); if(stayLogin == true)
		 * startMainActivity(); //SharedPreferences.Editor editor =
		 * preferences.edit();
		 */

		prepareData();
	}

	

	private void prepareData() {
		//get the singleton of CampusPoServiceHelper
		mServiceHelper = CampusPoServiceHelper
				.getInstance(getApplicationContext());
		
		mTranslateUp = AnimationUtils.loadAnimation(this, R.anim.translate_up);
		mTranslateUp.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				mContainerLayout.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
		});
		
		mContainerLayout = (LinearLayout) findViewById(R.id.layout);
		
		mLogoView = findViewById(R.id.logo);
		mEditTextEmail = (EditText) findViewById(R.id.et_email);
		mEditTextPassword = (EditText) findViewById(R.id.et_password);
		mButtonLogin = (Button) findViewById(R.id.btn_login);
		mButtonRegister = (Button) findViewById(R.id.btn_register);
		mTextViewForget = (TextView) findViewById(R.id.tv_forget_password);
		
		//bind listeners to the target button
		mButtonLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//mServiceHelper.doLogin("121234783@qq.com", "123");
				if ("".equals(getEmail()))
					Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT)
							.show();
				else if ("".equals(getPassword()))
					Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT)
							.show();
				else {
					 mServiceHelper.doLogin(getEmail(), getPassword());
					
				}

			}
		});

		mButtonRegister.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//==============have to modify
				setStayLogin(true);
				startMainActivity();

			}
		});

		mTextViewForget.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		
	}


	@Override
	public void onResume() {
		super.onResume();
		
		
		IntentFilter filter = new IntentFilter(
				CampusPoServiceHelper.ACTION_REQUEST_RESULT);

		mReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {

				if (BuildConfig.DEBUG)
					Log.d(TAG, "onReceive() called");

				int requestType = intent.getIntExtra(
						ServiceContants.REQUEST_TYPE, 0);

				if (requestType == ServiceContants.REQUEST_LOGIN) {
					if (BuildConfig.DEBUG)
						Log.d(TAG, "processing...");

					int resultCode = intent.getIntExtra(
							CampusPoServiceHelper.REQUEST_RESULT_CODE, 0);

					if (resultCode == 1) {
						startMainActivity();

					} else if (resultCode == -1) {
						Toast.makeText(LoginActivity.this, "无法连接服务器：连接超时",
								Toast.LENGTH_LONG).show();
					}

				}

			}

		};

		registerReceiver(mReceiver, filter);
		
		mLogoView.setAnimation(mTranslateUp);
		mTranslateUp.start();

	}

	@Override
	public void onPause() {
		super.onPause();

		if (mReceiver != null)
			unregisterReceiver(mReceiver);
	}
	
	/**
	 * jump to the MainActivity if login success or has already login
	 */
	private void startMainActivity() {
		Intent intent = new Intent(LoginActivity.this, MainActivity.class);
		this.startActivity(intent);
		this.finish();
	}
	
	/**
	 * set "stayLogin" in SharedPreference if user logined success
	 * @param stayLogin 
	 */
	public void setStayLogin(boolean stayLogin) {
		SharedPreferences sharedPref = this.getSharedPreferences("preferences",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putBoolean("stayLogin", stayLogin);
		editor.commit();
	}

	private String getEmail() {
		return mEditTextEmail.getText().toString().trim();
	}

	private String getPassword() {
		return mEditTextPassword.getText().toString().trim();
	}

	public RetainFragment findOrCreateRetainFragment(FragmentManager fm) {
		RetainFragment retainFragment = (RetainFragment) fm
				.findFragmentByTag(TAG);

		if (retainFragment == null) {
			retainFragment = new RetainFragment();
			fm.beginTransaction().add(retainFragment, TAG).commit();
		}

		return retainFragment;
	}

	/**
	 * A simple non-UI Fragment that stores a single Object and is retained over
	 * configuration changes. It will be used to retain the ImageCache object.
	 */
	public static class RetainFragment extends Fragment {
		private Object mObject;

		/**
		 * Empty constructor as per the Fragment documentation
		 */
		public RetainFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			// Make sure this Fragment is retained over a configuration change
			setRetainInstance(true);
		}

		/**
		 * Store a single object in this Fragment.
		 * 
		 * @param object
		 *            The object to store
		 */
		public void setObject(Object object) {
			mObject = object;
		}

		/**
		 * Get the stored object.
		 * 
		 * @return The stored object
		 */
		public Object getObject() {
			return mObject;
		}
	}
}
