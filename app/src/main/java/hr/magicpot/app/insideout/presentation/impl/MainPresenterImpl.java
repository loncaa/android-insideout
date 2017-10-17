package hr.magicpot.app.insideout.presentation.impl;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import hr.magicpot.app.insideout.location.MyLocationManager;
import hr.magicpot.app.insideout.storage.db.interactor.PinLocationInteractor;
import hr.magicpot.app.insideout.storage.db.interactor.impl.PinLocationInteractorImpl;
import hr.magicpot.app.insideout.storage.db.model.Location;
import hr.magicpot.app.insideout.userinterface.impl.MainActivity;

public class MainPresenterImpl implements PinLocationInteractor.onDatabaseListener, GoogleMap.OnMapLongClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private final MainActivity mainActivity;
    private final PinLocationInteractor pinLocationInteractor;

    private GoogleMap mMap;

    private Marker markerLocation;
    private Circle circleLocation;
    private double circleRadius = 50;

    private final LocationManager locationManager;

    private Intent locatonIntent;

    public MainPresenterImpl(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.pinLocationInteractor = new PinLocationInteractorImpl(this);

        locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
    }

    public void setMapFragment(int mapId) {
        SupportMapFragment mapFragment = (SupportMapFragment) mainActivity.getSupportFragmentManager().findFragmentById(mapId);
        mapFragment.getMapAsync(this);
    }
    
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;

        this.mMap.setOnMapLongClickListener(this);
        this.mMap.setOnMarkerClickListener(this);

        int p1 = ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION);
        int p2 = ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (p1 != PackageManager.PERMISSION_GRANTED && p2 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        else {
            this.mMap.setMyLocationEnabled(true);
            pinLocationInteractor.fetchLocation();
        }
    }

    @Override
    public void onLocationFetchSuccess(Location location) {
        startTracking(location);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(mainActivity.getBaseContext());
        circleRadius = Double.parseDouble(SP.getString("radius", "50"));

        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(isGPSEnabled)
            pinLocationInteractor.store(latLng);
        else
            mainActivity.showSettingsAlert();
    }

    @Override
    public void onLocationStoreSuccess(Location location) {
        startTracking(location);
    }

    public void processResetButton() {
        pinLocationInteractor.deleteSetLocation();
    }

    @Override
    public void onLocationDeleteSuccess() {
        stopTracking();
    }

    @Override
    public void onMessage(String message) {
        mainActivity.showToastMessage(message);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 18f));
        return true;
    }

    private void startTracking(Location location) {
        Log.d("LOC", "lat: " + location.getLat() + " lng: " + location.getLng());

        mainActivity.showResetButton();
        setMarkerData(location);

        this.locatonIntent = new Intent(mainActivity, MyLocationManager.class);
        this.locatonIntent.putExtra("lat", location.getLat());
        this.locatonIntent.putExtra("lng", location.getLng());
        this.locatonIntent.putExtra("radisu", circleRadius);

        mainActivity.startService(locatonIntent);
    }

    private void setMarkerData(Location location) {
        LatLng latLng = new LatLng(location.getLat(), location.getLng());

        markerLocation = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(location.getLat(), location.getLng()))
                .title("Location"));

        circleLocation = mMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(circleRadius)
                .strokeColor(Color.parseColor("#FF0099"))
                .strokeWidth(2));
    }

    public void stopTracking() {
        if(markerLocation != null && circleLocation != null) {
            mainActivity.hideResetButton();

            markerLocation.remove();
            circleLocation.remove();

            mainActivity.stopService(locatonIntent);
        }
    }

    public void showSettingsDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mainActivity);

        alertDialog.setTitle("GPS is settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mainActivity.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }
}
