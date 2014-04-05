package com.campuspo.fragment;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment
							implements DatePickerDialog.OnDateSetListener {

	private onTimeSetInterface mOnTimeSetInterface;
	
	public static interface onTimeSetInterface {
		void setTime(int year, int month, int day);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if(null == mOnTimeSetInterface) {
			mOnTimeSetInterface = (onTimeSetInterface) getActivity();
		}
	}

	@Override
	public void onDetach() {
	
		if(mOnTimeSetInterface != null) {
			mOnTimeSetInterface = null;
		}
		super.onDetach();

	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		mOnTimeSetInterface.setTime(year, monthOfYear, dayOfMonth);
	}

}
