package com.campuspo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.campuspo.R;

public class PosterRelatedFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater,
						ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_timeline, container, false);
		return v;
	}
						
}