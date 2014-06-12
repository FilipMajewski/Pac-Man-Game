package com.filipmajewski.map.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Main extends Activity implements LocationListener,View.OnLongClickListener {

    private GoogleMap googleMap;
    private LocationManager locationManager;
    public double latitude;
    public double longitude;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_map_fragment);

        getActionBar().hide();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, this);

        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }
            //googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            //googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            //googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = getLayoutInflater().inflate(R.layout.custom_marker_info, null);
                return v;

            }
        });

        Circle circle = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(51.8972, -8.4700))
                .radius(800)
                .strokeColor(Color.RED)
                .fillColor(Color.TRANSPARENT));

        circle.setVisible(true);
        //googleMap.setMyLocationEnabled(true);


    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LatLng POINT = new LatLng(latitude, longitude);

        marker = googleMap.addMarker(new MarkerOptions().position(POINT)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pacman)));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(POINT));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}