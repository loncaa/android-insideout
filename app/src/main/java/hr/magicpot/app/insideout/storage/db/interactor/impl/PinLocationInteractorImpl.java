package hr.magicpot.app.insideout.storage.db.interactor.impl;

import com.google.android.gms.maps.model.LatLng;

import hr.magicpot.app.insideout.storage.db.interactor.PinLocationInteractor;
import hr.magicpot.app.insideout.storage.db.manager.LocationManager;
import hr.magicpot.app.insideout.storage.db.manager.impl.LocationManagerImpl;
import hr.magicpot.app.insideout.storage.db.model.Location;

public class PinLocationInteractorImpl implements PinLocationInteractor, LocationManager.onDatabaseConnection {
    private final LocationManager locationManager;
    private final onDatabaseListener checkListener;

    public PinLocationInteractorImpl(onDatabaseListener checkListener) {
        this.locationManager = new LocationManagerImpl();
        this.checkListener = checkListener;
    }

    @Override
    public void fetchLocation() {
        locationManager.fetchLocation(this);
    }

    @Override
    public void onFetchSuccess(Location location) {
        checkListener.onLocationFetchSuccess(location); }


    @Override
    public void store(LatLng model) {
        locationManager.store(model, this);
    }

    @Override
    public void onStoreSuccess(Location location) {
        checkListener.onLocationStoreSuccess(location);
    }

    @Override
    public void deleteSetLocation() {
        locationManager.deleteLocations(this);
    }


    @Override
    public void onMessage(String msg) {
        checkListener.onMessage(msg);
    }

    @Override
    public void onDeleteSuccess() {
        checkListener.onLocationDeleteSuccess(); }
}
