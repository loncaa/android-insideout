package hr.magicpot.app.insideout.storage.db.manager;

import com.google.android.gms.maps.model.LatLng;

import hr.magicpot.app.insideout.storage.db.model.Location;

public interface LocationManager {
    interface onDatabaseConnection{
        void onMessage(String msg);
        void onStoreSuccess(Location location);
        void onFetchSuccess(Location location);
        void onDeleteSuccess();
    }

    void checkDatabase(LatLng latLng, final onDatabaseConnection listener);

    void store(LatLng latLng, onDatabaseConnection listener);

    void fetchLocation(onDatabaseConnection listener);

    void deleteLocations(onDatabaseConnection listener);
}
