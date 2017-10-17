package hr.magicpot.app.insideout.storage.db.interactor;

import com.google.android.gms.maps.model.LatLng;

import hr.magicpot.app.insideout.storage.db.model.Location;

public interface PinLocationInteractor {
    interface onDatabaseListener extends Interactor.onDatabaseListener {
        void onLocationStoreSuccess(Location location);
        void onLocationDeleteSuccess();
        void onLocationFetchSuccess(Location location);
    }

    void fetchLocation();

    void store(LatLng model);
    
    void deleteSetLocation();
}
