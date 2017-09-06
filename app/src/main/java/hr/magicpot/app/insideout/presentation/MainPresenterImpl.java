package hr.magicpot.app.insideout.presentation;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import hr.magicpot.app.insideout.storage.db.interactor.LocationInteractor;
import hr.magicpot.app.insideout.storage.db.interactor.impl.LocationInteractorImpl;
import hr.magicpot.app.insideout.storage.db.model.Location;
import hr.magicpot.app.insideout.userinterface.MainActivity;

/**
 * Created by Antonio on 5.9.2017..
 */

public class MainPresenterImpl implements MainPresenter, LocationInteractor.onDatabaseListener, GoogleMap.OnMapLongClickListener, OnMapReadyCallback {

    private final MainActivity mainActivity;
    private final LocationInteractor locationInteractor;

    private GoogleMap mMap;

    private Marker markerLocation;
    private Circle circleLocation;

    public MainPresenterImpl(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.locationInteractor = new LocationInteractorImpl(this);
    }

    public void setMapFragment(int mapId) {
        SupportMapFragment mapFragment = (SupportMapFragment) mainActivity.getSupportFragmentManager().findFragmentById(mapId);
        mapFragment.getMapAsync(this);
    }

    //= FETCH
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        this.mMap.setOnMapLongClickListener(this);

        mainActivity.hideResetButton();
        locationInteractor.fetchLocation();
    }

    @Override
    public void onLocationFetchSuccess(Location location) {
        mainActivity.showResetButton();
        setMarkerData(location);
    }

    //= STORE
    @Override
    public void onMapLongClick(LatLng latLng) {
        locationInteractor.store(latLng);
    }

    @Override
    public void onLocationStoreSuccess(Location location) {
        mainActivity.showResetButton();
        setMarkerData(location);
    }

    //= RESET
    @Override
    public void processResetButton() {
        locationInteractor.deleteSetLocation();
    }

    @Override
    public void onLocationDeleteSuccess() {
        mainActivity.hideResetButton();

        markerLocation.remove();
        circleLocation.remove();
    }

    //MESSAGE
    @Override
    public void onMessage(String message) {
        mainActivity.showToastMessage(message);
    }

    private void setMarkerData(Location location) {
        LatLng latLng = new LatLng(location.getLat(), location.getLng());

        markerLocation = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(location.getLat(), location.getLng()))
                .title("Location"));

        circleLocation = mMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(50)
                .strokeColor(Color.DKGRAY));
    }
}
