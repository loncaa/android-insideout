package hr.magicpot.app.insideout.location;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.Date;

import hr.magicpot.app.insideout.storage.db.interactor.UserLogInteractor;
import hr.magicpot.app.insideout.storage.db.interactor.impl.UserLogInteractorImpl;
import hr.magicpot.app.insideout.storage.db.model.UserLog;

/**
 * Created by Antonio on 10.9.2017..
 */

public class MyLocationManager extends Service {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 3; // 3 meters
    private static final long MIN_TIME_BW_UPDATES = 1000 * 0 * 1; // 1 minute
    private static final String FLAG = "flag";

    private LocationManager locationManager;
    private UserLogInteractor userLogInteractor;

    private SharedPreferences sharedPref;

    private double lat;
    private double lng;
    private double radisu;

    private Context mContext;

    final LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            onMyLocationChanged(location);
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
    };

    @Override
    public void onCreate() {
        this.mContext = this;
        userLogInteractor = new UserLogInteractorImpl();

        sharedPref = mContext.getSharedPreferences("insideout", Context.MODE_PRIVATE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(this.locationManager == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.locationManager = this.getSystemService(LocationManager.class);
            } else {
                this.locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            }
        }

        if(intent != null)
            startLocationService(intent);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        userLogInteractor.setEndTime(new Date());

        putInSharedPreferences(sharedPref, false, MyLocationManager.FLAG);

        stopLocationService();
    }

    private void startLocationService(Intent intent) {
        lat = intent.getDoubleExtra("lat", 0);
        lng = intent.getDoubleExtra("lng", 0);
        radisu = intent.getDoubleExtra("radisu", 0);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {return;}

        Location location = this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        onMyLocationChanged(location);

        this.locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MyLocationManager.MIN_TIME_BW_UPDATES,
                MyLocationManager.MIN_DISTANCE_CHANGE_FOR_UPDATES,
                locationListener
        );
    }

    private void stopLocationService() {
        this.locationManager.removeUpdates(locationListener);
    }

    private void putInSharedPreferences(SharedPreferences sharedPreferences, boolean value, String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void onMyLocationChanged(Location location) {
        boolean isInside = isInsideCircle(location);
        boolean isLogged = sharedPref.getBoolean(MyLocationManager.FLAG, false);

        if(isInside && !isLogged) {
            UserLog userLog = new UserLog();
            userLog.setStart(new Date());

            UserLog res = userLogInteractor.store(userLog);
            if(res != null)
                putInSharedPreferences(sharedPref, true, MyLocationManager.FLAG);
        }
        else if(!isInside && isLogged){
            UserLog res = userLogInteractor.setEndTime(new Date());
            if(res != null)
                putInSharedPreferences(sharedPref, false, MyLocationManager.FLAG);
        }
    }

    private boolean isInsideCircle(android.location.Location location) {
        float[] distance = new float[2];
        android.location.Location.distanceBetween(location.getLatitude(), location.getLongitude(), lat, lng, distance);
        return distance[0] < radisu;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
