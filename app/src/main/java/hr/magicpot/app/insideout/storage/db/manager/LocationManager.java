package hr.magicpot.app.insideout.storage.db.manager;

import com.google.android.gms.maps.model.LatLng;

import hr.magicpot.app.insideout.storage.db.model.Location;

/**
 * Created by Antonio on 5.9.2017..
 */

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
