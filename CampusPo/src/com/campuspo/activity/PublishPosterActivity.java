package com.campuspo.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.campuspo.R;
import com.campuspo.service.CampusPoServiceHelper;

public class PublishPosterActivity extends ActionBarActivity {
	
	private static final String TAG = PublishPosterActivity.class
			.getSimpleName();

	private EditText m_etPosterTitle;
	private EditText m_etPosterDescription;
	private EditText m_etWantedNum;
	private CheckBox m_cbWanted;
	private CheckBox m_cbNoLimit;
	private LinearLayout m_LinearLayout;
	private Button m_btnPublish;
	private Button m_btnCancel;

	private boolean isWanted;
	private boolean isLimit;
	
	public CampusPoServiceHelper mServiceHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_publish_poster);
		prepareData();
	}

	public void prepareData() {
		
		mServiceHelper = CampusPoServiceHelper.getInstance(getApplicationContext());
		m_etPosterTitle = (EditText) findViewById(R.id.et_poster_title);
		m_etPosterDescription = (EditText) findViewById(R.id.et_poster_description);
		m_etWantedNum = (EditText) findViewById(R.id.et_wanted_num);
		m_cbWanted = (CheckBox) findViewById(R.id.cb_wanted);
		m_cbNoLimit = (CheckBox) findViewById(R.id.cb_no_limit);
		m_LinearLayout = (LinearLayout) findViewById(R.id.ly_wanted);
		m_btnPublish = (Button) findViewById(R.id.btn_publish);
		m_btnCancel = (Button) findViewById(R.id.btn_cancel);
		
		isWanted = false;
		isLimit = true;

		m_cbWanted.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				isWanted = isChecked;

				m_LinearLayout.setVisibility((isWanted ? View.VISIBLE
						: View.GONE));
			}

		});

		m_cbNoLimit.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				isLimit = !isChecked;
				m_etWantedNum.setEnabled(isLimit);
			}

		});

		m_btnPublish.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// publish poster to the server
				publish();
				// destory the activity
				PublishPosterActivity.this.finish();
			}
		});

		m_btnCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// cancel this request and ask whether to save as draft
				cancel();
				// destory the activity

			}
		});

	}

	public void publish() {

		String posterTitle = m_etPosterTitle.getText().toString().trim();
		String posterDescription = m_etPosterDescription.getText().toString()
				.trim();
		int wantedNum = -1;
		// judge the content is empty, if true, return
		if (posterTitle.equals("") || posterDescription.equals("")) {
			Toast.makeText(this, "活动信息不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		// judge whether this poster wants participants
		// judge whether this poster set limitation to wanted num
		if (isWanted && isLimit) {
			String numString;
			if ((numString = m_etWantedNum.getText().toString().trim())
					.equals("")) {
				Toast.makeText(this, "招募人数不能为空", Toast.LENGTH_SHORT).show();
				return;
			}
			wantedNum = Integer.parseInt(numString);
			if (wantedNum == 0) {
				Toast.makeText(this, "招募人数不能为0", Toast.LENGTH_SHORT).show();
				return;
			}

		}

		mServiceHelper.publishPoster(posterTitle, posterDescription, isWanted, wantedNum);
		// PublishPosterActivity.this.finish();
	}

	// cancel and save
	public void cancel() {

	}

}
