package com.example.lavdrimshala_asignment1;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class CurrentLocationGmap extends FragmentActivity
implements OnMyLocationButtonClickListener,
		   ConnectionCallbacks,
		   OnConnectionFailedListener, 
		   com.google.android.gms.location.LocationListener{

	public GoogleMap mMap;
	public LocationClient mLocationClient;
	
	private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(500)         
            .setFastestInterval(16)   
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		setUpMapIfNeeded();
		setUpLocationClientIfNeeded();
		
	}
	@Override
	   
	protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        setUpLocationClientIfNeeded();
        mLocationClient.connect();
    }
	 @Override
	public void onPause() {
	        super.onPause();
	        if (mLocationClient != null) {
	            mLocationClient.disconnect();
	        }
	    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.location, menu);
		return true;
	}
	
		public void setUpMapIfNeeded() {
	        // Do a null check to confirm that we have not already instantiated the map.
	        if (mMap == null) {
	            // Try to obtain the map from the SupportMapFragment.
	            mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
	            // Check if we were successful in obtaining the map.
	            if (mMap != null) {
	                mMap.setMyLocationEnabled(true);
	                mMap.setOnMyLocationButtonClickListener(this);
	            }
	        }
	    }
	    public boolean onMyLocationButtonClick() {
	        return false;
	    }
	    @Override
	    public void onLocationChanged(Location loc) {
	    	LatLng latLng = new LatLng(loc.getLatitude(),loc.getLongitude());
	    	CameraUpdate camU = CameraUpdateFactory.newLatLngZoom(latLng,15);
	    	mMap.animateCamera(camU);
	    }

	
		@Override
		public void onConnectionFailed(ConnectionResult result) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onDisconnected() {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void onConnected(Bundle connectionHint) {
			mLocationClient.requestLocationUpdates(REQUEST,this);  
		}
	    public void setUpLocationClientIfNeeded() {
	        if (mLocationClient == null) { mLocationClient = new LocationClient(getApplicationContext(),
	                    this,  // ConnectionCallbacks
	                    this); // OnConnectionFailedListener
	        }
	    }
	    

}
