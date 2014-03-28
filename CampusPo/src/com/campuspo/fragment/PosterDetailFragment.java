package com.campuspo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.campuspo.R;
import com.campuspo.activity.PosterActivity;
import com.campuspo.domain.Poster;

public class PosterDetailFragment extends Fragment{
	
	@SuppressWarnings("unused")
	private static final String TAG = PosterDetailFragment.class.getSimpleName();
	
	private Poster mPoster;
	
	private TextView m_tvReleasedTime;
	private TextView m_tvPosterDescription;
	private TextView m_tvPosterCatagory;
	private TextView m_tvPosterTag;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		prepareData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
						ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View v = inflater.inflate(R.layout.fragment_poster_detail, container, false);
		m_tvReleasedTime = (TextView) v.findViewById(R.id.tv_released_time);
		m_tvPosterDescription = (TextView) v.findViewById(R.id.tv_poster_description);
		m_tvPosterCatagory = (TextView) v.findViewById(R.id.tv_poster_catagory);
		m_tvPosterTag = (TextView) v.findViewById(R.id.tv_poster_tag);
		
		populateUi();
		return v;
	}
	
	public void prepareData() {
		
		Bundle data = this.getArguments();
		mPoster = (Poster) data.getSerializable(PosterActivity.KEY_DATA);
	}
	
	public void populateUi() {
		m_tvPosterDescription.setText(mPoster.getPosterDescription());
	}
						
}
