package com.campuspo.activity;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.campuspo.R;
import com.campuspo.fragment.DatePickerFragment;

public class PublishDelegationActivity extends ActionBarActivity implements
		DatePickerFragment.onTimeSetInterface {

	@SuppressWarnings("unused")
	private static final String TAG = PublishDelegationActivity.class
			.getSimpleName();

	private EditText mTextTitle;
	private EditText mTextDescription;
	private EditText mTextReward;
	private TextView mTextDeadline;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_publish_delegation);
		populateUi();
		prepareData();
	}

	public void populateUi() {

		mTextTitle = (EditText) findViewById(R.id.et_delegation_title);
		mTextDescription = (EditText) findViewById(R.id.et_delegation_description);
		mTextReward = (EditText) findViewById(R.id.et_reward);
		mTextDeadline = (TextView) findViewById(R.id.tv_deadline);

		// bind listeners
		mTextDeadline.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDatePickerDialog();
			}

		});
	}
	
	public void prepareData() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		//January is corresponded to '0', so month = month + 1;
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		String deadline = String.format(
				getResources().getString(R.string.deadline), year, month, day);
		mTextDeadline.setText(deadline);
		
	}

	public void showDatePickerDialog() {
		DialogFragment newDialogFragment = new DatePickerFragment();
		newDialogFragment.show(getSupportFragmentManager(), "datePicker");
	}

	@Override
	public void setTime(int year, int month, int day) {

		//January is corresponded to '0', so month = month + 1;
		String deadline = String.format(
				getResources().getString(R.string.deadline), year, month + 1, day);
		mTextDeadline.setText(deadline);
	}
}
