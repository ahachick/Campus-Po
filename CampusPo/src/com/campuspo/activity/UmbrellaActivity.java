package com.campuspo.activity;

import java.util.LinkedList;
import java.util.WeakHashMap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.campuspo.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class UmbrellaActivity extends ActionBarActivity {

	@SuppressWarnings("unused")
	private static final String TAG = UmbrellaActivity.class.getSimpleName();

	private GoogleMap mMap;
	private SupportMapFragment mMapFragment;

	private WeakHashMap<Marker, HelpInfo> mMarkers = new WeakHashMap<Marker, HelpInfo>();
	private LinkedList<HelpInfo> mInfoList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_umbrella);

		mMapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);

		mMap = mMapFragment.getMap();

		// mMap.setMyLocationEnabled(true);

		mMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker m) {
				return false;
			}
		});
		mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker marker) {

			}
		});

		mMap.setInfoWindowAdapter(new InfoWindowAdapter() {

			@Override
			public View getInfoContents(Marker marker) {
				return UmbrellaActivity.this.getLayoutInflater().inflate(
						R.layout.info_window, null);
			}

			@Override
			public View getInfoWindow(Marker marker) {
				return null;
			}
		});
		prepareData();
		refreshMarkerData();
	}

	// Test Data
	public void prepareData() {
		mInfoList = new LinkedList<HelpInfo>();
		mInfoList
				.add(new HelpInfo(0, 51.5072, -0.5275, HelpInfo.FLAG_FOR_HELP));
		mInfoList
				.add(new HelpInfo(0, 52.5072, -0.9275, HelpInfo.FLAG_FOR_HELP));
		mInfoList.add(new HelpInfo(0, 56.5072, -0.8275, HelpInfo.FLAG_TO_HELP));
		mInfoList.add(new HelpInfo(0, 59.5072, -0.5275, HelpInfo.FLAG_TO_HELP));
		mInfoList.add(new HelpInfo(0, 54.5072, -0.2275, HelpInfo.FLAG_TO_HELP));
	}

	public void refreshMarkerData() {
		if (null != mInfoList) {
			// ClearAllTheData;
			mMap.clear();
			mMarkers.clear();
			for (HelpInfo info : mInfoList) {
				LatLng ll = new LatLng(info.mLatitude, info.mLongtitude);
				int bmpId = (info.mFlag == HelpInfo.FLAG_FOR_HELP) ? R.drawable.map_pin_holed_purple_normal
						: R.drawable.map_pin_holed_blue_normal;
				Marker marker = mMap.addMarker(new MarkerOptions().position(ll)
						.title("Dummy").snippet("Dummy")
						.icon(BitmapDescriptorFactory.fromResource(bmpId)));
				mMarkers.put(marker, info);
			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuItem item = menu.add(0, R.id.menu_toggle, 0, "Toggle");

		MenuItemCompat.setShowAsAction(item,
				MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onStart() {
		super.onStart();
		// mMapView.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		// mMapView.onStop();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_toggle:
			handleToggle();
			break;
		default:

		}

		return super.onOptionsItemSelected(item);
	}

	public void handleToggle() {
		new HelpInfoFragment().show(getSupportFragmentManager(), HelpInfoFragment.class.getSimpleName());
	}
	
	private static class HelpInfo {

		public static final int FLAG_FOR_HELP = 0;
		public static final int FLAG_TO_HELP = 1;
		public long mUserId;
		public int mFlag;
		public double mLatitude;
		public double mLongtitude;

		public HelpInfo(int id, double lat, double lng, int flag) {
			mUserId = id;
			mLatitude = lat;
			mLongtitude = lng;
			mFlag = flag;
		}
	}
	
	public static class HelpInfoFragment extends DialogFragment {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			
			LayoutInflater inflater = getActivity().getLayoutInflater();;
			
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setPositiveButton(R.string.confirm, new Dialog.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
				
			}).setNegativeButton(R.string.cancel, new Dialog.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
				
			}).setView(inflater.inflate(R.layout.dialog_helpinfo, null));
			return builder.create();
		}
		
		
		
	}

	/*
	 * @Override public void onRegionChanged(PolarisMapView mapView) { if
	 * (Config.INFO_LOGS_ENABLED) { // Log.i(LOG_TAG, "onRegionChanged"); } }
	 * 
	 * @Override public void onRegionChangeConfirmed(PolarisMapView mapView) {
	 * if (Config.INFO_LOGS_ENABLED) { // Log.i(LOG_TAG,
	 * "onRegionChangeConfirmed"); } }
	 * 
	 * @Override public void onAnnotationSelected(PolarisMapView mapView,
	 * MapCalloutView calloutView, int position, Annotation annotation) { if
	 * (Config.INFO_LOGS_ENABLED) { // Log.i(LOG_TAG, "onAnnotationSelected"); }
	 * calloutView.setDisclosureEnabled(true); calloutView.setClickable(true);
	 * if (!TextUtils.isEmpty(annotation.getSnippet())) {
	 * calloutView.setLeftAccessoryView(getLayoutInflater().inflate(
	 * R.layout.accessory, calloutView, false)); } else {
	 * calloutView.setLeftAccessoryView(null); } }
	 * 
	 * @Override public void onAnnotationDeselected(PolarisMapView mapView,
	 * MapCalloutView calloutView, int position, Annotation annotation) { if
	 * (Config.INFO_LOGS_ENABLED) { // Log.i(LOG_TAG, "onAnnotationDeselected");
	 * } }
	 * 
	 * @Override public void onAnnotationClicked(PolarisMapView mapView,
	 * MapCalloutView calloutView, int position, Annotation annotation) { if
	 * (Config.INFO_LOGS_ENABLED) { // Log.i(LOG_TAG, "onAnnotationClicked"); }
	 * // oast.makeText(this, getString(R.string.annotation_clicked, //
	 * annotation.getTitle()), Toast.LENGTH_SHORT).show(); }
	 */

	/*
	 * //BMapManager mBMapManager = null; PolarisMapView mMapView = null;
	 * FrameLayout mMapContainer;
	 * 
	 * @Override protected void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState);
	 * 
	 * //mBMapManager = new BMapManager(getApplication());
	 * //mBMapManager.init(null);
	 * 
	 * setContentView(R.layout.activity_umbrella); //mMapView = (MapView)
	 * findViewById(R.id.map_view);
	 * 
	 * mMapView = new PolarisMapView(this, Config.GOOGLE_MAPS_API_KEY);
	 * //mMapView.setUserTrackingButtonEnabled(true);
	 * //mMapView.setOnRegionChangedListenerListener(this);
	 * //mMapView.setOnAnnotationSelectionChangedListener(this);
	 * 
	 * 
	 * //mMapView.getOverlays().add(null);
	 * 
	 * MapController mc = mMapView.getController(); GeoPoint[] points = { new
	 * GeoPoint((int) (39.915 * 1E6), (int) (116.404 * 1E6)), new GeoPoint((int)
	 * (39.925 * 1E6), (int) (116.444 * 1E6)), new GeoPoint((int) (39.935 *
	 * 1E6), (int) (116.484 * 1E6)), new GeoPoint((int) (39.945 * 1E6), (int)
	 * (116.434 * 1E6)) };
	 * 
	 * final Drawable altMarker = MapViewUtils
	 * .boundMarkerCenterBottom(getResources().getDrawable(
	 * R.drawable.map_pin_holed_blue_normal));
	 * 
	 * //AnnotationsOverlay ao = new AnnotationsOverlay(altMarker, mMapView);
	 * ArrayList<Annotation> annotations = new ArrayList<Annotation>(); for
	 * (GeoPoint point : points) { Annotation annotation = new Annotation(point,
	 * "Title", "SubTitle"); annotation.setMarker(altMarker);
	 * annotations.add(annotation); }
	 * 
	 * annotations.add(new Annotation(new GeoPoint((int) (39.985 * 1E6), (int)
	 * (116.414 * 1E6)),"Title", "Snippt"));
	 * 
	 * mMapView.setAnnotations(annotations,
	 * R.drawable.map_pin_holed_purple_normal); //mc.setCenter(points[0]);
	 * //mc.setZoom(12);
	 * 
	 * mMapContainer = (FrameLayout) findViewById(R.id.map_container);
	 * mMapContainer.addView(mMapView); }
	 * 
	 * @Override protected void onDestroy() { mMapView.destroy(); if
	 * (mBMapManager != null) { mBMapManager.destroy(); mBMapManager = null; }
	 * super.onDestroy(); }
	 * 
	 * @Override protected void onPause() { mMapView.onPause(); if (mBMapManager
	 * != null) { mBMapManager.stop(); } super.onPause(); }
	 * 
	 * @Override protected void onResume() { mMapView.onResume(); if
	 * (mBMapManager != null) { mBMapManager.start(); //Log.d(TAG, "" +
	 * mMapView.getLatitudeSpan()); } super.onResume(); }
	 * 
	 * @Override public void onAnnotationSelected(PolarisMapView mapView,
	 * MapCalloutView calloutView, int position, Annotation annotation) {
	 * calloutView.setDisclosureEnabled(true); calloutView.setClickable(true);
	 * if (!TextUtils.isEmpty(annotation.getSnippet())) {
	 * calloutView.setLeftAccessoryView
	 * (getLayoutInflater().inflate(R.layout.accessory, calloutView, false)); }
	 * else { calloutView.setLeftAccessoryView(null); } }
	 * 
	 * @Override public void onAnnotationDeselected(PolarisMapView mapView,
	 * MapCalloutView calloutView, int position, Annotation annotation) {
	 * 
	 * }
	 * 
	 * @Override public void onAnnotationClicked(PolarisMapView mapView,
	 * MapCalloutView calloutView, int position, Annotation annotation) {
	 * 
	 * }
	 * 
	 * @Override public void onRegionChanged(PolarisMapView mapView) {
	 * 
	 * }
	 * 
	 * @Override public void onRegionChangeConfirmed(PolarisMapView mapView) {
	 * 
	 * }
	 */
}
