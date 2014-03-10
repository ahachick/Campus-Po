package com.campuspo.widget;

import com.campuspo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TabIndicatorView extends LinearLayout{
	
	private ImageView mTabIcon;
	private TextView mTabLabel;
	private ImageView mIndicator;

	public TabIndicatorView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		LayoutInflater inflater =  LayoutInflater.from(context);
		inflater.inflate(R.layout.view_tab_indicator, this);
		mTabIcon = (ImageView) findViewById(R.id.iv_tab_icon);
		//mTabLabel = (TextView) findViewById(R.id.tv_tab_label);
	}
	
	public TabIndicatorView setIcon(int resId) {
		mTabIcon.setImageResource(R.drawable.actionbar_tab_indicator);
		return this;
	}
	
	public TabIndicatorView setLabel(CharSequence label) {
		mTabLabel.setText(label);
		return this;
	}

}
