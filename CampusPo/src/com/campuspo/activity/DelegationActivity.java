package com.campuspo.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;

import com.campuspo.R;

public class DelegationActivity extends ActionBarActivity{

	private static final String TAG = DelegationActivity.class.getSimpleName();
	
	private GridView mGridView;
	private int mColumnWidth;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		mGridView = new GridView(this);
		mGridView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		int mColumnWidth = this.getResources().getDimensionPixelOffset(R.dimen.item_delegation_width);
		
		
	}

	public int caculateNumColumn(int columnWidth) {
		
		int width = mGridView.getWidth();
		return 0;
	}
}
