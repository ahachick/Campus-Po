package com.campuspo.fragment;

import java.lang.reflect.Field;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.campuspo.R;
import com.campuspo.util.Utils;
import com.campuspo.widget.SegmentedRadioGroup;

public class HomePageFragment extends Fragment implements OnCheckedChangeListener{
	
	public static final String TAG = HomePageFragment.class.getSimpleName();
	
	FragmentManager mFragmentManager;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		this.setHasOptionsMenu(true);
		View v = inflater.inflate(R.layout.fragment_home_page, container, false);
		
		SegmentedRadioGroup group = (SegmentedRadioGroup) v.findViewById(R.id.segment_text);
	    group.setOnCheckedChangeListener(this);
	    
	   mFragmentManager = getChildFragmentManager();
	    
	    if(savedInstanceState != null) 
	    	return v;
	    
	    group.check(R.id.btn_lastest);

		return v;
	}
	
	//修补support包中frament中的bug
	@Override
	public void onDetach() {
	    super.onDetach();

	    try {
	        Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
	        childFragmentManager.setAccessible(true);
	        childFragmentManager.set(this, null);

	    } catch (NoSuchFieldException e) {
	        throw new RuntimeException(e);
	    } catch (IllegalAccessException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	@Override
    public void onDestroyView() {
        super.onDestroyView();
    }
	

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		
		super.onCreateOptionsMenu(menu, inflater);
		
		inflater.inflate(R.menu.menu_timeline_fragment, menu);
		
		//if build verson greater than 11, than set the item to actionbar
		if(Utils.hasHoneycomb()) {
			MenuItem item = menu.getItem(0);
			item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}	
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		Fragment newFragment = null;
		
		switch(checkedId) {
		case R.id.btn_lastest :
			newFragment = getChildFragmentManager().findFragmentByTag(PublicTimelineFragment.TAG);
			if(newFragment == null) {
				Log.d(TAG, "child fragment is null");
				newFragment = new PublicTimelineFragment();
			}
			ft.replace(R.id.content, newFragment, PublicTimelineFragment.TAG);
			ft.commit();
			break;
		case R.id.btn_focus :
			newFragment = getChildFragmentManager().findFragmentByTag(DelegationFragment.TAG);
			if(newFragment == null) {
				Log.d(TAG, "child fragment is null");
				newFragment = new DelegationFragment();

			}
			ft.replace(R.id.content, newFragment, DelegationFragment.TAG);
			ft.commit();
			break;
		}		
	}
	
}

